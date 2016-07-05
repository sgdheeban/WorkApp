package com.workapp.workappserver.businesslogic.model;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.MultivaluedMap;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.workapp.workappserver.businesslogic.model.table.User;
import com.workapp.workappserver.common.exception.DatabaseException;
import com.workapp.workappserver.common.exception.DuplicateDBEntryException;
import com.workapp.workappserver.common.exception.InternalServerException;
import com.workapp.workappserver.common.exception.MD5HashingException;
import com.workapp.workappserver.common.exception.RuntimeSQLException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.resources.implementation.WorkAppUtil;
import com.workapp.workappserver.dataaccess.orm.core.Persist;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation.WorkAppQbFactoryImp;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbFactory;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;

public class WorkAppServiceManager
{
	private  WorkAppMySQLConnectionManager _connections;
	private HashMap<String, Object> _configMap;
	private IApplicationLogger _logger;
	private WorkAppQbFactory _fac;

	/**
	 * Loads Common Resources in constructor
	 * 
	 * @param connections
	 * @throws SQLException
	 */
	public WorkAppServiceManager (WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap, IApplicationLogger logger) throws SQLException
	{
		_connections = connections;
		_configMap = configMap;
		_logger = logger;
		_fac = new WorkAppQbFactoryImp();
	}

	/**
	 * Registers new user
	 * 
	 * @param queryMap
	 * @param userJson
	 * @throws DuplicateDBEntryException
	 * @throws DatabaseException
	 * @throws InternalServerException
	 * @System.out.println(select.getQueryString());return
	 */
	public void registerUser(MultivaluedMap<String, String> queryMap, User user) throws DuplicateDBEntryException, DatabaseException, InternalServerException
	{
		String queryID = null;
		if (queryMap != null)
			queryID = queryMap.getFirst("qid");
		WorkAppJDBCConnection conn = null;
		Persist persist = null;
		try
		{
			conn = _connections.getConnection();
			persist = new Persist(conn);
			user.setId(WorkAppUtil.generateUUID(_logger, null));
			String password = user.getPassword();
			user.setPassword(WorkAppUtil.generateMD5HashString(_logger, null, password));
			persist.insert(user);
		}
		catch (RuntimeSQLException ex)
		{
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException)
			{
				_logger.LogDebug("[" + queryID + "] " + user.getEmail() + " already exists in the database.", WorkAppServiceManager.class);
				throw new DuplicateDBEntryException(user.getEmail() + " already exists in the database.", ex);
			}
			else
			{
				_logger.LogException(ex, WorkAppServiceManager.class);
				throw new DatabaseException("[" + queryID + "] " + "Database Exception with messasge:" + ex.getMessage(), ex);
			}
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
			throw new DatabaseException("[" + queryID + "] " + "Database Exception with messasge:" + ex.getMessage(), ex);
		}
		catch (MD5HashingException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
			throw new InternalServerException("[" + queryID + "] " + "Hashing Exception with messasge:" + ex.getMessage(), ex);
		}
		finally
		{
			conn.close();
			persist = null;
			conn = null;
		}
	}

}
