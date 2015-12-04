package com.workappinc.workappserver.dataaccess.resources;

/**
 * WorkAppFileWriter is a generic implementation of IWriter interface which
 * supports writing a variety of files to filesystem
 * 
 * @author dhgovindaraj
 *
 */
public abstract class WorkAppFileWriter implements IWriter
{
	public abstract void writeToFile(String outputFilePath, Object inMemoryObject);

	public abstract Object format(Object input);

}
