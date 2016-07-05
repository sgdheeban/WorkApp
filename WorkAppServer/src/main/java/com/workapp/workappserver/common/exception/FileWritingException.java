package com.workapp.workappserver.common.exception;

/**
 * FileWritingException is thrown when Writing Files to FileSystem fails
 * 
 * @author dhgovindaraj
 *
 */
public class FileWritingException extends Exception
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 8430023788740018690L;

	public FileWritingException()
	{
	}

	public FileWritingException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

	public FileWritingException(String message)
	{
		super(message);
	}
}
