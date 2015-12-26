package com.workappinc.workappserver.common.resources.implementation.workflow;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.IPipe;
import com.workappinc.workappserver.common.resources.interfaces.IWorker;

/**
 * WorkFlow is the driver program which manages the pipeline and the workers
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppWorkflow
{
	private IApplicationLogger mLogger = null;

	private WorkAppMultiThreadedExecutor executor = new WorkAppMultiThreadedExecutor();

	private IPipe[] pipes;

	public WorkAppWorkflow(IApplicationLogger logger)
	{
		this.mLogger = logger;
	}

	public void registerPipes(IPipe... pipes) throws Exception
	{
		this.pipes = pipes;
		for (IPipe pipe : pipes)
		{
			pipe.init();
		}
		for (IPipe pipe : pipes)
		{
			for (IWorker worker : pipe.getEndWorkers())
			{
				worker.setExecutor(executor);
				worker.open();
			}
		}
	}

	public void execute() throws Exception
	{

		for (IPipe pipe : pipes)
		{
			for (IWorker worker : pipe.getStandaloneWorkers())
			{
				((WorkAppWorker) worker).startMe(null);
			}
		}

		while (!isFinished())
		{
			if (this.mLogger != null)
				mLogger.LogInfo("waiting to finish jobs, #active jobs: " + executor.getNumberOfActiveTasks(),
						WorkAppWorkflow.class);

			try
			{
				Thread.sleep(1000L);
			}
			catch (InterruptedException ex)
			{
				if (this.mLogger != null)
					mLogger.LogException(ex, WorkAppWorkflow.class);
			}
		}

		double totalExecTime = (executor.getTotalExecutionTime()) / 1000.0;
		double totalProcTime = (executor.getTotalProcessingTime()) / 1000.0;

		if (this.mLogger != null)
			mLogger.LogInfo("WorkAppWorkflow ended, took " + totalExecTime + " seconds, "
					+ "total processing time took " + totalProcTime, WorkAppWorkflow.class);

		if (this.mLogger != null)
			mLogger.LogInfo("Total processing time for each worker:", WorkAppWorkflow.class);

		for (IPipe pipe : pipes)
		{
			for (IWorker worker : pipe.getEndWorkers())
			{
				double time = (worker.getTotalProcessingTime() / 1000.0);
				int ratioTime = (int) (time * 100.0 / totalExecTime);
				if (this.mLogger != null)
					mLogger.LogInfo("\t" + worker.getClass().getSimpleName() + ": " + time + ", " + ratioTime + "%",
							WorkAppWorkflow.class);
			}
		}

		for (IPipe pipe : pipes)
		{
			for (IWorker worker : pipe.getEndWorkers())
			{
				worker.close();
			}
		}
		executor.shutdown();
	}

	public boolean isFinished()
	{
		return executor.isFinished();
	}

	public void executeWithoutBlocking()
	{
		throw new UnsupportedOperationException();
	}
}
