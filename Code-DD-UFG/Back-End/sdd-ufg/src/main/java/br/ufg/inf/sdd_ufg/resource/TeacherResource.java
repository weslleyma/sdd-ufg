package br.ufg.inf.sdd_ufg.resource;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import br.ufg.inf.sdd_ufg.dao.KnowledgeGroupDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.KnowledgeGroup;
import br.ufg.inf.sdd_ufg.model.KnowledgeLevel;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
public class TeacherResource extends AbstractResource {

	private final TeacherDao teacherDao;
	private final KnowledgeGroupDao knowledgeGroupDao;

	@Inject
	public TeacherResource(final TeacherDao teacherDao,
			final KnowledgeGroupDao knowledgeGroupDao) {
		this.teacherDao = teacherDao;
		this.knowledgeGroupDao = knowledgeGroupDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveTeacherById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		Teacher teacher = teacherDao.findById(id, 2);
		if (teacher == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(teacher).build();
	}

	@GET
	public Response retrieveAllTeachers(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {
		List<Teacher> teachers = teacherDao.findAll(0);
		if (teachers == null || teachers.size() == 0) {
			return getResourceNotFoundResponse();
		}
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<Teacher> rsp = new ResultSetResponse<Teacher>(
				teachers, page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertTeacher(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		Teacher teacher;
		try {
			teacher = retrieveTeacherFromJson(request);
			fillKnowledgeLevels(teacher);

			teacherDao.insert(teacher);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response
				.status(Response.Status.CREATED)
				.header(HttpHeaders.LOCATION.toString(),
						"/teachers/" + teacher.getId())
				.entity(teacher).build();
	}

	private void fillKnowledgeLevels(Teacher teacher) {
		List<KnowledgeGroup> knowledgeGroups = knowledgeGroupDao.findAll(0);
		for (KnowledgeGroup kg : knowledgeGroups) {
			KnowledgeLevel kl = new KnowledgeLevel();
			kl.setLevel(3);
			kl.setTeacher(teacher);
			kl.setKnowledgeGroup(kg);

			teacher.getKnowledgeLevels().add(kl);
		}
	}

	@PUT
	@Path("/{id}")
	public Response updateTeacher(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		Teacher teacher;
		try {
			teacher = retrieveTeacherFromJson(request);
			teacher.setId(id);
			teacherDao.update(teacher);
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
						getLoggedUser(request).getSessionToken()).entity(teacher)
				.build();
	}

	private Teacher retrieveTeacherFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Teacher teacher = new Teacher();
		teacher.setName(content.get("name").toString());
		teacher.setRegistry(content.get("registry").toString());
		teacher.setUrlLattes(content.get("url_lattes").toString());
		teacher.setEntryDate(df.parse(content.get("date_entry").toString()));
		teacher.setFormation(content.get("formation").toString());
		teacher.setWorkload(new Integer(content.get("workload").toString()));
		teacher.setAbout(content.get("about").toString());
		teacher.setRg(content.get("rg").toString());
		teacher.setCpf(content.get("cpf").toString());
		teacher.setBirthDate(df.parse(content.get("birth_date").toString()));

		return teacher;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteTeacher(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			teacherDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
