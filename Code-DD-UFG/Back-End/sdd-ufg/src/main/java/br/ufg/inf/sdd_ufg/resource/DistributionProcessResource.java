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

import br.ufg.inf.sdd_ufg.dao.DistributionProcessDao;
import br.ufg.inf.sdd_ufg.model.DistributionProcess;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ResultSetResponse;

@Path("/processes")
@Produces(MediaType.APPLICATION_JSON)
public class DistributionProcessResource extends AbstractResource {

	private final DistributionProcessDao distributionProcessDao;

	@Inject
	public DistributionProcessResource(final DistributionProcessDao distributionProcessDao) {
		this.distributionProcessDao = distributionProcessDao;
	}

	@GET
	@Path("/{id}")
	public Response retrieveClazzById(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {

		DistributionProcess distributionProcess = distributionProcessDao.findById(id, 1);
		if (distributionProcess == null) {
			return getResourceNotFoundResponse();
		}
		return Response.ok(distributionProcess).build();
	}

	@GET
	public Response retrieveAllDistributionProcesss(@QueryParam("page") Integer page,
			@Context final HttpServletRequest request) {

		List<DistributionProcess> distributionProcesss = distributionProcessDao.findAll(0);
		if (page == null) {
			page = 1;
		}
		ResultSetResponse<DistributionProcess> rsp = new ResultSetResponse<DistributionProcess>(distributionProcesss,
				page);

		return Response.ok(rsp).build();
	}

	@POST
	public Response insertDistributionProcess(@Context final HttpServletRequest request,
			@Context UriInfo info) {
		DistributionProcess distributionProcessIntent;
		try {
			distributionProcessIntent = retrieveDistributionProcessFromJson(request);
			distributionProcessDao.insert(distributionProcessIntent);
		} catch (EntityExistsException eee) {
			return getInsertErrorResponse();
		} catch (Exception e) {
			return getBadRequestResponse();
		}

		URI location = info.getBaseUriBuilder().path("/processes")
				.path(distributionProcessIntent.getId().toString()).build();
		return Response
				.created(location)
				.header(HttpHeaders.SESSION_TOKEN.toString(),
						getLoggedUser(request).getSessionToken()).entity(distributionProcessIntent)
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateDistributionProcess(@PathParam("id") Long id,
			@Context final HttpServletRequest request, @Context UriInfo info) {
		DistributionProcess distributionProcess;
		try {
			distributionProcess = retrieveDistributionProcessFromJson(request);
			distributionProcess.setId(id);
			distributionProcessDao.update(distributionProcess);
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
						getLoggedUser(request).getSessionToken()).entity(distributionProcess)
				.build();
	}

	private DistributionProcess retrieveDistributionProcessFromJson(final HttpServletRequest request)
			throws Exception {
		Map<String, Object> content = getJSONContent(request);

		DistributionProcess distributionProcess = new DistributionProcess();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		distributionProcess.setSemester(content.get("semester").toString());
		distributionProcess.setClazzRegistryDate(df.parse(content.get("clazz_registry_date").toString()));
		distributionProcess.setTeacherIntentDate(df.parse(content.get("teacher_intent_date").toString()));
		distributionProcess.setFirstResolutionDate(df.parse(content.get("first_resolution_date").toString()));
		distributionProcess.setSubstituteDistribuitionDate(df.parse(content.get("substitute_distribution_date").toString()));
		distributionProcess.setFinishDate(df.parse(content.get("finish_date").toString()));
		
		return distributionProcess;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteDistributionProcess(@PathParam("id") Long id,
			@Context final HttpServletRequest request) {
		try {
			distributionProcessDao.delete(id);
		} catch (IllegalArgumentException iae) {
			return getResourceNotFoundResponse();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
