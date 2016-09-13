package com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces;

/**
 * Interface to generate an update query.
 * 
 * @author dhgovindaraj.
 */
public interface WorkAppQbUpdate extends WorkAppQbQuery
{
	/**
	 * Sets a field to the specified placeholder. May be called multiple times.
	 * 
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public WorkAppQbUpdate set(WorkAppQbField field, String placeholder);

	/**
	 * Adds a where clause. Very important. Call only once per QbUpdate object.
	 * 
	 * @return A QbWhere object that is bound to this query.
	 */
	public WorkAppQbWhere where();

	/**
	 * Signals that you want to update all records. If neither where or all is
	 * called the query builder will throw an exception.
	 * 
	 * @return This query builder.
	 */
	public WorkAppQbUpdate all();

	/**
	 * Which table to update. Table name should not contain backticks. Must be
	 * called to make a valid update query.
	 * 
	 * @param table
	 * @return This query builder.
	 */
	public WorkAppQbUpdate inTable(String table);
}