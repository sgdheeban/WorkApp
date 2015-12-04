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
	 * getContextName returns the name associated with the Context object
	 * 
	 * @return
	 */
	public String getContextComment();
}
