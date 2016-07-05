package com.workapp.workappserver.dataaccess.resources.implementation;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.workapp.workappserver.common.exception.SingletonInitException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.dataaccess.resources.interfaces.IConnectionManager;
import com.workapp.workappserver.dataaccess.resources.interfaces.IReader;

/**
 * 
 * WorkAppMySQLConnectionManager is a singleton implementation of Closeable and
 * IConnectionManager interface to handle MySQL Connection Pooling
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMySQLConnectionManager implements Closeable, IConnectionManager
{
	private static int poolsize = 10;
	private static long timeToLive = 300000;
	private static long threadSleepTime = 300000;
	private static Vector<WorkAppJDBCConnection> connections;
	private final ConnectionVerifier verifier;
	private final String url;
	private final String user;
	private final String password;
	private final String dbClass = "com.mysql.jdbc.Driver";
	private IApplicationLogger mLogger = null;
	private static IConnectionManager mInstance = null;

	/**
	 * Constructor for Instantiating WorkAppMySQLConnectionManager
	 * 
	 * @param url
	 *            URL of the MySQL Connection String
	 * @param user
	 *            User to access MySQL database
	 * @param password
	 *            to access MySQL database
	 * @throws ClassNotFoundException
	 */
	public WorkAppMySQLConnectionManager(String url, String user, String password, int poolsize, IApplicationLogger logger) throws ClassNotFoundException
	{
		Class.forName(dbClass);
		this.url = url;
		this.user = user;
		this.password = password;
		this.mLogger = logger;
		if (poolsize != -1)
			this.poolsize = poolsize;
		mLogger.LogDebug("Attempting to connect to db at: " + url, WorkAppMySQLConnectionManager.class);
		connections = new Vector<WorkAppJDBCConnection>(this.poolsize);
		verifier = new ConnectionVerifier();
		verifier.start();
	}

	/**
	 * Close the Connection Manager, closes and removes all active connections
	 */
	public synchronized void close()
	{
		mLogger.LogDebug("Closing all MySQL connections!", WorkAppMySQLConnectionManager.class);
		final Enumeration<WorkAppJDBCConnection> conns = connections.elements();
		while (conns.hasMoreElements())
		{
			final WorkAppJDBCConnection conn = conns.nextElement();
			connections.remove(conn);
			conn.terminate();
		}
	}

	/**
	 * Gets an active connection from the pool
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized WorkAppJDBCConnection getConnection() throws SQLException
	{
		WorkAppJDBCConnection conn;
		for (int i = 0; i < connections.size(); i++)
		{
			conn = connections.get(i);
			if (conn.lease())
			{
				if (conn.isValid()) { return conn; }
				mLogger.LogDebug("removing dead MySQL connection", WorkAppMySQLConnectionManager.class);
				connections.remove(conn);
				conn.terminate();
			}
		}
		mLogger.LogDebug("No available MySQL connections, attempting to create a new one", WorkAppMySQLConnectionManager.class);
		conn = new WorkAppJDBCConnection(DriverManager.getConnection(url, user, password), mLogger);
		conn.lease();
		if (!conn.isValid())
		{
			mLogger.LogException("SqlException-Could not create new connection", WorkAppMySQLConnectionManager.class);
			conn.terminate();
			throw new SQLException("Could not create new connection");
		}
		connections.add(conn);
		return conn;
	}

	/**
	 * Removes the specified connection from the connection pool
	 * 
	 * @param conn
	 *            Connection object to be closed
	 */
	public static synchronized void removeConn(Connection conn)
	{
		connections.remove(conn);
	}

	private synchronized void verifyConnections()
	{
		mLogger.LogDebug("Attempting to remove dead connections", WorkAppMySQLConnectionManager.class);
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
		mLogger.LogDebug(count + " dead connections removed", WorkAppMySQLConnectionManager.class);
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
					Thread.sleep(threadSleepTime);
				}
				catch (final InterruptedException e)
				{
				}
				verifyConnections();
			}
		}
	}
}
