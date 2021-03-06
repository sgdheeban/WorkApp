package com.workapp.workappserver.dataaccess.resources.testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.dataaccess.resources.examples.WorkAppPropertiesFileWriterExample;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileWriter;

/**
 * Test suite for writing properties to file system
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesFileWriterTestCase
{

	/**
	 * testing writing file to filesystem
	 */
	@Test
	public void writeToFileSystem()
	{
		try
		{
			String writepath = "config5.properties";
			IApplicationLogger logger = new AppLogger(null);
			Map<String, String> map = new HashMap<String, String>();
			map.put("database", "localhost");
			map.put("dbuser", "dheeban");
			map.put("dbpassword", "password");
			WorkAppPropertyFileWriter propertiesFileWriter = (WorkAppPropertyFileWriter) WorkAppPropertyFileWriter.getInstance(logger);
			logger.LogInfo("Printing Properties to FilePath", WorkAppPropertiesFileWriterExample.class);
			propertiesFileWriter.writeToFile(writepath, map);
			assertTrue(true);
		}
		catch (Exception ex)
		{
			assertTrue(false);
		}
	}

}
