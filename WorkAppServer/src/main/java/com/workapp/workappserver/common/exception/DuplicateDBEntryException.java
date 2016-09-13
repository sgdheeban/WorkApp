package com.workapp.workappserver.common.exception;

public class DuplicateDBEntryException extends Exception
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -7653023619867864643L;

	public DuplicateDBEntryException()
	{
	}

	public DuplicateDBEntryException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
