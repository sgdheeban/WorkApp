package com.workappinc.workappserver.presentation;

import java.util.HashMap;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * An implementation of IResource to expose services for loading Client Pages -
 * Web or Mobile
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPageResource implements IResource
{

	private static WorkAppMySQLConnectionManager _connections;
	private static HashMap<String, Object> _configMap;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 */
	public static void initResource(WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap)
	{
		_connections = connections;
		_configMap = configMap;
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
