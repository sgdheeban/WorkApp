package com.workappinc.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.util.Properties;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

/**
 * File Reader Example Test file
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesFileReaderExample
{
	/**
	 * Testing reading from classpath
	 * 
	 * @param logger
	 * @param classFilePath
	 */

	public static void printPropertiesFromClassPath(IApplicationLogger logger, String classFilePath)
	{
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
		logger.LogInfo("Printing Properties Loaded from Class Path", WorkAppPropertiesFileReaderExample.class);
		Properties prop = propertiesFileReader.loadPropertyFromClassPath(classFilePath);
		logger.LogInfo("Database: " + prop.getProperty("database"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("DBUser" + prop.getProperty("dbuser"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("DBPassword" + prop.getProperty("dbpassword"), WorkAppPropertiesFileReaderExample.class);
	}

	/**
	 * Testing reading from filesystem
	 * 
	 * @param logger
	 * @param fileSystemPath
	 */
	public static void printPropertiesFromFileSystem(IApplicationLogger logger, String fileSystemPath)
	{
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
		logger.LogInfo("Printing Properties Loaded from FileSystem Path", WorkAppPropertiesFileReaderExample.class);
		Properties prop = propertiesFileReader.loadPropertyFromFileSystem(fileSystemPath);
		logger.LogInfo("Database: " + prop.getProperty("database"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("DBUser" + prop.getProperty("dbuser"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("DBPassword" + prop.getProperty("dbpassword"), WorkAppPropertiesFileReaderExample.class);
	}

	/**
	 * Testing loading Log4j properties format
	 * 
	 * @param logger
	 * @param fileSystemPath
	 */
	public static void print4jPropertiesFromFileSystem(IApplicationLogger logger, String fileSystemPath)
	{
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
		logger.LogInfo("Printing Properties Loaded from FileSystem Path", WorkAppPropertiesFileReaderExample.class);
		Properties prop = propertiesFileReader.loadPropertyFromFileSystem(fileSystemPath);
		logger.LogInfo("log4j.rootLogger: " + prop.getProperty("log4j.rootLogger"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("log4j.appender.stdout: " + prop.getProperty("log4j.appender.stdout"), WorkAppPropertiesFileReaderExample.class);
		logger.LogInfo("log4j.appender.stdout.Target: " + prop.getProperty("log4j.appender.stdout.Target"), WorkAppPropertiesFileReaderExample.class);
	}

	public static void main(String args[]) throws IOException
	{
		String classFilePath = "config.properties";
		String fileSystemPath = "/home/dhgovindaraj/Documents/git_clone/WorkApp/WorkAppServer/config/config.properties";
		String fileSystemLog4jPath = "/home/dhgovindaraj/Documents/git_clone/WorkApp/WorkAppServer/config/log4j.properties";

		IApplicationLogger logger = WorkAppLogger.getInstance(null);

		printPropertiesFromClassPath(logger, classFilePath);
		printPropertiesFromFileSystem(logger, fileSystemPath);
		print4jPropertiesFromFileSystem(logger, fileSystemLog4jPath);

	}
}
