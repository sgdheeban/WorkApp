package com.workappinc.workappserver.common.resources;

import java.util.HashMap;

/**
 * SingletonRegistry is a class that lists all singletons in the system
 * Add reference to singleton as POJOs are created
 * @author dhgovindaraj
 *
 */
public class SingletonUsageRegistry
{
	private static HashMap<String, String> mSingletonDefinitions = new HashMap<String, String> ();

	static 
	{
		// ADD Singleton Definitions here, as the POJOs are created
	}
	
	/**
	 * Returns a list of all singleton objects available in the system
	 * @return
	 */
	public static HashMap<String, String> getSingletonList() {
		return mSingletonDefinitions;
	}

}
