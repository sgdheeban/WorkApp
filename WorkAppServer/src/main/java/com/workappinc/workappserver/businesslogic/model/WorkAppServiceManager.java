package com.workappinc.workappserver.businesslogic.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.workappinc.workappserver.businesslogic.model.table.User;
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
	 * @param userJson
	 * @System.out.println(select.getQueryString());return
	 */
	public static boolean registerUser(User userJson)
	{
		boolean isSuccess = false;
		WorkAppJDBCConnection conn = null;
		Persist persist = null;
		try
		{
			conn = _connections.getConnection();
			persist = new Persist(conn);
			String colname = "email";
			String table = "user";
			WorkAppQbSelect select = _fac.newSelectQuery();
			select.select(_fac.newCount(_fac.newStdField(colname), "cnt")).from(table);
			int count = persist.read(int.class, select.getQueryString());
			if (count > 0)
				return isSuccess;
			User user = new User();
			user.setId(WorkAppUtil.generateUUID(_logger, null));
			user.setEmail("dhgovindaraj@ebay.com");
			user.setPassword(WorkAppUtil.generateMD5HashString(_logger, null, "password"));
			user.setFirst_name("dhgovindaraj");
			user.setLast_name("SG");
			persist.insert(user);
			isSuccess = true;
		}
		catch (RuntimeSQLException ex)
		{
			// User/ Email Already Exists etc.
			_logger.LogException(ex, WorkAppServiceManager.class);
		}
		catch (SQLException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
		}
		catch (MD5HashingException ex)
		{
			_logger.LogException(ex, WorkAppServiceManager.class);
		}
		finally
		{
			conn.close();
			persist = null;
			conn = null;
		}
		return isSuccess;
	}

}
