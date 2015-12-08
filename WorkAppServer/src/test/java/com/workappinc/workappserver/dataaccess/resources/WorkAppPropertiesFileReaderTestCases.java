package com.workappinc.workappserver.dataaccess.resources;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

/**
 * Test suites for Properties File Reader
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesFileReaderTestCases
{
	/**
	 * Testing loading from classpath
	 */
	@Test
	public void printPropertiesFromClassPath()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String classFilePath = "config.properties";
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader
				.getInstance(logger);
		Properties prop = propertiesFileReader.loadPropertyFromClassPath(classFilePath);
		assertEquals("localhost", prop.getProperty("database"));
		assertEquals("dheeban", prop.getProperty("dbuser"));
		assertEquals("password", prop.getProperty("dbpassword"));
	}

	/**
	 * Testing loading from filesystem
	 */
	@Test
	public void printPropertiesFromFileSystem()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String fileSystemPath = "/home/dhgovindaraj/Documents/git_clone/WorkApp/WorkAppServer/config/config.properties";
		WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader
				.getInstance(logger);
		Properties prop = propertiesFileReader.loadPropertyFromFileSystem(fileSystemPath);
		assertEquals("localhost", prop.getProperty("database"));
		assertEquals("dheeban", prop.getProperty("dbuser"));
		assertEquals("password", prop.getProperty("dbpassword"));
	}

}
