package com.workapp.workappserver.common.exception;

/**
 * SingletonInitException is a custom RuntimeException for handling Singleton
 * Object creation error
 * 
 * @author dhgovindaraj
 *
 */
public class SingletonInitException extends RuntimeException
{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 2345586790716397712L;

	/**
	 * SingletonInitException Constructor
	 */
	public SingletonInitException()
	{
		super();
	}

	/**
	 * SingletonInitException Constructor
	 * 
	 * @param s
	 *            Message string
	 */
	public SingletonInitException(String s)
	{
		super(s);
	}

	/**
	 * SingletonInitException Constructor
	 * 
	 * @param s
	 *            Message string
	 * @param throwable
	 *            takes an exception throwable as parameter
	 */
	public SingletonInitException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	/**
	 * SingletonInitException Constructor
	 * 
	 * @param throwable
	 *            takes an exception throwable as parameter
	 */
	public SingletonInitException(Throwable throwable)
	{
		super(throwable);
	}
}
