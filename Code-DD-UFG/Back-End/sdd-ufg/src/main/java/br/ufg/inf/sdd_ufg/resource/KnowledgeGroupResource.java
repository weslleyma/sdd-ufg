package br.ufg.inf.sdd_ufg.resource;

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

import br.ufg.inf.sdd_ufg.dao.KnowledgeGroupDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.KnowledgeGroup;
import br.ufg.inf.sdd_ufg.model.KnowledgeLevel;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/knowledgeGroups")
@Produces(MediaType.APPLICATION_JSON)
public class KnowledgeGroupResource extends AbstractResource {

	private final KnowledgeGroupDao knowledgeGroupDao;
	private final TeacherDao teacherDao;

    @Inject
    public KnowledgeGroupResource(final KnowledgeGroupDao knowledgeGroupDao, final TeacherDao teacherDao) {
        this.knowledgeGroupDao = knowledgeGroupDao;
        this.teacherDao = teacherDao;
    }
	
    @GET
	@Path("/{id}")
	public Response retrieveKnowledgeGroupById(@PathParam("id") Long id, @Context final HttpServletRequest request) {
    	if (!validateSession(request)) {
			return getAuthenticationErrorResponse();
		}
    	
		KnowledgeGroup knowledgeGroup = knowledgeGroupDao.findById(id, 1);
		if (knowledgeGroup == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
    
	@GET
    public Response retrieveAllKnowledgeGroups(@QueryParam("page") Integer page, @Context final HttpServletRequest request) {
		if (!validateSession(request)) {
			return getAuthenticationErrorResponse();
		}
		
		List<KnowledgeGroup> knowledgeGroups = knowledgeGroupDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<KnowledgeGroup> rsp = new ResultSetResponse<KnowledgeGroup>(knowledgeGroups, page);
		
		return Response.ok(rsp)
				.build();
    }
	
	@POST
	public Response insertKnowledgeGroup(@Context final HttpServletRequest request) {
		if (!validateSession(request)) {
			return getAuthenticationErrorResponse();
		}
		
		KnowledgeGroup knowledgeGroup;
		try {
			knowledgeGroup = retrieveKnowledgeGroupFromJson(request);
			knowledgeGroupDao.insert(knowledgeGroup);
			
			updateTeachersKnowledge(knowledgeGroup);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
	
	private void updateTeachersKnowledge(KnowledgeGroup kg) {
		List<Teacher> teachers = teacherDao.findAll(2);
		for (Teacher teacher : teachers) {
			KnowledgeLevel kl = new KnowledgeLevel();
			kl.setLevel(3);
			kl.setTeacher(teacher);
			kl.setKnowledgeGroup(kg);
			
			teacher.getKnowledgeLevels().add(kl);
			teacherDao.update(teacher);
		}
		
	}
	
	@PUT
	@Path("/{id}")
	public Response updateKnowledgeGroup(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (!validateSession(request)) {
			return getAuthenticationErrorResponse();
		}
		
		KnowledgeGroup knowledgeGroup;
		try {
			knowledgeGroup = retrieveKnowledgeGroupFromJson(request);
			knowledgeGroup.setId(id);
			knowledgeGroupDao.update(knowledgeGroup);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
	
	private KnowledgeGroup retrieveKnowledgeGroupFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		KnowledgeGroup knowledgeGroup = new KnowledgeGroup();
		knowledgeGroup.setName(content.get("name").toString());
		
		return knowledgeGroup;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteKnowledgeGroup(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (!validateSession(request)) {
			return getAuthenticationErrorResponse();
		}
		
		try {
			knowledgeGroupDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
}
