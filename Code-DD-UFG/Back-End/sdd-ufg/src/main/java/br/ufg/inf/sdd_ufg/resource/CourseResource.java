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
import br.ufg.inf.sdd_ufg.model.Course;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource extends AbstractResource {

	private final CourseDao courseDao;

	@Inject
	public CourseResource(final CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveCourseById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		Course course = courseDao.findById(id, 1);
		if (course == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(course).build();
	}

	@GET
	public Response retrieveAllCourses(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {
		List<Course> courses = courseDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<Course> rsp = new ResultSetResponse<Course>(courses,
				page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertCourse(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		Course course;
		try {
			course = retrieveCourseFromJson(request);
			courseDao.insert(course);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}

		URI location = info.getBaseUriBuilder().path("/courses")
				.path(course.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken())
				.entity(course).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateCourse(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		Course course;
		try {
			course = retrieveCourseFromJson(request);
			course.setId(id);
			courseDao.update(course);
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
				.entity(course).build();
	}

	private Course retrieveCourseFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		Course course = new Course();
		course.setName(content.get("name").toString());

		return course;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCourse(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			courseDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
