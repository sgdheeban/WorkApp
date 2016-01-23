package com.workappinc.workappserver.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.workappinc.workappserver.businesslogic.model.TestUserInfo;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

@Path("/users")
public class TestResource implements IResource
{
	private static IApplicationLogger _logger;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 */
	public static void initResource(IApplicationLogger logger)
	{
		_logger = logger;
	}

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestUserInfo getUserInfo(@PathParam("userId")
	long userId)
	{
		return new TestUserInfo("Dheeban", "SG");
	}

	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test()
	{
		return "Test";
	}
}
