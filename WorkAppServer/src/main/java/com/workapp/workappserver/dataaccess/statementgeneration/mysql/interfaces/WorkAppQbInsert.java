package com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces;

/**
 * An interface to generate INSERT queries.
 * 
 * @author dhgovindaraj
 */
public interface WorkAppQbInsert extends WorkAppQbQuery
{
	/**
	 * Sets a database column placeholder.
	 * 
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public WorkAppQbInsert set(WorkAppQbField field, String placeholder);

	/**
	 * Sets the table to insert into. Must be called to make a valid insert
	 * statement.
	 * 
	 * @param table
	 * @return This query builder.
	 */
	public WorkAppQbInsert inTable(String table);
}
