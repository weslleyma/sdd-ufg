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

import br.ufg.inf.sdd_ufg.dao.ClazzScheduleDao;
import br.ufg.inf.sdd_ufg.model.ClazzSchedule;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
public class ClazzScheduleResource extends AbstractResource {

	private final ClazzScheduleDao clazzScheduleDao;

	@Inject
	public ClazzScheduleResource(final ClazzScheduleDao clazzScheduleDao) {
		this.clazzScheduleDao = clazzScheduleDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveClazzScheduleById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		ClazzSchedule clazzSchedule = clazzScheduleDao.findById(id, 1);
		if (clazzSchedule == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(clazzSchedule).build();
	}

	@GET
	public Response retrieveAllClazzSchedules(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {
		List<ClazzSchedule> clazzSchedules = clazzScheduleDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<ClazzSchedule> rsp = new ResultSetResponse<ClazzSchedule>(
				clazzSchedules, page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertClazzSchedule(
			@Context final HttpServletRequest request, @Context UriInfo info) {
		ClazzSchedule clazzSchedule;
		try {
			clazzSchedule = retrieveClazzScheduleFromJson(request);
			clazzScheduleDao.insert(clazzSchedule);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		
		URI location = info.getBaseUriBuilder().path("/schedules")
				.path(clazzSchedule.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken())
				.entity(clazzSchedule).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateClazzSchedule(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		ClazzSchedule clazzSchedule;
		try {
			clazzSchedule = retrieveClazzScheduleFromJson(request);
			clazzSchedule.setId(id);
			clazzScheduleDao.update(clazzSchedule);
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
						getLoggedUser(request).getSessionToken())
				.entity(clazzSchedule).build();
	}

	private ClazzSchedule retrieveClazzScheduleFromJson(
			final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);

		ClazzSchedule clazzSchedule = new ClazzSchedule();
		clazzSchedule.setWeekDay(new Integer(content.get("week_day").toString()));
		clazzSchedule.setStartTime(content.get("start_time").toString());
		clazzSchedule.setEndTime(content.get("end_time").toString());

		return clazzSchedule;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteClazzSchedule(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			clazzScheduleDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
