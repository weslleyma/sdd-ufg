package br.ufg.inf.sdd_ufg.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends AbstractResource {

	private final UserDao userDao;
	private final TeacherDao teacherDao;

	@Inject
	public UserResource(final UserDao userDao, final TeacherDao teacherDao) {
		this.userDao = userDao;
		this.teacherDao = teacherDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveUserById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		User user = userDao.findById(id, 1);
		if (user == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(user).build();
	}

	@GET
	public Response retrieveAllUsers(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {
		List<User> users = userDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<User> rsp = new ResultSetResponse<User>(users, page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertUser(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		User user;
		try {
			user = retrieveUserFromJson(request);
			userDao.insert(user);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}

		return Response
				.status(Response.Status.CREATED)
				.header(HttpHeaders.LOCATION.toString(),
						"/users/" + user.getId())
				.entity(user).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateUser(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		User existingUser;
		try {
			User jsonUser = retrieveUserFromJson(request);
			existingUser = userDao.findById(id, 1);

			existingUser.setEmail(jsonUser.getEmail());
			existingUser.setPassword(jsonUser.getPassword());
			existingUser.setIsAdmin(jsonUser.getIsAdmin());

			userDao.update(existingUser);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		URI location = info.getRequestUri();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken()).entity(existingUser)
				.build();
	}

	private User retrieveUserFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		User user = new User();
		if (content.get("username") != null) {
			user.setUsername(content.get("username").toString());
		}
		user.setPassword(content.get("password").toString());
		user.setEmail(content.get("email").toString());
		if (content.get("is_admin") != null && getLoggedUser(request).getIsAdmin()) {
			user.setIsAdmin(new Boolean(content.get("is_admin").toString()));
		} else {
			user.setIsAdmin(false);
		}
		if (content.get("teacher_id") != null) {
			Teacher teacher = teacherDao.findById(
					new Long(content.get("teacher_id").toString()), 0);
			user.setTeacher(teacher);
		}

		return user;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			userDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
