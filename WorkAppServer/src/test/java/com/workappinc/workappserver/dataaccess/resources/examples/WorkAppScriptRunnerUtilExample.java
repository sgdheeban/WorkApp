package com.workappinc.workappserver.dataaccess.resources.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppScriptRunnerUtil;

/**
 * WorkAppScriptRunnerExample tests execution of SQL scripts
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppScriptRunnerUtilExample
{

	public static void main(String args[]) throws IOException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
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
