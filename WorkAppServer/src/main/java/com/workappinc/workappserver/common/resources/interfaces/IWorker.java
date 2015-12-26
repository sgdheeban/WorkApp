package com.workappinc.workappserver.common.resources.interfaces;

import com.workappinc.workappserver.common.resources.implementation.workflow.WorkAppMultiThreadedExecutor;

/**
 * Generic IWorker interface to implement Worker Objects
 * 
 * @author dhgovindaraj
 *
 */
public interface IWorker
{

	public boolean receive(Object data, IPipe sender);

	public void registerInPipe(IPipe inPipe);

	public void registerOutPipe(IPipe outPipe);

	public void registerInPipe(String type, IPipe inPipe);

	public void registerOutPipe(String type, IPipe outPipe);

	public boolean isStandalone();

	public void open() throws Exception;

	public void close() throws Exception;

	public long getTotalProcessingTime();

	public void setExecutor(WorkAppMultiThreadedExecutor executor);
}
