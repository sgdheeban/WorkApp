package com.workappinc.workappserver.presentation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.time.StopWatch;
/*import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;*/

import com.workappinc.workappserver.businesslogic.model.WorkAppServiceManager;
import com.workappinc.workappserver.businesslogic.model.table.User;
import com.workappinc.workappserver.common.exception.DatabaseException;
import com.workappinc.workappserver.common.exception.DuplicateDBEntryException;
import com.workappinc.workappserver.common.exception.InternalServerException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services for performing user related services
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/v1/user/")
public class WorkAppUserResource implements IResource
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

	/**
	 * Registers new user
	 * 
	 * @param response
	 * @return
	 */
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(@Context UriInfo uriInfo, User userJson)
	{
		MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		try
		{
			WorkAppServiceManager.registerUser(queryMap, userJson);
			stopwatch.stop();
			return Response.status(Status.OK).entity("Saved.").header("Time Taken", stopwatch.getTime() + " ms").build();
		}
		catch (DuplicateDBEntryException ex)
		{
			stopwatch.stop();
			return Response.status(Status.CONFLICT).entity("User already exists in the DB.").header("Time Taken", stopwatch.getTime() + " ms").build();
		}
		catch (DatabaseException | InternalServerException ex)
		{
			stopwatch.stop();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error inside server logic.").header("Time Taken", stopwatch.getTime() + " ms").build();
		}
		finally
		{
			stopwatch.reset();
		}
	}

	/**
	 * Login User
	 * 
	 * @param loginInfo
	 * @return
	 */
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(String loginInfo)
	{
		Response response = null;
		return response;
	}

	/**
	 * Logout User
	 * 
	 * @param logoutInfo
	 * @return
	 */
	@POST
	@Path("logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logoutUser(String logoutInfo)
	{
		Response response = null;
		return response;
	}
	
	/*
	 * API pending to authenticate user session or this is done internally for every action call
	 */
	
	/**
	 * Edit user info
	 * 
	 * @param userJson
	 * @return
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(String userJson)
	{
		Response response = null;
		return response;
	}

	/*
	 * API pending to Upload Files and Pictures
	 * @POST
	 * 
	 * @Path("/upload")
	 * 
	 * @Consumes(MediaType.MULTIPART_FORM_DATA) public Response
	 * uploadFile(@FormDataParam("file") InputStream
	 * uploadedInputStream, @FormDataParam("file") FormDataContentDisposition
	 * fileDetail) { Response response = null; return response; }
	 */
	
	/*
	 * API pending to download files and pics 
	 */
	
	/**
	 * Get configuration value, given a key
	 * 
	 * @param configName
	 * @return
	 */
	@GET
	@Path("{configName}")
	public Response getConfig(@PathParam("configName")
	String configName)
	{
		Response response = null;
		return response;
	}

	/*
	 *  API pending to create task
	 *  API pending to edit task
	 *  API pending to delete task 
	 */


}
