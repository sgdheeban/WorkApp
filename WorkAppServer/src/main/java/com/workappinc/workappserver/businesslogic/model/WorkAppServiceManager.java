package com.workappinc.workappserver.businesslogic.model;

import java.util.HashMap;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

public class WorkAppServiceManager
{
	private static WorkAppMySQLConnectionManager _connections;
	private static HashMap<String, Object> _configMap;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 */
	public static void initResource (WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap)
	{
		_connections = connections;
		_configMap = configMap;
	}


}
