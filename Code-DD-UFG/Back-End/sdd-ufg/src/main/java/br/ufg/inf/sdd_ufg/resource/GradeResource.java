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

import br.ufg.inf.sdd_ufg.dao.CourseDao;
import br.ufg.inf.sdd_ufg.dao.GradeDao;
import br.ufg.inf.sdd_ufg.dao.KnowledgeGroupDao;
import br.ufg.inf.sdd_ufg.model.Course;
import br.ufg.inf.sdd_ufg.model.Grade;
import br.ufg.inf.sdd_ufg.model.KnowledgeGroup;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/grades")
@Produces(MediaType.APPLICATION_JSON)
public class GradeResource extends AbstractResource {

	private final GradeDao gradeDao;
	private final CourseDao courseDao;
	private final KnowledgeGroupDao knowledgeGroupDao;

	@Inject
	public GradeResource(final GradeDao gradeDao, final CourseDao courseDao,
			final KnowledgeGroupDao knowledgeGroupDao) {
		this.gradeDao = gradeDao;
		this.courseDao = courseDao;
		this.knowledgeGroupDao = knowledgeGroupDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveGradeById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		if (getLoggedUser(request) == null) {
			return getAuthenticationErrorResponse();
		}

		Grade grade = gradeDao.findById(id, 1);
		if (grade == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(grade).build();
	}

	@GET
	public Response retrieveAllGrades(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {
		if (getLoggedUser(request) == null) {
			return getAuthenticationErrorResponse();
		}

		List<Grade> grades = gradeDao.findAll(1);
		if (page == null) {
			page = 1;
		}

		for (Grade grade : grades) {
			grade.setCourseId(grade.getCourse().getId());
			grade.setCourse(null);
			grade.setKnowledgeGroupId(grade.getKnowledgeGroup().getId());
			grade.setKnowledgeGroup(null);
		}

		ResultSetResponse<Grade> rsp = new ResultSetResponse<Grade>(grades,
				page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertGrade(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		if (getLoggedUser(request) == null) {
			return getAuthenticationErrorResponse();
		}

		Grade grade;
		try {
			grade = retrieveGradeFromJson(request);
			gradeDao.insert(grade);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		URI location = info.getBaseUriBuilder().path("/grades")
				.path(grade.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken()).entity(grade)
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateGrade(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		if (getLoggedUser(request) == null) {
			return getAuthenticationErrorResponse();
		}

		Grade grade;
		try {
			grade = retrieveGradeFromJson(request);
			grade.setId(id);
			gradeDao.update(grade);
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
						getLoggedUser(request).getSessionToken()).entity(grade)
				.build();
	}

	private Grade retrieveGradeFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		Grade grade = new Grade();
		grade.setName(content.get("name").toString());

		Course course = courseDao.findById(new Long(content.get("course_id")
				.toString()), 0);
		grade.setCourse(course);

		KnowledgeGroup knowledgeGroup = knowledgeGroupDao.findById(new Long(
				content.get("knowledge_id").toString()), 0);
		grade.setKnowledgeGroup(knowledgeGroup);

		return grade;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteGrade(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		if (getLoggedUser(request) == null) {
			return getAuthenticationErrorResponse();
		}

		try {
			gradeDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
