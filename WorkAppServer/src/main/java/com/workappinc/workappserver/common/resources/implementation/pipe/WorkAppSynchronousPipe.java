package com.workappinc.workappserver.common.resources.implementation.pipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.IPipe;
import com.workappinc.workappserver.common.resources.interfaces.IWorker;

/**
 * Synchronous Pipe used to coordinate inter-thread communication in Sync
 * fashion via. thread blocking
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppSynchronousPipe implements IPipe
{
	private IApplicationLogger mLogger = null;

	private IWorker[] inputWorkers;
	private IWorker[] outputWorkers;

	private Semaphore freeReceivers;
	
	public WorkAppSynchronousPipe (IApplicationLogger logger)
	{
		this.mLogger = logger ; 
	}
	
	public boolean send(Object data)
	{
		try
		{
			freeReceivers.acquire();
			return trySendData(data);
		}
		catch (InterruptedException ex)
		{
			if(this.mLogger != null)
				mLogger.LogException(ex, WorkAppSynchronousPipe.class);
		}
		return false;
	}

	private boolean trySendData(Object data)
	{
		for (IWorker worker : outputWorkers)
		{
			if (worker.receive(data, this))
			{
				// worker received new data
				return true;
			}
		}
		return false;
	}

	@Override
	public IPipe addInputs(IWorker... workers)
	{
		return addInputs(null, workers);
	}

	@Override
	public IPipe addOutputs(IWorker... workers)
	{
		return addOutputs(null, workers);
	}

	@Override
	public IPipe addInputs(String type, IWorker... workers)
	{
		this.inputWorkers = workers;
		for (IWorker worker : inputWorkers)
		{
			if (type == null)
			{
				worker.registerOutPipe(this);
			}
			else
			{
				worker.registerOutPipe(type, this);
			}
		}
		return this;
	}

	@Override
	public IPipe addOutputs(String type, IWorker... workers)
	{
		this.outputWorkers = workers;
		for (IWorker worker : outputWorkers)
		{
			if (type == null)
			{
				worker.registerInPipe(this);
			}
			else
			{
				worker.registerInPipe(type, this);
			}
		}
		return this;
	}

	@Override
	public void receiveEchoFromOutput(IWorker worker)
	{
		freeReceivers.release();
	}

	@Override
	public void init()
	{
		freeReceivers = new Semaphore(outputWorkers.length);
	}

	@Override
	public List<IWorker> getStandaloneWorkers()
	{
		List<IWorker> standaloneWorkers = new ArrayList<IWorker>();
		for (IWorker worker : inputWorkers)
		{
			if (worker.isStandalone())
			{
				standaloneWorkers.add(worker);
			}
		}
		return standaloneWorkers;
	}

	@Override
	public List<IWorker> getEndWorkers()
	{
		List<IWorker> endWorkers = new ArrayList<IWorker>();
		Collections.addAll(endWorkers, outputWorkers);
		endWorkers.addAll(getStandaloneWorkers());
		return endWorkers;
	}

}
