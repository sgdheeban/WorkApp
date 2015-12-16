package com.workappinc.workappserver.businesslogic.model;

import java.io.IOException;

/**
 * 
 * WorkAppMainServer is the main entrypoint for WorkApp sever project
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppMainServer implements Runnable, IServer
{
	/**
	 * Static Main method called on start of the executable, instantiates the
	 * server
	 * 
	 * @throws IOException
	 */
	public static void main() throws IOException
	{
		new WorkAppMainServer().run();
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub

		// Step1: Read Command-Line Options into Properties
		// Step2 : Read config file and update Properties
		// Step3: Read Log config file, if any and get a Logger Instance
		// Step4: Instantiate JSON Parser for reading any JSON Config
		// Step5: Get an Instance of Object Allocation Tracker
		// Step6: Get an instance of SQL Query Generator and Data Access tools
		// Step7: Start an Jetty-HTTP or Thrift server to serve requests

	}

}
