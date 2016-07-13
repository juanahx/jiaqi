package com.alberg.jiaqi.integration.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

public class RequestSessionFilter implements ClientRequestFilter {
	private HttpSession session;

	public RequestSessionFilter(HttpSession session) {
		this.session = session;
	}

	public void filter(ClientRequestContext requestContext) throws IOException {
		List<Object> cookies = (List<Object>) session
				.getAttribute("guotaiapicookies");
		if (cookies != null) {
			requestContext.getHeaders().put("Cookie", cookies);
		}
/*
		requestContext.getHeaders().add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
		requestContext.getHeaders().add("Connection", "keep-alive");
		requestContext.getHeaders().add("Cache-Control", "max-age=0");
		requestContext.getHeaders().add("Accept", "text/html,application/xhtml+xml,application/xml;");
		requestContext.getHeaders().add("Accept-Encoding", "gzip, deflate, sdch");
		requestContext.getHeaders().add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	
*/
	}

}
