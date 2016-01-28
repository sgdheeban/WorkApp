package com.workappinc.workappserver.common.exception;

public class InternalServerException extends Exception
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -5002827640105983345L;

	public InternalServerException()
	{
	}

	public InternalServerException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
