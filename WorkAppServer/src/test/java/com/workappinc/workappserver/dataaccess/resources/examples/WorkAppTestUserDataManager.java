package com.workappinc.workappserver.dataaccess.resources.examples;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.dataaccess.entry.IEntry;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workappinc.workappserver.dataaccess.resources.interfaces.IDataManager;

/**
 * WorkAppTestUserDataManager is an example singleton implementation of
 * IDataManager
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppTestUserDataManager extends TimerTask implements IDataManager
{
	private IApplicationLogger mLogger = null;
	private static IDataManager mInstance = null;
	private final LinkedBlockingQueue<IEntry> insertQueue = new LinkedBlockingQueue<IEntry>();
	private String dbUrl = "jdbc:mysql://localhost:3306/";
	private String username = "root";
	private String password = "password";
	private WorkAppJDBCConnection conn = null;
	private PreparedStatement prpdstmnt = null;
	private Statement stmnt = null;
	private ResultSet resultSet = null;
	private WorkAppMySQLConnectionManager connections = null;
	public Timer loggingTimer = null;
	private Calendar cal = null;
	private SimpleDateFormat sdf = null;

	private final String insertSQL = "insert into testdb.user" + " values" + "(?,?)";

	private WorkAppTestUserDataManager(IApplicationLogger logger) throws ClassNotFoundException
	{
		sdf = new SimpleDateFormat("HH:mm:sss");
		mLogger = logger;
		connections = new WorkAppMySQLConnectionManager (dbUrl, username, password, -1, logger);
		loggingTimer = new Timer(); // Instantiate logging timer
		loggingTimer.scheduleAtFixedRate(this, 100000, 100000); // Start logging
		// timer
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IDataManager getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppTestUserDataManager.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppTestUserDataManager(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppTestUserDataManager Class", ex);
		}
	}

	/**
	 * Close resources used by WorkAppTestUserDataManager
	 */
	public void close()
	{
		if (connections != null)
			connections.close();

		if (loggingTimer != null)
			loggingTimer.cancel();
	}

	@Override
	public void addEntry(IEntry entry)
	{
		insertQueue.add(entry);
	}

	@Override
	public void run()
	{
		try
		{
			cal = Calendar.getInstance();
			conn = connections.getConnection();
			prpdstmnt = conn.prepareStatement(insertSQL);
			while (!insertQueue.isEmpty())
			{
				WorkAppUserTestEntry entry = (WorkAppUserTestEntry) insertQueue.poll();
				prpdstmnt.setString(1, entry.getName());
				prpdstmnt.setInt(2, entry.getAge());
				prpdstmnt.executeUpdate();
				mLogger.LogDebug("User Record (" + entry.getName() + "," + entry.getAge() + ") Inserted at " + sdf.format(cal.getTime()), WorkAppTestUserDataManager.class);
			}
		}
		catch (SQLException ex)
		{
			mLogger.LogException(ex, WorkAppTestUserDataManager.class);
		}
		finally
		{
			try
			{
				if (prpdstmnt != null)
					prpdstmnt.close();

				if (conn != null)
					conn.close();

				if (cal != null)
					cal.clear();
			}
			catch (SQLException ex)
			{
				mLogger.LogException(ex, WorkAppTestUserDataManager.class);
			}
		}

	}

}
