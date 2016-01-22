package com.workappinc.workappserver.businesslogic.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jhades.JHades;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppAllocationTrackerUtil;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppScriptRunnerUtil;
import com.workappinc.workappserver.presentation.WorkAppRestServer;

/**
 * 
 * WorkAppMainServer is the main entrypoint for WorkApp sever project
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMainServer
{
	@WorkAppArgument(alias = "c", description = "Config File Location (Required)", required = true)
	private static String configFile;

	@WorkAppArgument(alias = "db", description = "DB Config File Location")
	private static String dbConfigFile;

	@WorkAppArgument(alias = "m", description = "Start Mode - Choose between rest, thrift, batch")
	private static String mode;

	@WorkAppArgument(alias = "p", description = "Start Port")
	private static Integer port;

	@WorkAppArgument(alias = "t", description = "Track Allocations")
	private static Boolean trackAllocation;

	@WorkAppArgument(alias = "dj", description = "Detect JAR Clash")
	private static Boolean detectJARClash;

	@WorkAppArgument(alias = "s", description = "DB Schema File Location")
	private static String schemaFile;

	@WorkAppArgument(alias = "l", description = "Log Level")
	private static String logLevel;

	@WorkAppArgument(alias = "lp", description = "Log4j Properties File Location")
	private static String log4jPropFile;

	private static String printArray(String[] arr)
	{
		if (arr == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		for (String s : arr)
		{
			sb.append(s + ",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Private Static Variable Declaration
	 */
	private static IApplicationLogger logger = null;
	private static String database = null;
	private static String dbUser = null;
	private static String dbPassword = null;
	private static String dbSchema = null;
	private static int dbPoolSize = -1;
	private static final String REST_SERVICE = "rest";
	private static final String THRIFT_SERVICE = "thrift";

	/**
	 * Print terminating condition and end program
	 */
	private static void terminateWithMessage()
	{
		WorkAppCommandLineArgsReader.usage(WorkAppMainServer.class);
		System.err.println("Also please ensure you entered correct values for DBConfig file.");
		terminate();
	}

	private static void terminate()
	{
		System.exit(1);
		return;
	}

	/**
	 * Sets values fromm Config File, if not set from command line already
	 * 
	 * @param configFile2
	 */
	private static void setValuesFromConfig(String configFile, IApplicationLogger logger)
	{
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
		Properties prop = propertiesFileReader.loadPropertyFromFileSystem(configFile);

		if (mode == null)
		{
			mode = prop.getProperty("mode");
		}
		if (port == null)
		{
			port = Integer.parseInt(prop.getProperty("port"));
		}
		if (trackAllocation == null)
		{
			trackAllocation = Boolean.parseBoolean(prop.getProperty("trackAllocation"));
		}
		if (detectJARClash == null)
		{
			detectJARClash = Boolean.parseBoolean(prop.getProperty("detectJARClash"));
		}
		if (dbConfigFile == null)
		{
			dbConfigFile = prop.getProperty("dbConfigFile");
		}
		if (schemaFile == null)
		{
			schemaFile = prop.getProperty("schemaFile");
		}

		// Must be passed
		if (mode == null || port == null || dbConfigFile == null)
		{
			terminateWithMessage();
		}

		// Read DBConfig to memory
		if (dbConfigFile != null)
		{
			Properties dbProp = propertiesFileReader.loadPropertyFromFileSystem(dbConfigFile);
			database = dbProp.getProperty("database");
			dbUser = dbProp.getProperty("dbuser");
			dbPassword = dbProp.getProperty("dbpassword");
			dbSchema = dbProp.getProperty("dbschema");
			if (dbProp.getProperty("dbpoolsize") != null)
				dbPoolSize = Integer.parseInt(dbProp.getProperty("dbpoolsize"));
		}

		// Must be passed
		if (database == null || dbUser == null || dbPassword == null || dbSchema == null)
		{
			terminateWithMessage();
		}
	}

	/**
	 * Create Logger from Properties file
	 * 
	 * @return IApplicationLogger
	 */
	private static IApplicationLogger createLoggerFromProperties()
	{
		IApplicationLogger mLogger = null;
		Properties log4jProp = new Properties();
		InputStream input = null;
		try
		{
			input = new FileInputStream(log4jPropFile);
			log4jProp.load(input);
			mLogger = WorkAppLogger.getInstance(log4jProp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			System.out.println("Check your Log4J properties file format / content. ");
			System.exit(1);
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		return mLogger;
	}

	/**
	 * Override Log4j Log-level with command line option if any
	 */
	private static void setLogLevel()
	{
		if (logLevel != null)
			switch (logLevel.toUpperCase())
			{
			case "ALL":
				Logger.getRootLogger().setLevel(Level.TRACE);
				break;
			case "DEBUG":
				Logger.getRootLogger().setLevel(Level.DEBUG);
				break;
			case "ERROR":
				Logger.getRootLogger().setLevel(Level.ERROR);
				break;
			case "FATAL":
				Logger.getRootLogger().setLevel(Level.FATAL);
				break;
			case "INFO":
				Logger.getRootLogger().setLevel(Level.INFO);
				break;
			case "OFF":
				Logger.getRootLogger().setLevel(Level.OFF);
				break;
			case "TRACE":
				Logger.getRootLogger().setLevel(Level.TRACE);
				break;
			case "WARN":
				Logger.getRootLogger().setLevel(Level.WARN);
				break;
			default:
				Logger.getRootLogger().setLevel(Level.DEBUG);
				break;
			}
	}

	/**
	 * Optional method to print startup config values
	 * 
	 * @param logger
	 */
	private static void printConfigValues(IApplicationLogger logger)
	{
		logger.LogDebug("configFile:" + configFile, WorkAppMainServer.class);
		logger.LogDebug("log4jPropFile:" + log4jPropFile, WorkAppMainServer.class);
		logger.LogDebug("logLevel:" + logLevel, WorkAppMainServer.class);
		logger.LogDebug("mode:" + mode, WorkAppMainServer.class);
		logger.LogDebug("port:" + port, WorkAppMainServer.class);
		logger.LogDebug("trackAllocation:" + trackAllocation, WorkAppMainServer.class);
		logger.LogDebug("detectJARClash:" + detectJARClash, WorkAppMainServer.class);
		logger.LogDebug("dbConfigFile:" + dbConfigFile, WorkAppMainServer.class);
		logger.LogDebug("schemaFile:" + schemaFile, WorkAppMainServer.class);
		logger.LogDebug("database:" + database, WorkAppMainServer.class);
		logger.LogDebug("dbUser:" + dbUser, WorkAppMainServer.class);
		logger.LogDebug("dbPassword:" + dbPassword, WorkAppMainServer.class);
		logger.LogDebug("dbSchema:" + dbSchema, WorkAppMainServer.class);
		logger.LogDebug("dbPoolSize:" + dbPoolSize, WorkAppMainServer.class);
	}

	/**
	 * Static Main method called on start of the executable, instantiates the
	 * server
	 * 
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
		@SuppressWarnings("unused")
		final List<String> parse;

		try
		{
			parse = WorkAppCommandLineArgsReader.parse(WorkAppMainServer.class, args);
		}
		catch (IllegalArgumentException e)
		{
			terminateWithMessage();
		}

		// Instantiate Logger & override levels from command line
		if (log4jPropFile == null)
			logger = WorkAppLogger.getInstance(null);
		else
		{
			logger = createLoggerFromProperties();
		}
		setLogLevel();

		// Read config file and update Properties, if not already set from
		// command line
		setValuesFromConfig(configFile, logger);
		printConfigValues(logger);

		// Detect JAR Clash and stop
		if (detectJARClash)
		{
			new JHades().overlappingJarsReport();
			System.err.println("Please ensure JAR dependency clashes are cleaned up.\n");
			terminate();
		}

		// If SchemaFile not null, create schema using DB Utility and stop
		// Change from singleton to prototype for WorkAppScriptRunner
		if (schemaFile != null)
		{
			if (database != null || dbUser != null || dbPassword != null || dbSchema != null)
			{
				try
				{
					String dbClass = "com.mysql.jdbc.Driver";
					Class.forName(dbClass);
					Connection connection = DriverManager.getConnection(database, dbUser, dbPassword);
					WorkAppScriptRunnerUtil runner = new WorkAppScriptRunnerUtil(connection, false, false, logger);
					runner.runScript(new BufferedReader(new FileReader(schemaFile)));
					connection.close();
				}
				catch (Exception ex)
				{
					logger.LogException(ex, WorkAppMainServer.class);
					System.err.println("Error executing Schema File. Please Check File or DB Connection.\n");
				}
			}
			else System.err.println("Please ensure you have provided schemaFile and DB Config details. \n");

			terminate();
		}

		// Get an Instance of Object Allocation Tracker - if mode on from
		// configuration
		if (trackAllocation)
		{
			logger.LogDebug("Don't forgot to add JVM param -javaagent:<location-to>/java-allocation-instrumenter-3.0.jar", WorkAppMainServer.class);
			// Track Heap Allocations
			WorkAppAllocationTrackerUtil.trackHeapAllocation(logger);
			// Track Custom Constructor Allocations (you can write your own
			// constructor method to track like below example)
			// WorkAppAllocationTrackerUtil.trackConstructorAllocationTest(logger);
		}

		// If DB config not null - Instantiate Essentials
		// Get an instance of Connection Manager - use connection-string from
		// dbconfig
		// Pass this reference to Service Layer
		if (database != null || dbUser != null || dbPassword != null || dbSchema != null)
		{

		}

		// Start an Jetty-HTTP or Thrift server to serve requests - use
		// mode/port info from config
		if (mode.equalsIgnoreCase(REST_SERVICE))
		{
			WorkAppRestServer.startServer(port.intValue(), logger);
		}

	}

};
