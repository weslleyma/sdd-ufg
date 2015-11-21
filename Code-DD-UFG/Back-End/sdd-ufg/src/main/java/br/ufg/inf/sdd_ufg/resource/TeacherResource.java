package br.ufg.inf.sdd_ufg.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
public class TeacherResource extends AbstractResource {

	private final TeacherDao teacherDao;

    @Inject
    public TeacherResource(final TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
	
    @GET
	@Path("/{id}")
	public Response retrieveTeacherById(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		Teacher teacher = teacherDao.findById(id, 1);
		if (teacher == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(teacher)
				.build();
	}
    
	@GET
    public Response retrieveAllTeachers(@QueryParam("page") Integer page, @Context final HttpServletRequest request) {
		List<Teacher> teachers = teacherDao.findAll(0);
		if (teachers == null || teachers.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<Teacher> rsp = new ResultSetResponse<Teacher>(teachers, page);
		
		return Response.ok(rsp)
				.build();
    }
	
	@POST
	public Response insertTeacher(@Context final HttpServletRequest request) {
		Teacher teacher;
		try {
			teacher = retrieveTeacherFromJson(request);
			teacherDao.insert(teacher);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.ok(teacher)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateTeacher(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		Teacher teacher;
		try {
			teacher = retrieveTeacherFromJson(request);
			teacher.setId(id);
			teacherDao.update(teacher);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.ok(teacher)
				.build();
	}
	
	private Teacher retrieveTeacherFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		Teacher teacher = new Teacher();
		teacher.setName(content.get("name").toString());
		teacher.setRegistry(content.get("registry").toString());
		teacher.setUrlLattes(content.get("url_lattes").toString());
		teacher.setEntryDate(df.parse(content.get("entry_date").toString()));
		teacher.setFormation(content.get("formation").toString());
		teacher.setWorkload(new Integer(content.get("workload").toString()));
		teacher.setAbout(content.get("about").toString());
		teacher.setRg(content.get("rg").toString());
		teacher.setCpf(content.get("cpf").toString());
		teacher.setBirthDate(df.parse(content.get("birth_date").toString()));
		//teacher.setUser(content.get("cpf").toString());
		
		return teacher;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteTeacher(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		try {
			teacherDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
}
