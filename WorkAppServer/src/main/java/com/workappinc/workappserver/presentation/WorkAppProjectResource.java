package com.workappinc.workappserver.presentation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * An implementation of IResource to expose services for loading Client Pages -
 * Web or Mobile
 * 
 * @author dhgovindaraj
 *
 */
@Path("/workapp/v1/project/")
public class WorkAppProjectResource implements IResource
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
	 * Create Board
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("createBoard")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBoard(String boardInfo)
	{
		Response response = null;
		return response;
	}

	/**
	 * View Boards for user
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("viewAllBoards")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewAllBoards(String userInfo)
	{
		Response response = null;
		return response;
	}
	
	/**
	 * View Board
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("viewBoard")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewBoard(String boardInfo)
	{
		Response response = null;
		return response;
	}

	/**
	 * Edit Board
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("editBoard")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editBoard(String boardInfo)
	{
		Response response = null;
		return response;
	}
	
	/**
	 * Create Work
	 * 
	 * @param workInfo
	 * @return
	 */
	@POST
	@Path("createWork")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createWork(String workInfo)
	{
		Response response = null;
		return response;
	}

	/**
	 * View Works for a board
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("viewAllWorks")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewAllWorks(String userInfo)
	{
		Response response = null;
		return response;
	}
	
	/**
	 * View Work
	 * 
	 * @param boardInfo
	 * @return
	 */
	@POST
	@Path("viewWork")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response viewWork(String boardInfo)
	{
		Response response = null;
		return response;
	}
	
	/**
	 * Edit Work
	 * 
	 * @param workInfo
	 * @return
	 */
	@POST
	@Path("editWork")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editWork(String workInfo)
	{
		Response response = null;
		return response;
	}
	
}
