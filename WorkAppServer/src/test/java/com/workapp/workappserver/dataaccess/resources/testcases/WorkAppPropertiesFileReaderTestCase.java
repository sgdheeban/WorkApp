package com.workapp.workappserver.dataaccess.resources.testcases;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.WorkAppLogger;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

/**
 * Test suites for Properties File Reader
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesFileReaderTestCase
{
	/**
	 * Testing loading from classpath
	 */
	@Test
	public void printPropertiesFromClassPath()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String classFilePath = "config.properties";
			WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
			Properties prop = propertiesFileReader.loadPropertyFromClassPath(classFilePath);
			assertEquals("localhost", prop.getProperty("database"));
			assertEquals("dheeban", prop.getProperty("dbuser"));
			assertEquals("password", prop.getProperty("dbpassword"));
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Testing loading from filesystem
	 */
	@Test @Ignore
	public void printPropertiesFromFileSystem()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String fileSystemPath = "/home/dhgovindaraj/Documents/git_clone/WorkApp/WorkAppServer/config/config.properties";
			WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(logger);
			Properties prop = propertiesFileReader.loadPropertyFromFileSystem(fileSystemPath);
			assertEquals("jdbc:mysql://localhost:3306/", prop.getProperty("database"));
			assertEquals("root", prop.getProperty("dbuser"));
			assertEquals("password", prop.getProperty("dbpassword"));
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

	}

}
