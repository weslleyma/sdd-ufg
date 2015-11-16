package br.ufg.inf.sdd_ufg.resource;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserDao userDao;

    @Inject
    public UserResource(final UserDao userDao) {
        this.userDao = userDao;
    }
	
	@GET
    public Response retrieveBatch(@Context final HttpServletRequest request) {
    	userDao.findAll();
		return Response.ok()
				.build();
    }
	
}
