package com.workappinc.workappclient.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

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

	@WorkAppArgument(alias = "ac", description = "Auto Complete File Location")
	private static String acFile;

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
	private static final HashMap<String, Quartet<String, String, String, String>> commandMap = new HashMap<String, Quartet<String, String, String, String>>();
	private static final String GET = "GET";
	private static final String POST = "POST";

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
		if (acFile == null)
		{
			acFile = prop.getProperty("acFile");
		}

		// Must be passed
		if (mode == null || host == null || port == null || acFile == null)
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
		logger.LogDebug("acFile:" + acFile, WorkAppClient.class);
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
		configMap.put("acFile", acFile);
	}

	private static void usageRestAPI()
	{
		StringBuilder outputString = new StringBuilder();
		outputString.append("Use the following command (Press TAB for auto complete in command line)");
		outputString.append("\n");
		outputString.append("test : Test the REST Service\n");
		outputString.append("get(key) : Get Value for a key\n");
		outputString.append("put(key,value) : Store a key-value pair\n");
		outputString.append("quit : Quit\n");
		System.out.println(outputString.toString());

		registerRestAPI();
	}

	private static void registerRestAPI()
	{
		commandMap.put(Command.test, new Quartet(GET, "http://" + host + ":" + port + "/workapp/v1/page/test", "text/plain", null));
	}

	private static void executeGetURL(String url, String contentType)
	{
		Client client =  null;
		try
		{
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			client = Client.create(clientConfig);
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.accept(contentType).get(ClientResponse.class);
			if (response.getStatus() != 200) { throw new RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
			String output = response.getEntity(String.class);
			System.out.println("Output from Server ....");
			System.out.println(output);
		}
		catch (Exception ex)
		{
			logger.LogException(ex, WorkAppClient.class);
		}
		finally
		{
			if(client != null)
				client.destroy();
		}
	}

	private static void executePostURL(String url, String contentType, String payload)
	{
		Client client =  null;
		try
		{
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			client = Client.create(clientConfig);
			WebResource webResource = client.resource(url);
			String input = payload;
			ClientResponse response = webResource.type(contentType).post(ClientResponse.class, input);
			if (response.getStatus() != 201) 
			{ 
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus()); 
			}
			System.out.println("Output from Server ....");
			String output = response.getEntity(String.class);
			System.out.println(output);
		}
		catch (Exception ex)
		{
			logger.LogException(ex, WorkAppClient.class);
		}
		finally
		{
			if(client != null)
				client.destroy();
		}
	}

	private static void executeURL(Quartet<String, String, String, String> quartet)
	{
		switch(quartet.getValue0())
		{
			case GET :
				executeGetURL(quartet.getValue1(), quartet.getValue2());
				break;
			case POST :
				executePostURL(quartet.getValue1(), quartet.getValue2(), quartet.getValue3());
				break;
			default:
				System.err.println("Wrong REST API Type chosen. Does not fit GET or PUT type. No action taken.");
				break;
		}
		
	}

	private static void startConsole()
	{
		InputStream input = null;
		try
		{
			ConsoleReader console = new ConsoleReader();
			console.setPrompt("WorkAppClient> ");

			// Get file from resources folder
			input = new FileInputStream(acFile);

			// console.addCompleter(new StringsCompleter(IOUtils.readLines(new
			// GZIPInputStream(input)))); //For tar.gz input
			console.addCompleter(new StringsCompleter(IOUtils.readLines(input)));
			console.addCompleter(new FileNameCompleter());
			String line = null;
			while ((line = console.readLine()) != null)
			{
				if (mode.equalsIgnoreCase(REST_CLIENT))
				{
					switch (line.toLowerCase())
					{
					case Command.test:
						executeURL(commandMap.get(Command.test));
						break;

					}
				}
			}
		}
		catch (IOException ex)
		{
			logger.LogException(ex, WorkAppClient.class);
		}
		finally
		{
			try
			{
				TerminalFactory.get().restore();
			}
			catch (Exception ex)
			{
				logger.LogException(ex, WorkAppClient.class);
			}

			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException ex)
				{
					logger.LogException(ex, WorkAppClient.class);
				}
			}
		}
	}

	public static void main(String args[]) throws IOException
	{
		System.setProperty("jline.shutdownhook", "true");
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

		if (mode != null && mode.equalsIgnoreCase(REST_CLIENT))
		{
			usageRestAPI();
		}
		startConsole();
	}
}
