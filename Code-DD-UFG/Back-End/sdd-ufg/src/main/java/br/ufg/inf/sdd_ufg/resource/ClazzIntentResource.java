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

import br.ufg.inf.sdd_ufg.dao.ClazzDao;
import br.ufg.inf.sdd_ufg.dao.ClazzIntentDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.Clazz;
import br.ufg.inf.sdd_ufg.model.ClazzIntent;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/intents")
@Produces(MediaType.APPLICATION_JSON)
public class ClazzIntentResource extends AbstractResource {

	private final ClazzIntentDao clazzIntentDao;
	private final ClazzDao clazzDao;
	private final TeacherDao teacherDao;

	@Inject
	public ClazzIntentResource(final ClazzIntentDao clazzIntentDao,
			final ClazzDao clazzDao,
			final TeacherDao teacherDao) {
		this.clazzIntentDao = clazzIntentDao;
		this.clazzDao = clazzDao;
		this.teacherDao = teacherDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveClazzById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {

		ClazzIntent clazzIntent = clazzIntentDao.findById(id, 1);
		if (clazzIntent == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(clazzIntent).build();
	}

	@GET
	public Response retrieveAllClazzIntents(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {

		List<ClazzIntent> clazzIntents = clazzIntentDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<ClazzIntent> rsp = new ResultSetResponse<ClazzIntent>(clazzIntents,
				page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertClazzIntent(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		ClazzIntent clazzIntentIntent;
		try {
			clazzIntentIntent = retrieveClazzIntentFromJson(request);
			clazzIntentDao.insert(clazzIntentIntent);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}

		URI location = info.getBaseUriBuilder().path("/intents")
				.path(clazzIntentIntent.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken()).entity(clazzIntentIntent)
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateClazzIntent(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		ClazzIntent clazzIntent;
		try {
			clazzIntent = retrieveClazzIntentFromJson(request);
			clazzIntent.setId(id);
			clazzIntentDao.update(clazzIntent);
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
						getLoggedUser(request).getSessionToken()).entity(clazzIntent)
				.build();
	}

	private ClazzIntent retrieveClazzIntentFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		ClazzIntent clazzIntent = new ClazzIntent();

		Clazz clazz = clazzDao.findById(
				new Long(content.get("clazz_id").toString()), 0);
		if (clazz == null) {
			throw new NullPointerException();
		}
		clazzIntent.setClazz(clazz);
		
		Teacher teacher = teacherDao.findById(
				new Long(content.get("teacher_id").toString()), 0);
		if (teacher == null) {
			throw new NullPointerException();
		}
		clazzIntent.setTeacher(teacher);

		return clazzIntent;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteClazzIntent(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			clazzIntentDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
