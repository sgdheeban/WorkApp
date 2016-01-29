package com.workappinc.workappserver.businesslogic.model;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.MultivaluedMap;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.workappinc.workappserver.businesslogic.model.table.User;
import com.workappinc.workappserver.common.exception.DatabaseException;
import com.workappinc.workappserver.common.exception.DuplicateDBEntryException;
import com.workappinc.workappserver.common.exception.InternalServerException;
import com.workappinc.workappserver.common.exception.MD5HashingException;
import com.workappinc.workappserver.common.exception.RuntimeSQLException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppUtil;
import com.workappinc.workappserver.dataaccess.orm.core.Persist;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation.WorkAppQbFactoryImp;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbFactory;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;

public class WorkAppServiceManager
{
	private static WorkAppMySQLConnectionManager _connections;
	private static HashMap<String, Object> _configMap;
	private static IApplicationLogger _logger;
	private static WorkAppQbFactory _fac;

	/**
	 * Loads Common Resources as Static references
	 * 
	 * @param connections
	 * @throws SQLException
	 */
	public static void initResource(WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap, IApplicationLogger logger) throws SQLException
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
	public static void registerUser(MultivaluedMap<String, String> queryMap,  User user) throws DuplicateDBEntryException, DatabaseException, InternalServerException
	{
		String queryID = queryMap.getFirst("qid");
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
				_logger.LogDebug("["+queryID+"] "+ user.getEmail() + " already exists in the database.", WorkAppServiceManager.class);
				throw new DuplicateDBEntryException(user.getEmail() + " already exists in the database.", ex);
			}
			else
			{
				_logger.LogException(ex, WorkAppServiceManager.class);
				throw new DatabaseException("["+queryID+"] "+ "Database Exception with messasge:" + ex.getMessage(), ex);
			}
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
			throw new DatabaseException("["+queryID+"] "+ "Database Exception with messasge:" + ex.getMessage(), ex);
		}
		catch (MD5HashingException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
			throw new InternalServerException("["+queryID+"] "+ "Hashing Exception with messasge:" + ex.getMessage(), ex);
		}
		finally
		{
			conn.close();
			persist = null;
			conn = null;
		}
	}

}
