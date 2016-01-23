package com.workappinc.workappserver.presentation;

import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services for loading Client Pages -
 * Web or Mobile
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/page/v1/")
public class WorkAppPageResource implements IResource
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
	 * Loads HomePage
	 * 
	 * @param response
	 * @return
	 */
	public Response loadHomePage(Request request)
	{
		Response response = null;
		return response;
	}

	/**
	 * Loads TalentPage
	 * 
	 * @param response
	 * @return
	 */
	public Response loadTalentPage(Request request)
	{
		Response response = null;
		return response;
	}

}
