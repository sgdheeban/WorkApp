package com.workapp.workappserver.dataaccess.resources.implementation;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.resources.interfaces.IUtil;

/**
 * <p>
 * Helper class for managing common JDBC tasks.
 * </p>
 *
 * <p>
 * This class is abstract to allow implementations to take advantage of
 * different logging capabilities/interfaces in different parts of the code.
 * </p>
 * 
 * @author dhgovindaraj
 */
abstract public class WorkAppJDBCUtil implements IUtil
{

	/**
	 * An abstract method which child classes override to handle logging of
	 * errors in their particular environments.
	 *
	 * @param errorString
	 *            the error message generated
	 */
	abstract protected void delegatedLog(String errorString, IApplicationLogger logger);

	/**
	 * Checks database metadata to see if a table exists. Try UPPER, lower, and
	 * MixedCase, to see if the table is there.
	 *
	 * @param dbMetaData
	 *            the database metadata to be used to look up this table
	 * @param tableName
	 *            the table name
	 *
	 * @throws SQLException
	 *             if an exception is encountered while accessing the database
	 */
	public static boolean tableExists(DatabaseMetaData dbMetaData, String tableName, IApplicationLogger logger) throws SQLException
	{
		return (tableExistsCaseSensitive(dbMetaData, tableName, logger) || tableExistsCaseSensitive(dbMetaData, tableName.toUpperCase(Locale.US), logger) || tableExistsCaseSensitive(dbMetaData, tableName.toLowerCase(Locale.US), logger));
	}

	/**
	 * Checks database metadata to see if a table exists. This method is
	 * sensitive to the case of the provided table name.
	 *
	 * @param dbMetaData
	 *            the database metadata to be used to look up this table
	 * @param tableName
	 *            the case sensitive table name
	 *
	 * @throws SQLException
	 *             if an exception is encountered while accessing the database
	 */
	public static boolean tableExistsCaseSensitive(DatabaseMetaData dbMetaData, String tableName, IApplicationLogger logger) throws SQLException
	{
		ResultSet rsTables = dbMetaData.getTables(null, null, tableName, null);
		try
		{
			boolean found = rsTables.next();
			return found;
		}
		finally
		{
			closeJDBCResultSet(rsTables, logger);
		}
	}

	/**
	 * Checks database metadata to see if a column exists in a table Try UPPER,
	 * lower, and MixedCase, both on the table name and the column name, to see
	 * if the column is there.
	 *
	 * @param dbMetaData
	 *            the database metadata to be used to look up this column
	 * @param tableName
	 *            the table name
	 * @param columnName
	 *            the column name
	 *
	 * @throws SQLException
	 *             if an exception is encountered while accessing the database
	 */
	public static boolean columnExists(DatabaseMetaData dbMetaData, String tableName, String columnName, IApplicationLogger logger) throws SQLException
	{
		return (columnExistsCaseSensitive(dbMetaData, tableName, columnName, logger) || columnExistsCaseSensitive(dbMetaData, tableName, columnName.toUpperCase(Locale.US), logger) || columnExistsCaseSensitive(dbMetaData, tableName, columnName.toLowerCase(Locale.US), logger) || columnExistsCaseSensitive(dbMetaData, tableName.toUpperCase(Locale.US), columnName, logger) || columnExistsCaseSensitive(dbMetaData, tableName.toUpperCase(Locale.US), columnName.toUpperCase(Locale.US), logger) || columnExistsCaseSensitive(dbMetaData, tableName.toUpperCase(Locale.US), columnName.toLowerCase(Locale.US), logger) || columnExistsCaseSensitive(dbMetaData, tableName.toLowerCase(Locale.US), columnName, logger) || columnExistsCaseSensitive(dbMetaData, tableName.toLowerCase(Locale.US), columnName.toUpperCase(Locale.US), logger) || columnExistsCaseSensitive(dbMetaData, tableName.toLowerCase(Locale.US), columnName.toLowerCase(Locale.US), logger));
	}

	/**
	 * Checks database metadata to see if a column exists in a table. This
	 * method is sensitive to the case of both the provided table name and
	 * column name.
	 *
	 * @param dbMetaData
	 *            the database metadata to be used to look up this column
	 * @param tableName
	 *            the case sensitive table name
	 * @param columnName
	 *            the case sensitive column name
	 *
	 * @throws SQLException
	 *             if an exception is encountered while accessing the database
	 */
	public static boolean columnExistsCaseSensitive(DatabaseMetaData dbMetaData, String tableName, String columnName, IApplicationLogger logger) throws SQLException
	{
		ResultSet rsTables = dbMetaData.getColumns(null, null, tableName, columnName);
		try
		{
			boolean found = rsTables.next();
			return found;
		}
		finally
		{
			closeJDBCResultSet(rsTables, logger);
		}
	}

	/**
	 * Closes database result set and logs if an error is encountered
	 *
	 * @param aResultSet
	 *            the result set to be closed
	 */
	public static void closeJDBCResultSet(ResultSet aResultSet, IApplicationLogger logger)
	{
		try
		{
			if (aResultSet != null)
			{
				aResultSet.close();
			}
		}
		catch (SQLException ex)
		{
			logger.LogError("Unable to close JDBCResulset: " + ex, WorkAppJDBCUtil.class);
		}
	}
}
