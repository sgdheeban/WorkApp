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
	 * Important Metrics to Calculate per Application: 
	 * 
	 * (1) Transaction Time per API, per Machine, Per Pool
	 * (2) Transaction per Second per Machine, Per Pool 
	 * (3) Exception Count per Machine, Per Pool
	 * (4) CPU Usage per Machine, Per Pool
	 * (5) JVM Memory Usage per Machine, Per Pool
	 * (6) GC Overhead Per Machine, Per Pool
	 * 
	 */
	
	/**
	 * StartCounter method is thread-safe, only one thread will ever set the
	 * StartCounter, to start the counter in the background
	 */
	public void startCounter();
	
}
