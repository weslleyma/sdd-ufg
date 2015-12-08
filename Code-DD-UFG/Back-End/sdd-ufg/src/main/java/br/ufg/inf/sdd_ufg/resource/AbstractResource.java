package br.ufg.inf.sdd_ufg.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.resource.utils.ErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteSource;

public abstract class AbstractResource {
	
	public ByteSource getContent(final HttpServletRequest request) {
		final ByteSource byteSource = new ByteSource() {
	        @Override
	        public InputStream openStream() throws IOException {
	            return request.getInputStream();
	        }
	    };
	    
	    return byteSource;
	}
	
	public String getStringContent(final HttpServletRequest request) {
	    String content;
		try {
			content = getContent(request)
					.asCharSource(Charset.forName("UTF-8"))
					.read();
		} catch (IOException e) {
			content = "";
		}
		
		return content;
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getJSONContent(final HttpServletRequest request) {
		String content = getStringContent(request);
		HashMap<String, Object> contentMapped;
		try {
			contentMapped = new ObjectMapper().readValue(content, HashMap.class);
		} catch (IOException e) {
			contentMapped = new HashMap<String, Object>();
		}
		
		return contentMapped;
	}
	
	public User getLoggedUser(final HttpServletRequest request) {
		User loggedUser = null;
    	try {
    		loggedUser = (User) request.getAttribute("Logged-User");
    	} catch(Exception e) {}
    	
    	return loggedUser;
	}
	
	protected Response getAuthenticationErrorResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.FORBIDDEN.getStatusCode()
				, "You have no permission to access this resource");
		
		return Response.status(Response.Status.FORBIDDEN)
				.entity(errorResponse)
				.build();
	}
	
	protected Response getInsertErrorResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
				, "Entity already exists.");
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(errorResponse)
			.build();
	}
	
	protected Response getInsertErrorResponse(String msg) {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
				, msg);
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(errorResponse)
			.build();
	}
	
	protected Response getResourceNotFoundResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode()
				, "Resource Not Found");
		
		return Response.status(Response.Status.NOT_FOUND)
				.entity(errorResponse)
				.build();
	}
	
	protected Response getBadRequestResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode()
				, "Wrong arguments.");
		
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(errorResponse)
			.build();
	}
}
