package com.workappinc.workappserver.common.resources;

/**
 * IContext interface from which all context objects to trace object flow within
 * the system
 * 
 * @author dhgovindaraj
 *
 */
public interface IContext
{
	/**
	 * getGUID returns the Global Unique Identifier for the Context object
	 * 
	 * @return
	 */
	public String getGUID();

	/**
	 * getMessage returns the message associated with the Context object
	 * 
	 * @return
	 */
	public String getMessage();
}
