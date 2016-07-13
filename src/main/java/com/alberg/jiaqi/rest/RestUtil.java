package com.alberg.jiaqi.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestUtil {

	public static String getResponseAsString(String queryUrl) {
		String line = null;
		StringBuffer document = new StringBuffer();
		HttpURLConnection conn = getConnection(queryUrl);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				document.append(line);
			}
			reader.close();
			conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document.toString();
	}

	private static HttpURLConnection getConnection(String queryUrl) {
		HttpURLConnection conn = null;
		try {
			URL u = new URL(queryUrl);
			/*
			 * if (queryUrl.startsWith("https")) { TrustManager[] trustAllCerts
			 * = new TrustManager[] { new X509TrustManager() { public
			 * java.security.cert.X509Certificate[] getAcceptedIssuers() {
			 * return null; }
			 * 
			 * public void checkClientTrusted(X509Certificate[] certs, String
			 * authType) { }
			 * 
			 * public void checkServerTrusted(X509Certificate[] certs, String
			 * authType) { }
			 * 
			 * } }; SSLContext sc = SSLContext.getInstance("SSL"); sc.init(null,
			 * trustAllCerts, new java.security.SecureRandom());
			 * HttpsURLConnection.setDefaultSSLSocketFactory(sc
			 * .getSocketFactory()); HostnameVerifier allHostsValid = new
			 * HostnameVerifier() { public boolean verify(String hostname,
			 * SSLSession session) { return true; }
			 * 
			 * }; // Install the all-trusting host verifier
			 * HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid); }
			 */
			// conn.setRequestProperty("Authorization", authorization);
			// conn.addRequestProperty("Accept", "application/json");
			conn = (HttpURLConnection) u.openConnection();
			conn.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
