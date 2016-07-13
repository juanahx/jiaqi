package com.alberg.jiaqi.integration.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class ResponseSessionFilter implements ClientResponseFilter {
	private List<Object> cookies;
	private HttpSession session;

	public ResponseSessionFilter(HttpSession session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void filter(ClientRequestContext requestContext,
			ClientResponseContext responseContext) throws IOException {
		if (responseContext.getCookies() != null) {
			if (cookies == null) {
				cookies = new ArrayList<Object>();
			}
			// simple addAll just for illustration (should probably check for
			// duplicates and expired cookies)
			cookies.addAll((Collection<? extends Object>) responseContext
					.getCookies().values());
			session.setAttribute("guotaiapicookies", cookies);
		}
	}

}
