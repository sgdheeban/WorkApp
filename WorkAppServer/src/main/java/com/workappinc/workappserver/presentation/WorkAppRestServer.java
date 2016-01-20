package com.workappinc.workappserver.presentation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;

import com.workappinc.workappserver.common.logging.IApplicationLogger;

public class WorkAppRestServer
{

	public static void startServer(int port, IApplicationLogger logger)
	{
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		Server jettyServer = new Server(port);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		// register jackson and jersey resources
		jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "com.fasterxml.jackson.jaxrs.json;com.workappinc.workappserver.presentation");

		try
		{
			jettyServer.start();
			jettyServer.join();
		}
		catch (InterruptedException ex)
		{
			logger.LogException(ex, WorkAppRestServer.class);

		}
		catch (Exception ex)
		{
			logger.LogException(ex, WorkAppRestServer.class);
		}
		finally
		{
			jettyServer.destroy();
		}
	}
}
