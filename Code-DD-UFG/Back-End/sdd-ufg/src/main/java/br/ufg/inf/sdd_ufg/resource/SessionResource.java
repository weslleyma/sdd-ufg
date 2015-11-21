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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.Role;
import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource extends AbstractResource {

	private final UserDao userDao;

    @Inject
    public SessionResource(final UserDao userDao) {
        this.userDao = userDao;
    }
	
}
