package com.workappinc.workappserver.dataaccess.resources.implementation;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.workappinc.workappserver.dataaccess.resources.interfaces.IConnectionManager;

public class WorkAppMySQLConnectionManager implements Closeable, IConnectionManager
{
	private static int poolsize = 10;
	private static long timeToLive = 300000;
	private static Vector<WorkAppJDBCConnection> connections;
	private final ConnectionVerifier verifier;
	private final String url;
	private final String user;
	private final String password;
	private final String dbClass = "com.mysql.jdbc.Driver";

	public WorkAppMySQLConnectionManager(String url, String user, String password) throws ClassNotFoundException
	{
		Class.forName(dbClass);
		// util.debug("Attempting to connect to db at: " + url);
		this.url = url;
		this.user = user;
		this.password = password;
		connections = new Vector<WorkAppJDBCConnection>(poolsize);
		verifier = new ConnectionVerifier();
		verifier.start();
	}

	public synchronized void close()
	{
		// util.debug("Closing all MySQL connections!");
		final Enumeration<WorkAppJDBCConnection> conns = connections.elements();
		while (conns.hasMoreElements())
		{
			final WorkAppJDBCConnection conn = conns.nextElement();
			connections.remove(conn);
			conn.terminate();
		}
	}

	public synchronized WorkAppJDBCConnection getConnection() throws SQLException
	{
		WorkAppJDBCConnection conn;
		for (int i = 0; i < connections.size(); i++)
		{
			conn = connections.get(i);
			if (conn.lease())
			{
				if (conn.isValid()) { return conn; }
				// util.debug("removing dead MySQL connection");
				connections.remove(conn);
				conn.terminate();
			}
		}
		// util.debug("No available MySQL connections, attempting to create a
		// new one");
		conn = new WorkAppJDBCConnection(DriverManager.getConnection(url, user, password));
		conn.lease();
		if (!conn.isValid())
		{
			conn.terminate();
			throw new SQLException("COuld not create new connection");
		}
		connections.add(conn);
		return conn;
	}

	public static synchronized void removeConn(Connection conn)
	{
		connections.remove(conn);
	}

	private synchronized void verifyConnections()
	{
		// util.debug("Attempting to remove dead connections");
		final long stale = System.currentTimeMillis() - timeToLive;
		final Enumeration<WorkAppJDBCConnection> conns = connections.elements();
		int count = 0;
		int i = 1;
		while (conns.hasMoreElements())
		{
			final WorkAppJDBCConnection conn = conns.nextElement();

			if (conn.inUse() && stale > conn.getLastUse() && !conn.isValid())
			{
				connections.remove(conn);
				count++;
			}

			if (i > poolsize)
			{
				connections.remove(conn);
				count++;
				conn.terminate();
			}
			i++;
		}
		// util.debug(count + " dead connections remove");
	}

	private class ConnectionVerifier extends Thread
	{

		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					Thread.sleep(300000);
				}
				catch (final InterruptedException e)
				{
				}
				verifyConnections();
			}
		}
	}
}
