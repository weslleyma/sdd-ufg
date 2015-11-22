package br.ufg.inf.sdd_ufg.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.resource.utils.ErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteSource;

public abstract class AbstractResource {
	
	private static final Integer SESSION_MAX_DURATION = 30; // Min
	
	@Inject
	private UserDao userDao;
	
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
	
	protected Boolean validateSession(final HttpServletRequest request) {
		String token = request.getHeader("Session-Token");
		
		User user = userDao.findUserByToken(token);
		
		if (user != null) {
			Long sessionDurationMs = new Date().getTime() - user.getTokenCreatedAt().getTime();
			Long sessionDurationM = TimeUnit.MICROSECONDS.toMinutes(sessionDurationMs);
			if (sessionDurationM.intValue() < SESSION_MAX_DURATION) {
				return true;
			}
		}
		
		return false;
	}
	
	protected Response getAuthenticationErrorResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.FORBIDDEN.toString()
				, "You have no permission to access this resource");
		
		return Response.status(Response.Status.FORBIDDEN)
				.entity(errorResponse)
				.build();
	}
	
	protected Response getInsertErrorResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.toString()
				, "Entity already exists.");
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(errorResponse)
			.build();
	}
	
	protected Response getResourceNotFoundResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.NOT_FOUND.toString()
				, "Resource Not Found");
		
		return Response.status(Response.Status.NOT_FOUND)
				.entity(errorResponse)
				.build();
	}
	
	protected Response getBadRequestResponse() {
		ErrorResponse errorResponse = new ErrorResponse(Response.Status.BAD_REQUEST.toString()
				, "Request malformed.");
		
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(errorResponse)
			.build();
	}
}
