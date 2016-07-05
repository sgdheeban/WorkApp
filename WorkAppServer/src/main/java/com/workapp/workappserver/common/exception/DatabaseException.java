package com.workapp.workappserver.common.exception;

public class DatabaseException extends Exception
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 5719365500651580408L;

	public DatabaseException()
	{
	}

	public DatabaseException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
