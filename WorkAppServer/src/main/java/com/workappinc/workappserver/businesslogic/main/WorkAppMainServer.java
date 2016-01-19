package com.workappinc.workappserver.businesslogic.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppAllocationTrackerUtil;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

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
	 * Print terminating condition and end program
	 */
	private static void terminateWithMessage()
	{
		WorkAppCommandLineArgsReader.usage(WorkAppMainServer.class);
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
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader
				.getInstance(logger);
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
		logger.LogDebug("dbConfigFile:" + dbConfigFile, WorkAppMainServer.class);
		logger.LogDebug("schemaFile:" + schemaFile, WorkAppMainServer.class);
	}

	/**
	 * Static Main method called on start of the executable, instantiates the
	 * server
	 * 
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
		// Read Command-Line Options into Properties
		IApplicationLogger logger = null;
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

		// Get an Instance of Object Allocation Tracker - if mode on from
		// configuration
		if (trackAllocation)
		{
			logger.LogDebug(
					"Don't forgot to add JVM param -javaagent:<location-to>/java-allocation-instrumenter-3.0.jar",
					WorkAppMainServer.class);
			// Track Heap Allocations
			WorkAppAllocationTrackerUtil.trackHeapAllocation(logger);
			// Track Custom Constructor Allocations (you can write your own
			// constructor method to track like below example)
			// WorkAppAllocationTrackerUtil.trackConstructorAllocationTest(logger);
		}

		// Instantiate Essentials -
		// Get an instance of Connection Manager - use connection-string from
		// dbconfig
		// Get an instance of SQL Query Generator
		// Instantiate JSON Parser for reading any JSON Config
		// Get an instance of Entity Layer
		// Get an instance of Data Manager

		// Step10: Start an Jetty-HTTP or Thrift server to serve requests - use
		// mode/port info from config

	}

};
