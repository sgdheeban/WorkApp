package com.workappinc.workappserver.common.exception;

/**
 * Records System Exception such as RuntimeException, UnknownHostException
 * 
 * @author dhgovindaraj
 *
 */
public class SystemException extends Exception
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -8610790003398426953L;

	public SystemException()
	{
	}

	public SystemException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
