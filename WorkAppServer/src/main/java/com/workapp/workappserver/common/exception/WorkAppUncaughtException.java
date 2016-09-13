package com.workapp.workappserver.common.exception;

/**
 * WorkAppUncaughtException is a custom RuntimeException for handling Singleton
 * Object creation error
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUncaughtException extends RuntimeException
{
	private static final long serialVersionUID = 2345586790716397712L;

	/**
	 * WorkAppUncaughtException Constructor
	 */
	public WorkAppUncaughtException()
	{
		super();
	}

	/**
	 * WorkAppUncaughtException Constructor
	 * 
	 * @param s
	 *            Message string
	 */
	public WorkAppUncaughtException(String s)
	{
		super(s);
	}

	/**
	 * WorkAppUncaughtException Constructor
	 * 
	 * @param s
	 *            Message string
	 * @param throwable
	 *            takes an exception throwable as parameter
	 */
	public WorkAppUncaughtException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	/**
	 * WorkAppUncaughtException Constructor
	 * 
	 * @param throwable
	 *            takes an exception throwable as parameter
	 */
	public WorkAppUncaughtException(Throwable throwable)
	{
		super(throwable);
	}
}
