package com.alberg.jiaqi.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GuotaiRestException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public GuotaiRestException(String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new JsonError("1001", message)).type(MediaType.APPLICATION_JSON).build());
	}

}
