package com.workapp.workappserver.common.resources.examples;

import java.util.Map;

import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.WorkAppLogger;
import com.workapp.workappserver.common.resources.implementation.pipe.WorkAppSynchronousPipe;
import com.workapp.workappserver.common.resources.implementation.workflow.WorkAppWorker;
import com.workapp.workappserver.common.resources.implementation.workflow.WorkAppWorkflow;
import com.workapp.workappserver.common.resources.interfaces.IPipe;

/**
 * Sync pipe example with single processor
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppSyncPipeStandardExample
{
	private static IApplicationLogger logger;

	public static void main(String[] args) throws Exception
	{
		logger = new WorkAppLogger(null);
		// a workflow reader, which creates data
		// and will send them through pipeline
		WorkAppWorker dataReader = createReader();

		// a workflow processors, each one will be executed in
		// a separate thread, each one will receive data as
		// they become available from the reader
		int numberOfProcessors = 2;
		WorkAppWorker[] processors = new WorkAppWorker[numberOfProcessors];
		for (int i = 0; i < numberOfProcessors; i++)
		{
			processors[i] = createProcessor();
		}

		// a workflow writer, in this case it collects all processed data
		// and it will iteratively create statistics from them
		WorkAppWorker writer = createWriter();

		// create pipeline similar to UNIX pipeline
		IPipe readerPipe = new WorkAppSynchronousPipe(logger).addInputs(dataReader).addOutputs(processors);
		IPipe writerPipe = new WorkAppSynchronousPipe(logger).addInputs(processors).addOutputs(writer);

		// register pipes and execute workflow
		WorkAppWorkflow workflow = new WorkAppWorkflow(logger);
		workflow.registerPipes(readerPipe, writerPipe);
		// do blocking execution
		workflow.execute();
	}

	private static WorkAppWorker createReader()
	{
		return new WorkAppWorker(logger)
		{
			int count = 0;

			@Override
			public void open() throws Exception
			{
				System.out.println("Entering Reader ... ");
			}

			@Override
			protected void doJob(Object data, Map<String, IPipe> outPipes) throws Exception
			{
				int max = 1000;
				for (int i = 0; i < max; i++)
				{
					double x = Math.random();
					// System.out.println("Input - " + x);
					count++;
					outPipes.get(DEFAULT_PIPE_TYPE).send(new DataToken(x));
				}
			}

			@Override
			public void close() throws Exception
			{
				System.out.println("Exiting Reader ... processing count : " + count);
				count = 0;
			}
		};
	}

	private static WorkAppWorker createProcessor()
	{
		return new WorkAppWorker(logger)
		{
			int count = 0;

			@Override
			public void open() throws Exception
			{
				System.out.println("Entering Processor ... ");
			}

			@Override
			protected void doJob(Object data, Map<String, IPipe> outPipes) throws Exception
			{
				DataToken dataToken = (DataToken) data;
				dataToken.setToken(Math.floor(dataToken.getToken() * 10));
				count++;
				outPipes.get(DEFAULT_PIPE_TYPE).send(dataToken);
			}

			@Override
			public void close() throws Exception
			{
				System.out.println("Exiting Processor ... processing count : " + count);
				count = 0;
			}
		};
	}

	private static WorkAppWorker createWriter()
	{
		return new WorkAppWorker(logger)
		{
			private final int range = 10;
			int[] receivedData = new int[range];
			int count = 0;

			@Override
			public void open() throws Exception
			{
				System.out.println("Entering Writer ... ");
			}

			@Override
			protected void doJob(Object data, Map<String, IPipe> outPipes) throws Exception
			{
				DataToken dataToken = (DataToken) data;
				receivedData[(int) dataToken.getToken()]++;
				count++;
			}

			@Override
			public void close() throws Exception
			{
				int sum = 0;
				// print out the stats
				for (int i = 0; i < range; i++)
				{
					// System.out.println(String.format("Bucket %d: %d", i,
					// receivedData[i]));
					sum += receivedData[i];
					// System.out.println("Total Generated Random Numbers : " +
					// sum);
				}
				System.out.println("Exiting Writer ... processing count : " + count);
				count = 0;
			}
		};
	}

	private static class DataToken
	{
		private double token;

		public DataToken(double token)
		{
			this.token = token;
		}

		public double getToken()
		{
			return token;
		}

		public void setToken(double token)
		{
			this.token = token;
		}
	}
}
