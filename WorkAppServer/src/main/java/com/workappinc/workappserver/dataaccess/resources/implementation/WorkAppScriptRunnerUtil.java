package com.workappinc.workappserver.dataaccess.resources.implementation;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.IUtil;
import com.workappinc.workappserver.dataaccess.resources.interfaces.IReader;

/**
 * WorkAppScriptRunnerUtility runs database scripts
 * 
 * @author dhgovindaraj
 */
public class WorkAppScriptRunnerUtil implements IUtil
{
	/**
	 * Regex to detect delimiter. ignores spaces, allows delimiter in comment,
	 * allows an equals-sign
	 */
	public static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", Pattern.CASE_INSENSITIVE);

	private static final String DEFAULT_DELIMITER = ";";
	private final Connection connection;
	private final boolean stopOnError;
	private final boolean autoCommit;
	private String delimiter = DEFAULT_DELIMITER;
	private boolean fullLineDelimiter = false;

	private IApplicationLogger mLogger = null;

	public WorkAppScriptRunnerUtil(Connection connection, boolean autoCommit, boolean stopOnError, IApplicationLogger logger)
	{
		this.connection = connection;
		this.autoCommit = autoCommit;
		this.stopOnError = stopOnError;
		this.mLogger = logger;
	}

	public void setDelimiter(String delimiter, boolean fullLineDelimiter)
	{
		this.delimiter = delimiter;
		this.fullLineDelimiter = fullLineDelimiter;
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter)
	 *
	 * @param reader
	 *            - the source of the script
	 */
	public void runScript(Reader reader) throws IOException, SQLException
	{
		try
		{
			boolean originalAutoCommit = connection.getAutoCommit();
			try
			{
				if (originalAutoCommit != this.autoCommit)
				{
					connection.setAutoCommit(this.autoCommit);
				}
				runScript(connection, reader);
			}
			finally
			{
				connection.setAutoCommit(originalAutoCommit);
			}
		}
		catch (IOException | SQLException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error running script.  Cause: " + e, e);
		}
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter) using the
	 * connection passed in
	 *
	 * @param conn
	 *            - the connection to use for the script
	 * @param reader
	 *            - the source of the script
	 * @throws SQLException
	 *             if any SQL errors occur
	 * @throws IOException
	 *             if there is an error reading from the Reader
	 */
	private void runScript(Connection conn, Reader reader) throws IOException, SQLException
	{
		StringBuffer command = null;
		try
		{
			LineNumberReader lineReader = new LineNumberReader(reader);
			String line;
			while ((line = lineReader.readLine()) != null)
			{
				if (command == null)
				{
					command = new StringBuffer();
				}
				String trimmedLine = line.trim();
				final Matcher delimMatch = delimP.matcher(trimmedLine);
				if (trimmedLine.length() < 1 || trimmedLine.startsWith("//"))
				{
					// Do nothing
					if (mLogger != null)
						mLogger.LogDebug(trimmedLine, WorkAppScriptRunnerUtil.class);
				}
				else if (delimMatch.matches())
				{
					setDelimiter(delimMatch.group(2), false);
				}
				else if (trimmedLine.startsWith("--"))
				{
					// Do nothing
					if (mLogger != null)
						mLogger.LogDebug(trimmedLine, WorkAppScriptRunnerUtil.class);
				}
				else if (trimmedLine.length() < 1 || trimmedLine.startsWith("--"))
				{
					// Do nothing
					if (mLogger != null)
						mLogger.LogDebug(trimmedLine, WorkAppScriptRunnerUtil.class);
				}
				else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter()) || fullLineDelimiter && trimmedLine.equals(getDelimiter()))
				{
					command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
					command.append(" ");
					Statement statement = conn.createStatement();

					if (mLogger != null)
						mLogger.LogDebug(command, WorkAppScriptRunnerUtil.class);

					boolean hasResults = false;
					try
					{
						hasResults = statement.execute(command.toString());
					}
					catch (SQLException ex)
					{
						final String errText = String.format("Error executing '%s' (line %d): %s", command, lineReader.getLineNumber(), ex.getMessage());
						if (stopOnError)
						{
							if (mLogger != null)
								mLogger.LogException(ex, WorkAppScriptRunnerUtil.class);

							throw new SQLException(errText, ex);
						}
						else
						{
							if (mLogger != null)
								mLogger.LogError(errText, WorkAppScriptRunnerUtil.class);
						}
					}

					if (autoCommit && !conn.getAutoCommit())
					{
						conn.commit();
					}

					ResultSet rs = statement.getResultSet();
					StringBuilder sb = new StringBuilder();
					if (hasResults && rs != null)
					{
						ResultSetMetaData md = rs.getMetaData();
						int cols = md.getColumnCount();
						for (int i = 1; i <= cols; i++)
						{
							String name = md.getColumnLabel(i);
							sb.append(name + "\t");
						}

						if (mLogger != null)
							mLogger.LogError(sb.toString(), WorkAppScriptRunnerUtil.class);

						sb.setLength(0);
						while (rs.next())
						{
							for (int i = 1; i <= cols; i++)
							{
								String value = rs.getString(i);
								sb.append(value + "\t");
							}
							if (mLogger != null)
								mLogger.LogError(sb.toString(), WorkAppScriptRunnerUtil.class);
							sb.setLength(0);
						}
					}
					command = null;
					try
					{
						statement.close();
					}
					catch (Exception e)
					{
						// Ignore to workaround a bug in Jakarta DBCP
					}
				}
				else
				{
					command.append(line);
					command.append("\n");
				}
			}
			if (!autoCommit)
			{
				conn.commit();
			}
		}
		catch (Exception e)
		{
			if (mLogger != null)
				mLogger.LogError(e, WorkAppScriptRunnerUtil.class);

			throw new IOException(String.format("Error executing '%s': %s", command, e.getMessage()), e);
		}
		finally
		{
			conn.rollback();
		}
	}

	private String getDelimiter()
	{
		return delimiter;
	}

}
