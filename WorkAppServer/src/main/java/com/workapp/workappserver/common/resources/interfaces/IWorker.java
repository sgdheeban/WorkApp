package com.workapp.workappserver.common.resources.interfaces;

import com.workapp.workappserver.common.resources.implementation.workflow.WorkAppMultiThreadedExecutor;

/**
 * Generic IWorker interface to implement Worker Objects
 * 
 * @author dhgovindaraj
 *
 */
public interface IWorker
{
	/**
	 * Receives object from another worker sent through the pipe
	 * 
	 * @param data
	 *            Object received
	 * @param sender
	 *            Sender worker
	 * @return
	 */
	public boolean receive(Object data, IPipe sender);

	/**
	 * Register the worker with the pipe for input
	 * 
	 * @param inPipe
	 */
	public void registerInPipe(IPipe inPipe);

	/**
	 * Register the worker with the pipe for output
	 * 
	 * @param outPipe
	 */
	public void registerOutPipe(IPipe outPipe);

	/**
	 * Register the worker with the pipe for input with a specific type
	 * 
	 * @param type
	 * @param inPipe
	 */
	public void registerInPipe(String type, IPipe inPipe);

	/**
	 * Register the worker with the pipe for output with a specific type
	 * 
	 * @param type
	 * @param outPipe
	 */
	public void registerOutPipe(String type, IPipe outPipe);

	/**
	 * Returns if the worker is a standalone worker, basically no input
	 * 
	 * @return
	 */
	public boolean isStandalone();

	/**
	 * Logic to be executed when Worker is created for the first time
	 * 
	 * @throws Exception
	 */
	public void open() throws Exception;

	/**
	 * Logic to be executed when Worker is closed at the end
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception;

	/**
	 * Total processing time per worker
	 * 
	 * @return
	 */
	public long getTotalProcessingTime();

	/**
	 * Assign a thread executor to the worker
	 * 
	 * @param executor
	 */
	public void setExecutor(WorkAppMultiThreadedExecutor executor);
}
