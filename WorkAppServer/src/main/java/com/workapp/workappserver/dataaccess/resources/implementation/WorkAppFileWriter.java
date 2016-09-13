package com.workapp.workappserver.dataaccess.resources.implementation;

import com.workapp.workappserver.dataaccess.resources.interfaces.IWriter;

/**
 * WorkAppFileWriter is a generic implementation of IWriter interface which
 * supports writing a variety of files to filesystem
 * 
 * @author dhgovindaraj
 *
 */
public abstract class WorkAppFileWriter implements IWriter
{
	/**
	 * Abstract method to Write to file system given an output-path and input
	 * 
	 * @param outputFilePath
	 * @param inMemoryObject
	 */
	public abstract void writeToFile(String outputFilePath, Object inMemoryObject);

	/**
	 * Abstract method to format, which can be customized according to the
	 * FileWriter type
	 * 
	 * @param input
	 * @return
	 */
	public abstract Object format(Object input);

}
