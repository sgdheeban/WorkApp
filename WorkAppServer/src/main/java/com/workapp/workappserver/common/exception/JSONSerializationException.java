package com.workapp.workappserver.common.exception;

/**
 * JSONSerializationException records JSON-POJO Serialization-Deserialization
 * Exceptio
 * 
 * @author dhgovindaraj
 *
 */
public class JSONSerializationException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6823570060072236466L;

	/**
	 * Generated Serial UID
	 */

	public JSONSerializationException()
	{
	}

	public JSONSerializationException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
