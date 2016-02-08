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
 * An implementation of IResource to expose services for performing user related
 * services
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
	public Response registerUser(@Context
	UriInfo uriInfo, User userJson)
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

	/**
	 * Get News Feed for User
	 * 
	 * @param logoutInfo
	 * @return
	 */
	@POST
	@Path("getNewsFeed")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getNewsFeed(String logoutInfo)
	{
		Response response = null;
		return response;
	}

	/**
	 * View Backlog for user
	 * 
	 * @param userInfo
	 * @return
	 */
	@POST
	@Path("viewBacklog")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewBacklog(String userInfo)
	{
		Response response = null;
		return response;
	}

}
