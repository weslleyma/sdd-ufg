package br.ufg.inf.sdd_ufg.resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.resource.utils.ErrorResponse;
import br.ufg.inf.sdd_ufg.resource.utils.SessionTokenResponse;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource extends AbstractResource {

	private final UserDao userDao;

    @Inject
    public SessionResource(final UserDao userDao) {
        this.userDao = userDao;
    }
	
	@POST
	public Response insertKnowledgeGroup(@Context final HttpServletRequest request) {
		User user;
		try {
			user = retrieveUserFromLogin(request);
			user.setSessionToken(generateSessionToken());
			user.setTokenCreatedAt(new Date());
			
			userDao.update(user);
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(Response.Status.FORBIDDEN.toString()
					, "The user could not be authenticated: " + e.getMessage());
			
			return Response.status(Response.Status.FORBIDDEN)
					.entity(errorResponse)
					.build();
		}
		SessionTokenResponse str = new SessionTokenResponse(user.getSessionToken());
		
		return Response.ok(str)
				.build();
	}
	
	private User retrieveUserFromLogin(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		String userName = content.get("user_name").toString();
		String authPass = content.get("auth_pass").toString();
		
		User user = userDao.findUserByUsername(userName);
		if (user != null) {
			String encodedPassword = "";
			long unixTime = System.currentTimeMillis() / 1000L;
			for (int i = 0; i < 5; i++) {
				unixTime -= i * 1L;
				encodedPassword = SHAsum((user.getPassword() 
						+ unixTime).getBytes());
				if (encodedPassword.equals(authPass)) {
					return user;
				}
			}
		}
		throw new Exception("Incorrect username/password");
	}
    
    private String generateSessionToken() {
    	StringBuilder sb =  new StringBuilder();
    	
    	for (int count = 1; count < 35; count++) {
    		if (count % 7 == 0) {
    			sb.append("-");
    		} else {
    			Integer rdNb = new Random().nextInt(16); 
    			sb.append(Integer.toHexString(rdNb));
    		}
    	}
    	
    	return sb.toString();
    }
    
    @SuppressWarnings("resource")
    public static String SHAsum(byte[] convertme) {
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		
        final byte[] hash = md.digest(convertme);
		Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        
        return formatter.toString();
    }
}
