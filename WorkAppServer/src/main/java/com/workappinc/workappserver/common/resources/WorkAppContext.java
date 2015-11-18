package com.workappinc.workappserver.common.resources;

/**
 * WorkAppContext is an implementation of IContext, which is used to trace flow
 * within the system
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppContext implements IContext
{
	/**
	 * Default Constructor
	 */
	public WorkAppContext()
	{

	}

	/**
	 * 
	 * @param GUID
	 *            Global Unique Identifier used to identify the Context object
	 * @param Message
	 *            Message related to the Context object
	 */
	public WorkAppContext(Long GUID, String Message)
	{

	}

	@Override
	public Long getGUID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
