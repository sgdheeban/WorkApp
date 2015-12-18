// $Id$

package com.workappinc.workappserver.dataaccess.orm.core;

@SuppressWarnings("serial")
public final class RuntimeIOException extends RuntimeException
{

	public RuntimeIOException(final Throwable cause)
	{
		super(cause);
	}

	public RuntimeIOException(final String message)
	{
		super(message);
	}

	public RuntimeIOException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

}
