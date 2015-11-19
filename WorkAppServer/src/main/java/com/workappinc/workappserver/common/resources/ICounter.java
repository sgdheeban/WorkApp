package com.workappinc.workappserver.common.resources;

/**
 * ICounter is a top-level interface for deriving Counter classes within the
 * project
 * 
 * @author dhgovindaraj
 *
 */
public interface ICounter
{
	/**
	 * 
	 * StartCounter method is thread-safe, only one thread will ever set the
	 * StartCounter, to start the counter in the background
	 */
	public void startCounter();
}
