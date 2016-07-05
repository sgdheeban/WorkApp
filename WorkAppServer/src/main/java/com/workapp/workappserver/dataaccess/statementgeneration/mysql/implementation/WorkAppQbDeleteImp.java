package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbDelete;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbWhere;

/**
 * Delete Query Builder Impelentation
 * 
 * @author dhgovindaraj
 *
 */
class WorkAppQbDeleteImp implements WorkAppQbDelete
{
	private String m_table;
	private boolean m_all;
	private WorkAppQbWhere m_where;

	@Override
	public String getQueryString()
	{
		if (m_table == null)
			throw new IllegalStateException("Must specify a table");

		if (m_all == false && m_where == null)
			throw new IllegalStateException("Must call all() to delete all records");

		StringBuilder builder = new StringBuilder("DELETE FROM ");
		builder.append(WorkAppQbCommonImp.protectTableName(m_table));

		if (m_where != null)
			builder.append(m_where.toString());

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_where != null)
			return m_where.getPlaceholderIndex(placeholderName);
		else throw new IllegalArgumentException("Placeholder doesn't exist");
	}

	@Override
	public WorkAppQbWhere where()
	{
		m_where = new WorkAppQbWhereImp(false, 1);
		return m_where;
	}

	@Override
	public WorkAppQbDelete all()
	{
		m_all = true;
		return this;
	}

	@Override
	public WorkAppQbDelete from(String table)
	{
		m_table = table;
		return this;
	}
}
