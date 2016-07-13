package com.alberg.jiaqi.integration.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alberg.jiaqi.rest.GuotaiRestException;

public class ConnectionUtil {
	private static final Logger logger = LogManager.getLogger();
	private static TrustManager truseAllManager = new X509TrustManager() {

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	public static String post(String url, String content, String contentType, HttpSession session) {

		logger.info("Request is ulr = " + url + " content = " + content);
		//logger.info("Request is:" + content);
		String line = null;
		StringBuffer document = new StringBuffer();
		

		// Create your httpclient
		DefaultHttpClient client = getHttpClient();


		// Generate BASIC scheme object and stick it to the execution context
		BasicScheme basicAuth = new BasicScheme();
		BasicHttpContext context = new BasicHttpContext();
		context.setAttribute("preemptive-auth", basicAuth);


		// You get request that will start the build
		HttpPost post = new HttpPost(url);

		try {
			StringEntity contentEntity = new StringEntity(content, "utf-8");
			//contentEntity.setContentEncoding("UTF-8");
			contentEntity.setContentType(contentType);
			post.setEntity(contentEntity);
			post.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
			post.addHeader("Connection", "keep-alive");
			post.addHeader("Cache-Control", "max-age=0");
			post.addHeader("Accept", "application/json");
			post.addHeader("Accept-Encoding", "gzip, deflate, sdch");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			
			Map<String, String> cookiesMap = (Map<String, String>)session.getAttribute("cookiesMap") ;
			
	        if(null == cookiesMap){ 
	        	cookiesMap = new HashMap<String, String> ();
	        }  

        	String cookieString = "";
        	for (String cookieName : cookiesMap.keySet()) {
        		cookieString = cookieString + cookieName + "=" + cookiesMap.get(cookieName) + ";";
        	}
            post.setHeader("Cookie", cookieString);  
            logger.info("cookie is:" + cookieString);
	        
			// Execute your request with the given context
			HttpResponse response = client.execute(post, context);
			
			HttpEntity entity = response.getEntity();
			//EntityUtils.consume(entity);
			
			CookieStore cookieStore = client.getCookieStore();  
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				cookiesMap.put(cookie.getName(), cookie.getValue());
			}
			session.setAttribute("cookiesMap", cookiesMap);
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"));
			while ((line = reader.readLine()) != null) {
				document.append(line);
			}
			reader.close();	

		}
		catch (IOException e) {
			logger.error("error!", e);
			throw new GuotaiRestException("服务器连接中断,请稍后再试或尝试联系系统管理员");
		}

		logger.info("response is:" + document.toString());
		return document.toString();
	
		
	}
	
	
	private static DefaultHttpClient getHttpClient() {
	    try {

			// Credentials
			String username = "zhijgu";
			String password = "Gzj_1982110811";
			
	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SSLContext sslcontext = SSLContext.getInstance("TLS");  
            sslcontext.init(null, new TrustManager[] { truseAllManager }, null);  
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            
	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", (SocketFactory) sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	        DefaultHttpClient httpClient = new DefaultHttpClient(ccm, params);
	        
            Scheme https = new Scheme("https", sf, 443);  
            httpClient.getConnectionManager().getSchemeRegistry().register(https);  

    		// Then provide the right credentials
            httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
    				new UsernamePasswordCredentials(username, password));

    		// Add as the first (because of the zero) request interceptor
    		// It will first intercept the request and preemptively initialize the authentication scheme if there is not
            httpClient.addRequestInterceptor(new PreemptiveAuth(), 0);
            return httpClient;
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	

	/**
	 * Preemptive authentication interceptor
	 *
	 */
	static class PreemptiveAuth implements HttpRequestInterceptor {

		/*
		 * (non-Javadoc)
		 *
		 * @see org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest,
		 * org.apache.http.protocol.HttpContext)
		 */
		public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
			// Get the AuthState
			AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

			// If no auth scheme available yet, try to initialize it preemptively
			if (authState.getAuthScheme() == null) {
				AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
				CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if (authScheme != null) {
					Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost
							.getPort()));
					if (creds == null) {
						throw new HttpException("No credentials for preemptive authentication");
					}
					authState.setAuthScheme(authScheme);
					authState.setCredentials(creds);
				}
			}

		}

	}
}
