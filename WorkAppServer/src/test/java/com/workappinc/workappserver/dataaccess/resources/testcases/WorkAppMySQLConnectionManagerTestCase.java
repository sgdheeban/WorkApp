package com.workappinc.workappserver.dataaccess.resources.testcases;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Ignore;
import org.junit.Test;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * Test Cases for WorkAppMySQLConnectionManager, turned off by default. Turn on
 * to test after ensuring test db in MySQL is available in the local box. Also
 * change user, password, connection string for MySQL accordingly.
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMySQLConnectionManagerTestCase
{
	/**
	 * Testing MySQL Queries using Adhoc Connection
	 */
	@Test
	public void testMySQLQueryUsingAdhocConnection()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String dbClass = "com.mysql.jdbc.Driver";
		// String query = "Select distinct(table_name) from
		// INFORMATION_SCHEMA.TABLES";
		String query = "Select * from testdb.user";
		String username = "root";
		String password = "password";
		try
		{
			Class.forName(dbClass);
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				// String tableName = resultSet.getString(1);
				// System.out.println("Table name : " + tableName);
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
			if (connection != null)
				connection.close();
			assertTrue(true);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			assertTrue(false);
			return;
		}
	}

	/**
	 * Testing MySQL statements using Adhoc Connection
	 */
	@Test
	public void testMySQLStatementUsingAdhocConnection()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String dbClass = "com.mysql.jdbc.Driver";
		// String query = "Select distinct(table_name) from
		// INFORMATION_SCHEMA.TABLES";
		String query = "insert into testdb.user values ('dheeban22',29)";
		String username = "root";
		String password = "password";
		try
		{
			Class.forName(dbClass);
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			Statement statement = connection.createStatement();
			statement.execute(query);
			if (connection != null)
				connection.close();
			assertTrue(true);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			assertTrue(false);
			return;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			assertTrue(false);
			return;
		}
	}

	/**
	 * Testing MySQL queries using Connection Manager
	 */
	@Test
	public void testMySQLQueryUsingConnectionManager()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String query = "Select * from testdb.user";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		WorkAppMySQLConnectionManager connections = null;
		try
		{
			connections = (WorkAppMySQLConnectionManager) WorkAppMySQLConnectionManager.getInstance(dbUrl, username, password, -1, logger);
			conn = connections.getConnection();
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
			assertTrue(true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
			return;
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				if (conn != null)
					conn.close();
				if (connections != null)
					connections.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Testing prepared statements using Connection Manager
	 */
	@Test
	public void testMySQLPreparedStatementUsingConnectionManager()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
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
			connections = (WorkAppMySQLConnectionManager) WorkAppMySQLConnectionManager.getInstance(dbUrl, username, password, -1, logger);
			conn = connections.getConnection();

			// INSERT Query
			prpdstmnt = conn.prepareStatement(insertSQL);
			prpdstmnt.setString(1, "dheeban88");
			prpdstmnt.setInt(2, 999);
			prpdstmnt.executeUpdate();
			if (prpdstmnt != null)
			{
				prpdstmnt.close();
			}

			conn.close();
			conn = connections.getConnection();

			// UPDATE Query
			prpdstmnt = conn.prepareStatement(updateSQL);
			prpdstmnt.setString(1, "dheeban98");
			prpdstmnt.setString(2, "dheeban88");
			prpdstmnt.executeUpdate();
			if (prpdstmnt != null)
			{
				prpdstmnt.close();
			}

			conn.close();
			conn = connections.getConnection();

			// SELECT Query
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(selectQuery);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
			assertTrue(true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
			return;
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

	/**
	 * Testing SQL queries without Transaction using Connection Manager
	 * 
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testMySQLQueryForTransactionsUsingConnectionManager()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String query = "Select * from testdb.user";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		WorkAppMySQLConnectionManager connections = null;
		try
		{
			connections = (WorkAppMySQLConnectionManager) WorkAppMySQLConnectionManager.getInstance(dbUrl, username, password, -1, logger);
			conn = connections.getConnection();
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
			assertTrue(true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
			return;
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				if (conn != null)
					conn.close();
				if (connections != null)
					connections.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Testing MySQL Transactions using Connection Manager
	 */
	@Test
	public void testMySQLTransactionsUsingConnectionManager()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		PreparedStatement prpdstmnt = null;
		Statement stmnt = null;
		ResultSet resultSet = null;

		String selectQuery = "select * from testdb.user";
		String insertSQL = "insert into testdb.user" + " values" + "(?,?)";
		String updateSQL = "update testdb.user set name =? " + "where name = ?";
		WorkAppMySQLConnectionManager connections = null;
		try
		{
			connections = (WorkAppMySQLConnectionManager) WorkAppMySQLConnectionManager.getInstance(dbUrl, username, password, -1, logger);
			conn = connections.getConnection();

			conn.setAutoCommit(false); // transaction block start

			// INSERT Query
			prpdstmnt = conn.prepareStatement(insertSQL);
			prpdstmnt.setString(1, "dheeban88");
			prpdstmnt.setInt(2, 999);
			prpdstmnt.executeUpdate();
			if (prpdstmnt != null)
			{
				prpdstmnt.close();
			}

			// UPDATE Query
			prpdstmnt = conn.prepareStatement(updateSQL);
			prpdstmnt.setString(1, "dheeban98");
			prpdstmnt.setString(2, "dheeban88");
			prpdstmnt.executeUpdate();
			if (prpdstmnt != null)
			{
				prpdstmnt.close();
			}

			System.out.println("Write Done. Not Committed Yet !");

			conn.commit();
			System.out.println("Commit Done !");

			// SELECT Query
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(selectQuery);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
			assertTrue(true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
			return;
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				if (conn != null)
					conn.close();
				if (connections != null)
					connections.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

}
