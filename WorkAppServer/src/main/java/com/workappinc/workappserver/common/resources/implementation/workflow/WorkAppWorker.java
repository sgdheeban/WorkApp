/**
 * 
 */
package com.workappinc.workappserver.common.resources.implementation.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.IPipe;
import com.workappinc.workappserver.common.resources.interfaces.IWorker;

/**
 * Worker Threads performing the job, assigned by the pipeline
 * 
 * @author dhgovindaraj
 *
 */
public abstract class WorkAppWorker implements IWorker
{
	private IApplicationLogger mLogger = null;

	public static final String NORMAL_PIPE_TYPE = "normal";
	public static final String ERROR_PIPE_TYPE = "error";

	private static int identifierCounter = 0;
	private WorkAppMultiThreadedExecutor executor;

	protected int identifier = getUniqueInstanceIdentifier();

	public static final String DEFAULT_PIPE_TYPE = "default";

	private List<IPipe> inPipes = new ArrayList<IPipe>();
	private Map<String, IPipe> outPipes = new HashMap<String, IPipe>();

	private boolean iAmBusy = false;
	private IPipe sender;

	private long totalProcessingTime = 0;

	public WorkAppWorker(IApplicationLogger logger)
	{
		this.mLogger = logger;
	}

	@Override
	public final synchronized boolean receive(final Object data, IPipe sender)
	{
		if (iAmBusy)
		{
			return false;
		}
		else
		{
			iAmBusy = true;
			this.sender = sender;
			// process data in thread
			startMe(data);
			return true;
		}
	}

	public boolean executeInBackground(Object data)
	{
		try
		{
			long start = System.currentTimeMillis();
			doJob(data, outPipes);
			totalProcessingTime += System.currentTimeMillis() - start;
		}
		catch (Exception ex)
		{
			if (this.mLogger != null)
				mLogger.LogException(ex, WorkAppWorker.class);
		}
		finally
		{
			iAmBusy = false;
			sendEchoToInput();
		}
		return true;
	}

	private void sendEchoToInput()
	{
		if (sender != null)
		{
			sender.receiveEchoFromOutput(this);
		}
	}

	abstract protected void doJob(Object data, Map<String, IPipe> outPipes) throws Exception;

	@Override
	public void registerInPipe(IPipe inPipe)
	{
		registerInPipe(DEFAULT_PIPE_TYPE, inPipe);
	}

	@Override
	public void registerOutPipe(IPipe outPipe)
	{
		registerOutPipe(DEFAULT_PIPE_TYPE, outPipe);
	}

	@Override
	public void registerInPipe(String type, IPipe inPipe)
	{
		inPipes.add(inPipe);
	}

	@Override
	public void registerOutPipe(String type, IPipe outPipe)
	{
		outPipes.put(type, outPipe);
	}

	public void startMe(Object data)
	{
		executor.executeTask(this, data);
	}

	private synchronized int getUniqueInstanceIdentifier()
	{
		return identifierCounter++;
	}

	@Override
	public boolean isStandalone()
	{
		return inPipes.size() == 0;
	}

	/**
	 * @return the totalProcessingTime
	 */
	public long getTotalProcessingTime()
	{
		return totalProcessingTime;
	}

	/**
	 * @param executor
	 *            the executor to set
	 */
	public void setExecutor(WorkAppMultiThreadedExecutor executor)
	{
		this.executor = executor;
	}

	@Override
	public void close() throws Exception
	{
	}

	@Override
	public void open() throws Exception
	{
	}
}
