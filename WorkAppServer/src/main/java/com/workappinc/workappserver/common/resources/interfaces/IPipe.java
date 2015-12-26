package com.workappinc.workappserver.common.resources.interfaces;

import java.util.List;

/**
 * Generic IPipe interface to implement Sync and ASync pipes
 * 
 * @author dhgovindaraj
 *
 */
public interface IPipe
{
	/**
	 * Send object through the pipe from one worker to another
	 * 
	 * @param data
	 * @return
	 */
	public boolean send(Object data);

	/**
	 * Add input workers to the pipe
	 * 
	 * @param workers
	 * @return
	 */
	public IPipe addInputs(IWorker... workers);

	/**
	 * Add output workers to the pipe
	 * 
	 * @param workers
	 * @return
	 */
	public IPipe addOutputs(IWorker... workers);

	/**
	 * Add input workers to the pipe of a specific type
	 * 
	 * @param type
	 * @param workers
	 * @return
	 */
	public IPipe addInputs(String type, IWorker... workers);

	/**
	 * Add output workers to the pipe of a specific type
	 * 
	 * @param type
	 * @param workers
	 * @return
	 */
	public IPipe addOutputs(String type, IWorker... workers);

	/**
	 * Mark the end of work by receiving response from the worker
	 * 
	 * @param worker
	 */
	public void receiveEchoFromOutput(IWorker worker);

	/**
	 * Initiate the pipeline
	 */
	public void init();

	/**
	 * Get list of all standalone workers registered on this pipe - i.e.,
	 * workers with no input pipes
	 * 
	 * @return
	 */
	public List<IWorker> getStandaloneWorkers();

	/**
	 * 
	 * Get all standalone, and endworkers, i.e., workers with no input, and no
	 * output pipes
	 * 
	 * @return
	 */
	public List<IWorker> getEndWorkers();
}
