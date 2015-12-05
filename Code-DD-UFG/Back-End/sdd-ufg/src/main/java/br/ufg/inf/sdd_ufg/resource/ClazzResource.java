package br.ufg.inf.sdd_ufg.resource;

import java.net.URI;
import java.util.ArrayList;
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
import br.ufg.inf.sdd_ufg.dao.ClazzScheduleDao;
import br.ufg.inf.sdd_ufg.dao.DistributionProcessDao;
import br.ufg.inf.sdd_ufg.dao.GradeDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.Clazz;
import br.ufg.inf.sdd_ufg.model.ClazzIntent;
import br.ufg.inf.sdd_ufg.model.ClazzSchedule;
import br.ufg.inf.sdd_ufg.model.DistributionProcess;
import br.ufg.inf.sdd_ufg.model.Grade;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.model.enums.StatusClazz;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/clazzes")
@Produces(MediaType.APPLICATION_JSON)
public class ClazzResource extends AbstractResource {

	private final ClazzDao clazzDao;
	private final GradeDao gradeDao;
	private final TeacherDao teacherDao;
	private final ClazzScheduleDao clazzScheduleDao;
	private final DistributionProcessDao distributionProcessDao;

	@Inject
	public ClazzResource(final ClazzDao clazzDao, final GradeDao gradeDao,
			final TeacherDao teacherDao,
			final ClazzScheduleDao clazzScheduleDao,
			final DistributionProcessDao distributionProcessDao) {
		this.clazzDao = clazzDao;
		this.gradeDao = gradeDao;
		this.teacherDao = teacherDao;
		this.clazzScheduleDao = clazzScheduleDao;
		this.distributionProcessDao = distributionProcessDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveClazzById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {

		Clazz clazz = clazzDao.findById(id, 2);
		if (clazz == null) {
			return getResourceNotFoundResponse();
		}
		for (ClazzIntent ci : clazz.getIntents()) {
			ci.setClazz(null);
		}
		return Response.ok(clazz).build();
	}

	@GET
	public Response retrieveAllClazzs(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {

		List<Clazz> clazzs = clazzDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<Clazz> rsp = new ResultSetResponse<Clazz>(clazzs,
				page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertClazz(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		Clazz clazz;
		try {
			clazz = retrieveClazzFromJson(request);
			clazzDao.insert(clazz);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}

		URI location = info.getBaseUriBuilder().path("/clazzes")
				.path(clazz.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken()).entity(clazz)
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateClazz(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		Clazz clazz;
		try {
			clazz = retrieveClazzFromJson(request);
			clazz.setId(id);
			clazzDao.update(clazz);
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
						getLoggedUser(request).getSessionToken()).entity(clazz)
				.build();
	}

	@SuppressWarnings("unchecked")
	private Clazz retrieveClazzFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		Clazz clazz = new Clazz();
		clazz.setWorkload(new Integer(content.get("workload").toString()));

		Grade grade = gradeDao.findById(new Long(content.get("grade_id")
				.toString()), 0);
		if (grade == null) {
			throw new NullPointerException();
		}
		clazz.setGrade(grade);

		DistributionProcess dp = distributionProcessDao.findById(new Long(
				content.get("process_id").toString()), 0);
		if (dp == null) {
			throw new NullPointerException();
		}
		clazz.setProcess(dp);

		if (content.get("teacher_id") != null) {
			Teacher teacher = teacherDao.findById(
					new Long(content.get("teacher_id").toString()), 0);
			clazz.setTeacher(teacher);
		}

		clazz.setSchedules(new ArrayList<ClazzSchedule>());
		ArrayList<Object> schedules = (ArrayList<Object>) content
				.get("schedules");
		for (Object schedule : schedules) {
			ClazzSchedule clazzSchedule = clazzScheduleDao.findById(new Long(
					schedule.toString()), 0);
			clazz.getSchedules().add(clazzSchedule);
		}

		return clazz;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteClazz(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			Clazz clazz = clazzDao.findById(id, 1);
			clazz.setStatus(StatusClazz.CANCELED.toString());
			clazzDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
