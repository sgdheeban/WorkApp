package com.workapp.workappserver.businesslogic.model;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
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
import com.workapp.workappserver.common.resources.implementation.WorkAppConstants;
import com.workapp.workappserver.common.resources.implementation.WorkAppUtil;
import com.workapp.workappserver.dataaccess.orm.core.Persist;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppJDBCConnection;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppMySQLConnectionManager;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation.WorkAppQbFactoryImp;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbDelete;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbFactory;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbInsert;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;

public class ServiceManager
{
	private WorkAppMySQLConnectionManager _connections;
	private HashMap<String, Object> _configMap;
	private IApplicationLogger _logger;
	private WorkAppQbFactory _fac;
	
	/**
	 * Loads Common Resources in constructor
	 * 
	 * @param connections
	 * @throws SQLException
	 */
	public ServiceManager(WorkAppMySQLConnectionManager connections, HashMap<String, Object> configMap, IApplicationLogger logger) throws SQLException
	{
		_connections = connections;
		_configMap = configMap;
		_logger = logger;
		_fac = new WorkAppQbFactoryImp();
	}

	/**
	 * Persists a key, value pair
	 * @param key
	 * @param value
	 * @throws DuplicateDBEntryException 
	 * @throws DatabaseException 
	 */
	public void put(String key, String value ) throws DuplicateDBEntryException, DatabaseException
	{
		WorkAppJDBCConnection conn = null;
		String insertSQL = 
				 new StringBuilder().append("INSERT INTO ")
				.append(_configMap.get("dbSchema").toString() + "." +WorkAppConstants.KVTABLE+"(key_str,value_str)")
				.append(" VALUES('"+key+"','"+value+"')")
				.append("ON DUPLICATE KEY UPDATE value_str='"+value+"'").toString();
		try
		{
			conn = _connections.getConnection();
			Statement statement  = conn.createStatement();
			statement.executeUpdate(insertSQL);
		}
		catch (RuntimeSQLException ex)
		{
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException)
			{
				_logger.LogDebug(ex.getCause(), ServiceManager.class);
				throw new DuplicateDBEntryException("Duplicate DB Entry Exception", ex);
			}
			else
			{
				_logger.LogException(ex.getCause(), ServiceManager.class);
				throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
			}
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex.getCause(), ServiceManager.class);
			throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Retrieve value for key
	 * @param key
	 * @throws DuplicateDBEntryException
	 * @throws DatabaseException
	 */
	public String get(String key) throws DuplicateDBEntryException, DatabaseException
	{
		WorkAppJDBCConnection conn = null;
		String selectSQL = 
				 new StringBuilder().append("SELECT value_str FROM ")
				.append(_configMap.get("dbSchema").toString() + "." +WorkAppConstants.KVTABLE)
				.append(" WHERE key_str ='"+key+"'").toString();
		try
		{
			conn = _connections.getConnection();
			Persist persist = new Persist(conn);
			String valueStr = persist.read(String.class, selectSQL);
			return valueStr;
		}
		catch (RuntimeSQLException ex)
		{
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException)
			{
				_logger.LogDebug(ex.getCause(), ServiceManager.class);
				throw new DuplicateDBEntryException("Duplicate DB Entry Exception", ex);
			}
			else
			{
				_logger.LogException(ex.getCause(), ServiceManager.class);
				throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
			}
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex.getCause(), ServiceManager.class);
			throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Delete the entry given a key
	 * @param key
	 * @throws DuplicateDBEntryException
	 * @throws DatabaseException
	 */
	public void delete(String key) throws DuplicateDBEntryException, DatabaseException
	{
		WorkAppJDBCConnection conn = null;
		String deleteSQL = 
				 new StringBuilder().append("DELETE FROM ")
				.append(_configMap.get("dbSchema").toString() + "." +WorkAppConstants.KVTABLE)
				.append(" WHERE key_str ='"+key+"'").toString();
		try
		{
			conn = _connections.getConnection();
			Statement statement  = conn.createStatement();
			statement.executeUpdate(deleteSQL);
		}
		catch (RuntimeSQLException ex)
		{
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException)
			{
				_logger.LogDebug(ex.getCause(), ServiceManager.class);
				throw new DuplicateDBEntryException("Duplicate DB Entry Exception", ex);
			}
			else
			{
				_logger.LogException(ex.getCause(), ServiceManager.class);
				throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
			}
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex.getCause(), ServiceManager.class);
			throw new DatabaseException( "Database Exception with messasge:" + ex.getMessage(), ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Releases all resources
	 */
	public void exit()
	{
		_connections.close();
	}

}
