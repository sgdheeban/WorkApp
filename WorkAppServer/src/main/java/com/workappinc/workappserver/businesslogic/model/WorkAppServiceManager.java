package com.workappinc.workappserver.businesslogic.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.dataaccess.orm.core.Persist;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation.WorkAppQbFactoryImp;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbFactory;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;

public class WorkAppServiceManager
{
	private static WorkAppMySQLConnectionManager _connections;
	private static HashMap<String, Object> _configMap;
	private static IApplicationLogger _logger;
	private static WorkAppQbFactory _fac;
	private static  Persist _persist ;
	private static WorkAppJDBCConnection _conn ; 

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 * @throws SQLException 
	 */
	public static void initResource(WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap, IApplicationLogger logger) throws SQLException
	{
		_connections = connections;
		_configMap = configMap;
		_logger = logger;
		_fac = new WorkAppQbFactoryImp();
		_conn = _connections.getConnection() ;
		_persist = new Persist(_conn);
	}

	/**
	 * Registers new user
	 * 
	 * @param userInfo
	 * @System.out.println(select.getQueryString());return
	 */
	public static boolean registerUser(ArrayList<String> userInfo)
	{
		boolean isSuccess = false;
		String colname = "email" ;
		String table = "user";
		WorkAppQbSelect select = _fac.newSelectQuery();
		select.select(_fac.newCount(_fac.newStdField(colname), "cnt")).from(table);
		
		
		int count = _persist.read(int.class, select.getQueryString());
		
		System.out.println("Count: " + count);
		
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
