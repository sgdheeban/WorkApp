package com.workappinc.workappserver.presentation;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * An implementation of IResource to expose services for loading Client Pages - Web or Mobile
 * @author dhgovindaraj
 *
 */
public class WorkAppPageResource implements IResource
{
	/**
	 * Loads HomePage
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
	 * @param response
	 * @return
	 */
	public Response loadTalentPage(Request request)
	{
		Response response = null;
		return response;
	}
	
}
