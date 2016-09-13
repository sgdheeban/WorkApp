package com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces;

/**
 * Factory interface to create query-builder objects such as fields and queries.
 * Concretely implemented by a class for each database.
 * 
 * @author dhgovindaraj
 */
public interface WorkAppQbFactory
{
	/**
	 * Creates an all fields selector.
	 * 
	 * @return QbField
	 */
	public WorkAppQbField newAllField();

	/**
	 * Creates an all fields selector for the specified table.
	 * 
	 * @param table
	 *            - A table name without backticks.
	 * @return QbField
	 */
	public WorkAppQbField newAllField(String table);

	/**
	 * Creates a standard database field. Will enclose the field in backticks in
	 * the final query.
	 * 
	 * @param name
	 *            - A table column without backticks.
	 * @return QbField
	 */
	public WorkAppQbField newStdField(String name);

	/**
	 * Creates a qualified database field. Will enclose the field and table name
	 * in backticks in the final query.
	 * 
	 * @param table
	 *            - A table name without backticks.
	 * @param name
	 *            - A table column without backticks.
	 * @return QbField
	 */
	public WorkAppQbField newQualifiedField(String table, String name);

	/**
	 * Creates a SUM aggregate function on the specified field.
	 * 
	 * @param field
	 *            - Returned by newStdField.
	 * @param alias
	 *            - A string to use with AS. May be null in which case no AS is
	 *            used.
	 * @return QbField
	 */
	public WorkAppQbField newSum(WorkAppQbField field, String alias);

	/**
	 * Creates a count aggregate function on the specified field.
	 * 
	 * @param field
	 *            - Returned by newStdField.
	 * @param alias
	 *            - A string to use with AS. May be null in which case no AS is
	 *            used.
	 * @return Field
	 */
	public WorkAppQbField newCount(WorkAppQbField field, String alias);

	/**
	 * Creates an average aggregate function on the specified field.
	 * 
	 * @param field
	 *            - Returned by newStdField.
	 * @param alias
	 *            - A string to use with AS. May be null in which case no AS is
	 *            used.
	 * @return QbField
	 */
	public WorkAppQbField newAvg(WorkAppQbField field, String alias);

	/**
	 * Creates a minimum function on the specified field.
	 * 
	 * @param field
	 *            - Returned by newStdField.
	 * @param alias
	 *            - A string to use with AS. May be null in which case no AS is
	 *            used.
	 * @return QbField
	 */
	public WorkAppQbField newMin(WorkAppQbField field, String alias);

	/**
	 * Creates a count aggregate function on the specified field.
	 * 
	 * @param field
	 *            - Returned by newStdField.
	 * @param alias
	 *            - A string to use with AS. May be null in which case no AS is
	 *            used.
	 * @return QbField
	 */
	public WorkAppQbField newMax(WorkAppQbField field, String alias);

	/**
	 * Creates a custom field.
	 * 
	 * @param custom
	 *            - A custom string that will be used as is.
	 * @return QbField
	 */
	public WorkAppQbField newCustomField(String custom);

	/**
	 * Creates a SELECT query.
	 */
	public WorkAppQbSelect newSelectQuery();

	/**
	 * Creates an UPDATE query.
	 */
	public WorkAppQbUpdate newUpdateQuery();

	/**
	 * Creates a DELETE query.
	 */
	public WorkAppQbDelete newDeleteQuery();

	/**
	 * Creates an INSERT query.
	 */
	public WorkAppQbInsert newInsertQuery();

	/**
	 * Creates a query object with the given sql. Only use this if the query
	 * builder can not build an appropriate query.
	 * 
	 * @param sql
	 *            - A custom sql string.
	 * @return QbQuery
	 */
	public WorkAppQbQuery newQuery(String sql);
}
