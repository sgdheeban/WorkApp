package com.workappinc.workappserver.businesslogic.main;

import java.io.IOException;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;

/**
 * 
 * WorkAppMainServer is the main entrypoint for WorkApp sever project
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMainServer
{
	
	@WorkAppArgument(alias = "c", description = "Config File Location", required = true)
	private static String configFile;

	@WorkAppArgument(alias = "db", description = "DB Config File Location", required = true)
	private static String dbConfigFile;
	
	@WorkAppArgument(alias = "m", description = "Start Mode - Choose between Rest, Thrift, Batch")
	private static String mode;
	
	@WorkAppArgument(alias = "p", description = "Start Port")
	private static Integer port = 8080;

	@WorkAppArgument(alias = "s", description = "DB Schema File Location")
	private static String schemaFile;
	
	@WorkAppArgument(alias = "l", description = "Log Level")
	private static String logLevel;

	@WorkAppArgument(alias = "lp", description = "Log4j Properties File Location", required = true)
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
	 * Static Main method called on start of the executable, instantiates the
	 * server
	 * 
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
		// Step1: Read Command-Line Options into Properties
		// Step2 : Read config file and update Properties
		// Step3: Read Log config file, if any and get a Logger Instance
		// Step4: Instantiate JSON Parser for reading any JSON Config
		// Step5: Get an Instance of Object Allocation Tracker
		// Step6: Get an instance of SQL Query Generator and Data Access tools
		// Step7: Start an Jetty-HTTP or Thrift server to serve requests

		System.out.println("Hello World ! ");
		
		
	}

}
