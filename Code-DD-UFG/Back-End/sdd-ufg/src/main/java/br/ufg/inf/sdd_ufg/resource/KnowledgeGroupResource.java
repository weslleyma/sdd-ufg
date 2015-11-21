package br.ufg.inf.sdd_ufg.resource;

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

import br.ufg.inf.sdd_ufg.dao.KnowledgeGroupDao;
import br.ufg.inf.sdd_ufg.model.KnowledgeGroup;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/knowledgeGroups")
@Produces(MediaType.APPLICATION_JSON)
public class KnowledgeGroupResource extends AbstractResource {

	private final KnowledgeGroupDao knowledgeGroupDao;

    @Inject
    public KnowledgeGroupResource(final KnowledgeGroupDao knowledgeGroupDao) {
        this.knowledgeGroupDao = knowledgeGroupDao;
    }
	
    @GET
	@Path("/{id}")
	public Response retrieveKnowledgeGroupById(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		KnowledgeGroup knowledgeGroup = knowledgeGroupDao.findById(id, 1);
		if (knowledgeGroup == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
    
	@GET
    public Response retrieveAllKnowledgeGroups(@QueryParam("page") Integer page, @Context final HttpServletRequest request) {
		List<KnowledgeGroup> knowledgeGroups = knowledgeGroupDao.findAll(0);
		if (knowledgeGroups == null || knowledgeGroups.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<KnowledgeGroup> rsp = new ResultSetResponse<KnowledgeGroup>(knowledgeGroups, page);
		
		return Response.ok(rsp)
				.build();
    }
	
	@POST
	public Response insertKnowledgeGroup(@Context final HttpServletRequest request) {
		KnowledgeGroup knowledgeGroup;
		try {
			knowledgeGroup = retrieveKnowledgeGroupFromJson(request);
			knowledgeGroupDao.insert(knowledgeGroup);
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateKnowledgeGroup(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		KnowledgeGroup knowledgeGroup;
		try {
			knowledgeGroup = retrieveKnowledgeGroupFromJson(request);
			knowledgeGroup.setId(id);
			knowledgeGroupDao.update(knowledgeGroup);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return Response.ok(knowledgeGroup)
				.build();
	}
	
	private KnowledgeGroup retrieveKnowledgeGroupFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		KnowledgeGroup knowledgeGroup = new KnowledgeGroup();
		knowledgeGroup.setName(content.get("name").toString());
		//knowledgeGroup.setGrade(content.get("cpf").toString());
		
		return knowledgeGroup;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteKnowledgeGroup(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		try {
			knowledgeGroupDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
}
