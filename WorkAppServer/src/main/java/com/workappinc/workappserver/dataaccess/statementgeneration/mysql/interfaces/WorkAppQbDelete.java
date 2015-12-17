package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces;

/**
 * An interface to build DELETE queries.
 * 
 * @author dhgovindaraj.
 */
public interface WorkAppQbDelete extends WorkAppQbQuery
{
	/**
	 * Sets the WHERE clause.
	 * 
	 * @return A QbWhere object which is bound to this query.
	 */
	public WorkAppQbWhere where();

	/**
	 * Marks all records for deletion. Either this method or where must be
	 * called.
	 * 
	 * @return This query builder.
	 */
	public WorkAppQbDelete all();

	/**
	 * The table you want to delete from. Must be called to make a valid delete
	 * statement.
	 * 
	 * @param table
	 *            - A table name without backticks.
	 * @return This query builder.
	 */
	public WorkAppQbDelete from(String table);
}
