package com.workapp.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.dataaccess.orm.core.Persist;
import com.workapp.workappserver.dataaccess.resources.examples.testtable.User;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * Test ORM Example
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppOrmExample
{

	public void simpleTableORMExample() throws SystemException
	{
		IApplicationLogger logger = new AppLogger(null);
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
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password, -1, logger);
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
		catch (ClassNotFoundException ex)
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

	public static void main(String args[]) throws IOException, SystemException
	{
		WorkAppOrmExample orm = new WorkAppOrmExample();
		orm.simpleTableORMExample();
	}

}
