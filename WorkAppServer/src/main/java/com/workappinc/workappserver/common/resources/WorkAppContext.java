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

	private String mGUID = "";
	private String mContextComment = "";

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
	 * @param ContextName
	 *            ContextName related to the Context object
	 */
	public WorkAppContext(String GUID, String message)
	{
		this.mGUID = GUID;
		this.mContextComment = message;
	}

	@Override
	public String getGUID()
	{
		// TODO Auto-generated method stub
		return mGUID;
	}

	@Override
	public String getContextComment()
	{
		// TODO Auto-generated method stub
		return mContextComment;
	}
}
