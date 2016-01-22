package com.workappinc.workappserver.presentation;

import java.util.HashMap;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * An implementation of IResource to expose services for performing core site
 * services
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppCoreResource implements IResource
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
	 * Registers new User
	 * 
	 * @param response
	 * @return
	 */
	public Response registerUser(Request request)
	{
		Response response = null;
		return response;
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
