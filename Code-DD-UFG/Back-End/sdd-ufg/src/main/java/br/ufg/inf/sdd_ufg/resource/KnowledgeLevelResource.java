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
import br.ufg.inf.sdd_ufg.dao.KnowledgeLevelDao;
import br.ufg.inf.sdd_ufg.dao.TeacherDao;
import br.ufg.inf.sdd_ufg.model.KnowledgeGroup;
import br.ufg.inf.sdd_ufg.model.KnowledgeLevel;
import br.ufg.inf.sdd_ufg.model.Teacher;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/knowledge_levels")
@Produces(MediaType.APPLICATION_JSON)
public class KnowledgeLevelResource extends AbstractResource {

	private final KnowledgeLevelDao knowledgeLevelDao;
	private final TeacherDao teacherDao;
	private final KnowledgeGroupDao knowledgeGroupDao;

    @Inject
    public KnowledgeLevelResource(final KnowledgeLevelDao knowledgeLevelDao,
    		final TeacherDao teacherDao, 
    		final KnowledgeGroupDao knowledgeGroupDao) {
        this.knowledgeLevelDao = knowledgeLevelDao;
        this.teacherDao = teacherDao;
        this.knowledgeGroupDao = knowledgeGroupDao;
    }
	
    @GET
	@Path("/{id}")
	public Response retrieveKnowledgeLevelById(@PathParam("id") Long id, @Context final HttpServletRequest request) {
    	if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
    	
    	KnowledgeLevel knowledgeLevel = knowledgeLevelDao.findById(id, 1);
		if (knowledgeLevel == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(knowledgeLevel)
				.build();
	}
    
	@GET
    public Response retrieveAllKnowledgeLevels(@QueryParam("page") Integer page, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		List<KnowledgeLevel> knowledgeLevels = knowledgeLevelDao.findAll(0);
		if (knowledgeLevels == null || knowledgeLevels.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<KnowledgeLevel> rsp = new ResultSetResponse<KnowledgeLevel>(knowledgeLevels, page);
		
		return Response.ok(rsp)
				.build();
    }
	
	@POST
	public Response insertKnowledgeLevel(@Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		KnowledgeLevel knowledgeLevel;
		try {
			knowledgeLevel = retrieveKnowledgeLevelFromJson(request);
			knowledgeLevelDao.insert(knowledgeLevel);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response.ok(knowledgeLevel)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateKnowledgeLevel(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		KnowledgeLevel knowledgeLevel;
		try {
			knowledgeLevel = retrieveKnowledgeLevelFromJson(request);
			knowledgeLevel.setId(id);
			knowledgeLevelDao.update(knowledgeLevel);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response.ok(knowledgeLevel)
				.build();
	}
	
	private KnowledgeLevel retrieveKnowledgeLevelFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		KnowledgeLevel knowledgeLevel = new KnowledgeLevel();
		
		Teacher teacher = teacherDao.findById(new Long(content.get("teacher_id").toString()), 0);
		knowledgeLevel.setTeacher(teacher);
		
		KnowledgeGroup knowledgeGroup = knowledgeGroupDao.findById(new Long(content.get("knowledge_id").toString()), 0);
		knowledgeLevel.setKnowledgeGroup(knowledgeGroup);
		
		return knowledgeLevel;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteKnowledgeLevel(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		try {
			knowledgeLevelDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
}
