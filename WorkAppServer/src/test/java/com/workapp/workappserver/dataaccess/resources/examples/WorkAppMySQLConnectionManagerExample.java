package com.workapp.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

/**
 * Test Example for WorkAppMySQLConnectionManager, test after ensuring test db
 * in MySQL is available in the local box. Also change user, password,
 * connection string for MySQL accordingly
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMySQLConnectionManagerExample
{
	WorkAppMySQLConnectionManager connections = null;

	public void testMySQLQueryUsingAdhocConnection(IApplicationLogger logger)
	{
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
			connection.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void testMySQLStatementUsingAdhocConnection(IApplicationLogger logger)
	{
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
			connection.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public WorkAppJDBCConnection getConnection(IApplicationLogger logger)
	{
		try
		{
			return connections.getConnection();
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public void testMySQLQueryUsingConnectionManager(IApplicationLogger logger)
	{
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String query = "Select * from testdb.user";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		try
		{
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password, -1, logger);
			conn = getConnection(logger);
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
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
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				conn.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void testMySQLPreparedStatementUsingConnectionManager(IApplicationLogger logger)
	{
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

		try
		{
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password, -1, logger);
			conn = getConnection(logger);

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
			conn = getConnection(logger);

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
			conn = getConnection(logger);

			// SELECT Query
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(selectQuery);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
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
				conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void testMySQLQueryForTransactionsUsingConnectionManager(IApplicationLogger logger)
	{
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String query = "Select * from testdb.user";
		String username = "root";
		String password = "password";
		WorkAppJDBCConnection conn = null;
		Statement stmnt = null;
		ResultSet resultSet = null;
		try
		{
			conn = getConnection(logger);
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
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
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				conn.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void testMySQLTransactionsUsingConnectionManager(IApplicationLogger logger)
	{
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

		try
		{
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password, -1, logger);
			conn = getConnection(logger);

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
				if (resultSet != null)
				{
					resultSet.close();
				}

				if (stmnt != null)
				{
					stmnt.close();
				}
				conn.close();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException, SystemException
	{
		IApplicationLogger logger = new AppLogger(null);
		WorkAppMySQLConnectionManagerExample app = new WorkAppMySQLConnectionManagerExample();
		app.testMySQLStatementUsingAdhocConnection(logger);
		app.testMySQLQueryUsingAdhocConnection(logger);
		app.testMySQLPreparedStatementUsingConnectionManager(logger);
		app.testMySQLTransactionsUsingConnectionManager(logger);
		app.testMySQLQueryForTransactionsUsingConnectionManager(logger);

		if (app.connections != null)
			app.connections.close();
	}
}
