package br.ufg.inf.sdd_ufg.resource;

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

import br.ufg.inf.sdd_ufg.dao.ClazzDao;
import br.ufg.inf.sdd_ufg.dao.ClazzScheduleDao;
import br.ufg.inf.sdd_ufg.dao.DistributionProcessDao;
import br.ufg.inf.sdd_ufg.dao.GradeDao;
import br.ufg.inf.sdd_ufg.model.Clazz;
import br.ufg.inf.sdd_ufg.model.ClazzSchedule;
import br.ufg.inf.sdd_ufg.model.DistributionProcess;
import br.ufg.inf.sdd_ufg.model.Grade;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/clazzs")
@Produces(MediaType.APPLICATION_JSON)
public class ClazzResource extends AbstractResource {

	private final ClazzDao clazzDao;
	private final GradeDao gradeDao;
	private final ClazzScheduleDao clazzScheduleDao;
	private final DistributionProcessDao distributionProcessDao;

    @Inject
    public ClazzResource(final ClazzDao clazzDao, final GradeDao gradeDao, 
    		final ClazzScheduleDao clazzScheduleDao,
    		final DistributionProcessDao distributionProcessDao) {
        this.clazzDao = clazzDao;
        this.gradeDao = gradeDao;
        this.clazzScheduleDao = clazzScheduleDao;
        this.distributionProcessDao = distributionProcessDao;
    }
	
    @GET
	@Path("/{id}")
	public Response retrieveClazzById(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
    	
    	Clazz clazz = clazzDao.findById(id, 1);
		if (clazz == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(clazz)
				.build();
	}
    
	@GET
    public Response retrieveAllClazzs(@QueryParam("page") Integer page, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		List<Clazz> clazzs = clazzDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<Clazz> rsp = new ResultSetResponse<Clazz>(clazzs, page);
		
		return Response.ok(rsp)
				.build();
    }
	
	@POST
	public Response insertClazz(@Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		Clazz clazz;
		try {
			clazz = retrieveClazzFromJson(request);
			clazzDao.insert(clazz);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}
		return Response.ok(clazz)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateClazz(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
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
		return Response.ok(clazz)
				.build();
	}
	
	@SuppressWarnings("unchecked")
	private Clazz retrieveClazzFromJson(final HttpServletRequest request) throws Exception {
		Map<String, Object> content = getJSONContent(request);
		
		Clazz clazz = new Clazz();
		clazz.setWorkload(new Integer(content.get("workload").toString()));
		
		Grade grade = gradeDao.findById(new Long(content.get("grade_id").toString()), 0);
		clazz.setGrade(grade);
		
		DistributionProcess dp = distributionProcessDao.findById(new Long(content.get("process_id").toString()), 0);
		clazz.setDistributionProcess(dp);
		
		clazz.setClazzSchedules(new ArrayList<ClazzSchedule>());
		ArrayList<Object> schedules = (ArrayList<Object>) content.get("schedules");
		for (Object schedule : schedules) {
			ClazzSchedule clazzSchedule = clazzScheduleDao.findById(new Long(schedule.toString()), 0);
			clazz.getClazzSchedules().add(clazzSchedule);
		}
		
		return clazz;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteClazz(@PathParam("id") Long id, @Context final HttpServletRequest request) {
		if (validateSession(request) == null) {
			return getAuthenticationErrorResponse();
		}
		
		try {
			clazzDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
}
