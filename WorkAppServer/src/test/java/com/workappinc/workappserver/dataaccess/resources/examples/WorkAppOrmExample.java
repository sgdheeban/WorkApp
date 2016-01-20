package com.workappinc.workappserver.dataaccess.resources.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.orm.annotations.Table;
import com.workappinc.workappserver.dataaccess.orm.core.Persist;
import com.workappinc.workappserver.dataaccess.resources.examples.testtable.User;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * Test ORM Example
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppOrmExample
{

	public void simpleTableORMExample()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/testdb";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		PreparedStatement prpdstmnt = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		WorkAppMySQLConnectionManager connections = null;

		String selectQuery = "select * from testdb.user";
		String insertSQL = "insert into testdb.user" + " values" + "(?,?)";
		String updateSQL = "update testdb.user set name =? " + "where name = ?";
		try
		{
			connections = (WorkAppMySQLConnectionManager) WorkAppMySQLConnectionManager.getInstance(dbUrl, username, password, logger);
			conn = connections.getConnection();
			Persist persist = new Persist(conn);
			List<User> users = persist.readList(User.class, "select * from user where age > ?", 900);
			System.out.println("Size: " + users.size());
			System.out.println("List of Retrieved Records:");
			for (User x : users)
			{
				System.out.println(x.toString());
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
					conn.close();
				if (connections != null)
					connections.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException
	{
		WorkAppOrmExample orm = new WorkAppOrmExample();
		orm.simpleTableORMExample();
	}

}
