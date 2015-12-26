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

	public boolean send(Object data);

	public IPipe addInputs(IWorker... workers);

	public IPipe addOutputs(IWorker... workers);

	public IPipe addInputs(String type, IWorker... workers);

	public IPipe addOutputs(String type, IWorker... workers);

	public void receiveEchoFromOutput(IWorker worker);

	public void init();

	public List<IWorker> getStandaloneWorkers();

	public List<IWorker> getEndWorkers();
}
