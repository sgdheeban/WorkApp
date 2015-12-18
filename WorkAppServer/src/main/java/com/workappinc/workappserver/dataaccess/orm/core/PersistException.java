// $Id: RuntimeSQLException.java 12 2007-08-29 05:23:13Z jcamaia $

package com.workappinc.workappserver.dataaccess.orm.core;

@SuppressWarnings("serial")
public final class PersistException extends RuntimeException
{

	public PersistException(final Throwable cause)
	{
		super(cause);
	}

	public PersistException(final String message)
	{
		super(message);
	}

	public PersistException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

}
