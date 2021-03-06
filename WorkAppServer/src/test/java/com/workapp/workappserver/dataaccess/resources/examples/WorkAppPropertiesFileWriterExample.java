package com.workapp.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileWriter;

/**
 * Test file to write properties file to FileSystem
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesFileWriterExample
{

	/**
	 * Method to write file to filesystem
	 * 
	 * @param logger
	 * @param writepath
	 * @param map
	 */
	public static void writeToFileSystem(IApplicationLogger logger, String writepath, Map<String, String> map)
	{
		WorkAppPropertyFileWriter propertiesFileWriter = (WorkAppPropertyFileWriter) WorkAppPropertyFileWriter.getInstance(logger);
		logger.LogInfo("Printing Properties to FilePath", WorkAppPropertiesFileWriterExample.class);
		propertiesFileWriter.writeToFile(writepath, map);
	}

	public static void main(String args[]) throws IOException, SystemException
	{
		String writepath = "config5.properties";
		IApplicationLogger logger = new AppLogger(null);
		Map<String, String> map = new HashMap<String, String>();
		map.put("database", "localhost");
		map.put("dbuser", "dheeban");
		map.put("dbpassword", "password");

		writeToFileSystem(logger, writepath, map);
	}
}
