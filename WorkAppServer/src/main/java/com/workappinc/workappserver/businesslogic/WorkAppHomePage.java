package com.workappinc.workappserver.businesslogic;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.ISerializer;

/**
 * A Singleton Class for modeling the homepage and takes care of serializing and de-serializing between JSON-POJO representation 
 * @author dhgovindaraj
 *
 */
public class WorkAppHomePage implements IHomePage
{
	private static ISerializer mSerializer = null;
	private static IApplicationLogger mLogger = null;
	private static IPage mInstance = null;
	
	private WorkAppHomePage(IApplicationLogger logger, ISerializer serializer)
	{
		mLogger = logger;
		mSerializer = serializer ;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IPage getInstance(IApplicationLogger logger, ISerializer serializer)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppHomePage.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppHomePage(logger, serializer);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppHomePage Class", ex);
		}
	}

	@Override
	public Object loadPage()
	{
		// TODO Auto-generated method stub - serialize and load page
		return null;
	}

	@Override
	public Object loadSection(Object name)
	{
		// TODO Auto-generated method stub - serialize and load page
		return null;
	}
	
	// Incoming updates from Client - use deserialize and save

}
