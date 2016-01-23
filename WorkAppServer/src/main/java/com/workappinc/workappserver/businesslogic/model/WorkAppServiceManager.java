package com.workappinc.workappserver.businesslogic.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

public class WorkAppServiceManager
{
	private static WorkAppMySQLConnectionManager _connections;
	private static HashMap<String, Object> _configMap;
	private static IApplicationLogger _logger;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 */
	public static void initResource(WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap, IApplicationLogger logger)
	{
		_connections = connections;
		_configMap = configMap;
		_logger = logger;
	}

	/**
	 * Registers new user
	 * 
	 * @param userInfo
	 * @return
	 */
	public static boolean registerUser(ArrayList<String> userInfo)
	{
		boolean isSuccess = false;

		// Compose a Select SQL query to select user with this credential from
		// DB
		// Compose an Insert SQL query to select user with this credential from
		// DB

		// Get a connection from Pool
		// Check if user exists by running Select SQL query
		// if exists, return false
		// Else
		// Execute Insert SQL through the connection
		// Close connection & return success
		// If exception, return false

		return isSuccess;
	}

}
