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

/**
 * Tool to run database scripts
 */
public class WorkAppScriptRunnerUtility
{
	/**
	 * Regex to detect delimiter. ignores spaces, allows delimiter in comment, allows an equals-sign
	 */
	public static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", Pattern.CASE_INSENSITIVE);

	private static final String DEFAULT_DELIMITER = ";";
	private final Connection connection;
	private final boolean stopOnError;
	private final boolean autoCommit;
	private String delimiter = DEFAULT_DELIMITER;
	private boolean fullLineDelimiter = false;

	/**
	 * Default constructor
	 */
	public WorkAppScriptRunnerUtility(Connection connection, boolean autoCommit, boolean stopOnError)
	{
		this.connection = connection;
		this.autoCommit = autoCommit;
		this.stopOnError = stopOnError;
	}

	public void setDelimiter(String delimiter, boolean fullLineDelimiter)
	{
		this.delimiter = delimiter;
		this.fullLineDelimiter = fullLineDelimiter;
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter)
	 *
	 * @param reader - the source of the script
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
				}
				else if (delimMatch.matches())
				{
					setDelimiter(delimMatch.group(2), false);
				}
				else if (trimmedLine.startsWith("--"))
				{
					println(trimmedLine);
				}
				else if (trimmedLine.length() < 1 || trimmedLine.startsWith("--"))
				{
					// Do nothing
				}
				else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter())
						|| fullLineDelimiter && trimmedLine.equals(getDelimiter()))
				{
					command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
					command.append(" ");
					Statement statement = conn.createStatement();

					println(command);

					boolean hasResults = false;
					try
					{
						hasResults = statement.execute(command.toString());
					}
					catch (SQLException e)
					{
						final String errText = String.format("Error executing '%s' (line %d): %s", command,
								lineReader.getLineNumber(), e.getMessage());
						if (stopOnError)
						{
							throw new SQLException(errText, e);
						}
						else
						{
							println(errText);
						}
					}

					if (autoCommit && !conn.getAutoCommit())
					{
						conn.commit();
					}

					ResultSet rs = statement.getResultSet();
					if (hasResults && rs != null)
					{
						ResultSetMetaData md = rs.getMetaData();
						int cols = md.getColumnCount();
						for (int i = 0; i < cols; i++)
						{
							String name = md.getColumnLabel(i);
							print(name + "\t");
						}
						println("");
						while (rs.next())
						{
							for (int i = 0; i < cols; i++)
							{
								String value = rs.getString(i);
								print(value + "\t");
							}
							println("");
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
			throw new IOException(String.format("Error executing '%s': %s", command, e.getMessage()), e);
		}
		finally
		{
			conn.rollback();
			flush();
		}
	}

	private String getDelimiter()
	{
		return delimiter;
	}


	private void print(Object o)
	{
		
	}

	private void println(Object o)
	{
		
	}

	private void printlnError(Object o)
	{
		
	}

	private void flush()
	{

	}
}
