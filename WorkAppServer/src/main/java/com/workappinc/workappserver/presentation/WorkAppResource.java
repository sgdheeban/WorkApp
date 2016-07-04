package com.workappinc.workappserver.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/v1/")
public class WorkAppResource implements IResource
{

	private static IApplicationLogger _logger;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 */
	public static void initResource(IApplicationLogger logger)
	{
		_logger = logger;
	}

	/**
	 * Test
	 * 
	 * @return
	 */
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test()
	{
		return "Test";
	}

	/**
	 * Get value for a given key
	 * 
	 * @param response
	 * @return
	 */
	public Response get(String key)
	{
		Response response = null;
		return response;
	}

	/**
	 * Save a key-value pair
	 * 
	 * @param response
	 * @return
	 */
	public Response put(String key, String value)
	{
		Response response = null;
		return response;
	}

	/**
	 * Delete a key value pair
	 * 
	 * @param response
	 * @return
	 */
	public Response delete(String key)
	{
		Response response = null;
		return response;
	}
	
}
