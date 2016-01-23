package com.workappinc.workappserver.presentation;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.workappinc.workappserver.businesslogic.model.TestUserInfo;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;

@Path("/users")
public class TestResource implements IResource
{
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
