package com.workapp.workappserver.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.workapp.workappserver.businesslogic.model.ServiceManager;
import com.workapp.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/v1/")
public class WorkAppResource implements IResource {

	private static IApplicationLogger _logger;
	private static ServiceManager _serviceManager;

	/**
	 * Initialize Resources
	 * 
	 * @param logger
	 * @param serviceManager
	 */
	public static void initResource(IApplicationLogger logger, ServiceManager serviceManager) {
		_logger = logger;
		_serviceManager = serviceManager;
	}

	/**
	 * Test
	 * 
	 * @return
	 */
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	/**
	 * Get value for a given key
	 * 
	 * @param response
	 * @return
	 */
	@GET
	@Path("get")
	public Response get(@QueryParam("key") String key) {
		try {
			if(key != null)
			{
				String result = _serviceManager.get(key);
				return Response.status(200).entity(result).build();
			}
			else return Response.serverError().entity("Key can't be null").build();
		} catch (Exception ex) {
			return Response.serverError().entity(ex.getStackTrace()).build();
		}
	}

	/**
	 * Save a key-value pair
	 * 
	 * @param response
	 * @return
	 */
	@GET
	@Path("put")
	public Response put(@QueryParam("key") String key, @QueryParam("value") String value) {
		try {
			if(key != null)
			{
				 _serviceManager.put(key,value);
				return Response.status(200).build();
			}
			else  return Response.serverError().entity("Key can't be null").build();
		} catch (Exception ex) {
			return Response.serverError().entity(ex.getStackTrace()).build();
		}
	}

	/**
	 * Delete a key value pair
	 * 
	 * @param response
	 * @return
	 */
	@GET
	@Path("delete")
	public Response delete(@QueryParam("key") String key) {
		try {
			if(key != null)
			{
				_serviceManager.delete(key);
				return Response.status(200).build();
			}
			else  return Response.serverError().entity("Key can't be null").build();
			
		} catch (Exception ex) {
			return Response.serverError().entity(ex.getStackTrace()).build();
		}
	}

}
