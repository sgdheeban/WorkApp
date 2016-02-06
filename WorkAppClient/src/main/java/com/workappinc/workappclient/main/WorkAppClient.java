package com.workappinc.workappclient.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

public class WorkAppClient
{
	@WorkAppArgument(alias = "c", description = "Config File Location (Required)", required = true)
	private static String configFile;

	@WorkAppArgument(alias = "m", description = "Start Mode - Choose between rest, thrift, batch")
	private static String mode;

	@WorkAppArgument(alias = "h", description = "Start Host")
	private static String host;
	
	@WorkAppArgument(alias = "p", description = "Start Port")
	private static Integer port;

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
	private static final String REST_CLIENT = "rest_client";
	private static final String THRIFT_CLIENT = "thrift_client";
	private static final HashMap<String, Object> configMap = new HashMap<String, Object>();

	/**
	 * Print terminating condition and end program
	 */
	private static void terminateWithMessage()
	{
		WorkAppCommandLineArgsReader.usage(WorkAppClient.class);
		System.err.println("Also please ensure you entered correct values for Config file.");
		terminate();
	}

	private static void terminate()
	{
		System.exit(1);
		return;
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
			mLogger = new WorkAppLogger(log4jProp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			System.out.println("Check your Log4J properties file format / content. ");
			System.exit(1);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			System.out.println("Log4j logger errored out in Start. Check your Log4J properties file format / content. ");
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
				Logger.getRootLogger().setLevel(Level.WARN);
				break;
			}
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
		if (host == null)
		{
			host = prop.getProperty("host");
		}
		if (port == null)
		{
			port = Integer.parseInt(prop.getProperty("port"));
		}

		// Must be passed
		if (mode == null || host == null || port == null)
		{
			terminateWithMessage();
		}
	}

	/**
	 * Optional method to print startup config values
	 * 
	 * @param logger
	 */
	private static void printConfigValues(IApplicationLogger logger)
	{
		logger.LogDebug("configFile:" + configFile, WorkAppClient.class);
		logger.LogDebug("log4jPropFile:" + log4jPropFile, WorkAppClient.class);
		logger.LogDebug("logLevel:" + logLevel, WorkAppClient.class);
		logger.LogDebug("mode:" + mode, WorkAppClient.class);
		logger.LogDebug("host:" + host, WorkAppClient.class);
		logger.LogDebug("port:" + port, WorkAppClient.class);
	}

	/**
	 * Populate startup config values to pass in a map
	 * 
	 * @param logger
	 */
	private static void populateConfigValues()
	{
		configMap.put("configFile", configFile);
		configMap.put("log4jPropFile", log4jPropFile);
		configMap.put("logLevel", logLevel);
		configMap.put("mode", mode);
		configMap.put("host", host);
		configMap.put("port", port);
	}

	
	public static void main(String args[]) throws IOException
	{
		@SuppressWarnings("unused")
		final List<String> parse;

		try
		{
			parse = WorkAppCommandLineArgsReader.parse(WorkAppClient.class, args);
		}
		catch (IllegalArgumentException e)
		{
			terminateWithMessage();
		}
		
		// Instantiate Logger & override levels from command line
		if (log4jPropFile == null)
			try
			{
				logger = new WorkAppLogger(null);
			}
			catch (SystemException ex)
			{
				ex.printStackTrace();
				System.out.println("Log4j logger errored out in Start. Check your Log4J properties file format / content. ");
				System.exit(1);
			}
		else
		{
			logger = createLoggerFromProperties();
		}
		setLogLevel();

		// Read config file and update Properties, if not already set from
		// command line
		setValuesFromConfig(configFile, logger);
		printConfigValues(logger);
		populateConfigValues();

		if(mode != null && mode.equalsIgnoreCase(REST_CLIENT))
		{
			System.out.println("Hi...");
		}


	}
}
