package com.workappinc.workappserver.presentation;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.StopWatch;

import com.workappinc.workappserver.businesslogic.model.WorkAppServiceManager;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services for performing core site
 * services
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/core/v1/")
public class WorkAppCoreResource implements IResource
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
	 * Registers new User
	 * 
	 * @param response
	 * @return
	 */
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(Request request)
	{
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		Response response = null;
		ArrayList<String> userInfoList = null;
		boolean returnvalue = WorkAppServiceManager.registerUser(userInfoList);
		stopwatch.stop();
		if (returnvalue)
			return "Saved.";
		else return "Not Saved.";
	}

	/**
	 * Login User
	 * 
	 * @param response
	 * @return
	 */
	public Response loginUser(Request request)
	{
		Response response = null;
		return response;
	}

	/**
	 * Logout User
	 * 
	 * @param response
	 * @return
	 */
	public Response logoutUser(Request request)
	{
		Response response = null;
		return response;
	}

	/**
	 * Add Catalog Category
	 * 
	 * @param response
	 * @return
	 */
	public Response addCatalogCategory(Request request)
	{
		Response response = null;
		return response;
	}

	/**
	 * Get List of Catalog Sub-Categories - given a parent Catalog Name
	 * 
	 * @param response
	 * @return
	 */
	public Response getCatagorySubCategory(Request request)
	{
		Response response = null;
		return response;
	}

}
