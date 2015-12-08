package com.workappinc.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

public class WorkAppMySQLConnectionManagerExample
{
	WorkAppMySQLConnectionManager connections = null;

	public void testMySQLQueryUsingAdhocConnection()
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

	public void testMySQLStatementUsingAdhocConnection()
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

	public WorkAppJDBCConnection getConnection()
	{
		try
		{
			return connections.getConnection();
		}
		catch (final SQLException ex)
		{
			// util.severe("Error while attempting to get connection: " + ex);
			return null;
		}
	}

	public void testMySQLQueryUsingConnectionManager()
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
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password);
			conn = getConnection();
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
		}
		catch (SQLException ex)
		{
			// util.severe("Unable to add player to database: " + ex);
		}
		catch (ClassNotFoundException ex)
		{
			// TODO Auto-generated catch block
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
				// util.severe("unable to close SQL connection: " + ex);
			}
		}
	}

	public void testMySQLPreparedStatementUsingConnectionManager()
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
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password);
			conn = getConnection();

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
			conn = getConnection();

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
			conn = getConnection();

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
			// util.severe("Unable to add player to database: " + ex);
		}
		catch (ClassNotFoundException ex)
		{
			// TODO Auto-generated catch block
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
				// util.severe("unable to close SQL connection: " + ex);
			}
		}
	}

	private void testMySQLQueryForTransactionsUsingConnectionManager()
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
			conn = getConnection();
			stmnt = conn.createStatement();
			resultSet = stmnt.executeQuery(query);

			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1) + "," + resultSet.getString(2));
			}
		}
		catch (SQLException ex)
		{
			// util.severe("Unable to add player to database: " + ex);
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
				// util.severe("unable to close SQL connection: " + ex);
			}
		}
	}

	public void testMySQLTransactionsUsingConnectionManager()
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
			connections = new WorkAppMySQLConnectionManager(dbUrl, username, password);
			conn = getConnection();

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
			// util.severe("Unable to add player to database: " + ex);
		}
		catch (ClassNotFoundException ex)
		{
			// TODO Auto-generated catch block
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
				// util.severe("unable to close SQL connection: " + ex);
			}
		}
	}

	public static void main(String args[]) throws IOException
	{
		WorkAppMySQLConnectionManagerExample app = new WorkAppMySQLConnectionManagerExample();
		// app.testMySQLStatementUsingAdhocConnection();
		// app.testMySQLQueryUsingAdhocConnection();
		// app.testMySQLPreparedStatementUsingConnectionManager();
		app.testMySQLTransactionsUsingConnectionManager();
		app.testMySQLQueryForTransactionsUsingConnectionManager();
	}
}
