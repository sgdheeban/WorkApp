package com.workapp.workappserver.dataaccess.resources.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppScriptRunnerUtil;

/**
 * WorkAppScriptRunnerExample tests execution of SQL scripts
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppScriptRunnerUtilExample
{

	public static void main(String args[]) throws IOException, SystemException
	{
		IApplicationLogger logger = new AppLogger(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		WorkAppMySQLConnectionManager connections = null;

		try
		{
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password, -1, logger);
			conn = connections.getConnection();
			WorkAppScriptRunnerUtil runner = new WorkAppScriptRunnerUtil(conn, false, false, logger);

			String file = "src/main/resources/mysql_schema.sql";
			runner.runScript(new BufferedReader(new FileReader(file)));
			conn.close();
		}
		catch (Exception ex)
		{
			logger.LogException(ex, WorkAppScriptRunnerUtilExample.class);
		}
	}
}
