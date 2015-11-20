package br.ufg.inf.sdd_ufg.resource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.Role;
import br.ufg.inf.sdd_ufg.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends AbstractResource {

	private final UserDao userDao;

    @Inject
    public UserResource(final UserDao userDao) {
        this.userDao = userDao;
    }
	
    @GET
	@Path("/{user_id}")
	public Response retrieveUserById(@PathParam("user_id") Long id, @Context final HttpServletRequest request) {
		User user = userDao.findById(id, 1);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(user)
				.build();
	}
    
	@GET
    public Response retrieveAllUsers(@Context final HttpServletRequest request) {
		List<User> users = userDao.findAll(0);
		if (users == null || users.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.ok(users)
				.build();
    }
	
	@POST
	public Response insertUser(@Context final HttpServletRequest request) {
		try {
			User user = retrieveUserFromJson(request);
			userDao.insert(user);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
	
	@PUT
	@Path("/{user_id}")
	public Response updateUser(@PathParam("user_id") Long id, @Context final HttpServletRequest request) {
		try {
			User user = retrieveUserFromJson(request);
			user.setId(id);
			userDao.update(user);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
	
	@SuppressWarnings("unchecked")
	private User retrieveUserFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		User user = new User();
		user.setUserName(content.get("username").toString());
		user.setPassword(content.get("password").toString());
		user.setEmail(content.get("email").toString());
		
		if (content.get("role") != null) {
			Map<String, Object> roleJson = (LinkedHashMap<String, Object>) content.get("role");
			
			Role role = new Role();
			role.setId(new Long(roleJson.get("id").toString()));
			role.setRole(roleJson.get("role").toString());
		}
		
		return user;
	}
	
	@DELETE
	@Path("/{user_id}")
	public Response deleteUser(@PathParam("user_id") Long id, @Context final HttpServletRequest request) {
		try {
			userDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
	
}
