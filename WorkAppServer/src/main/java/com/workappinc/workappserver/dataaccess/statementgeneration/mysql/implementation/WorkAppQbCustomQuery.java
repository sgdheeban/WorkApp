package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbQuery;

/**
 * Immutable class to implement a custom query.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbCustomQuery implements WorkAppQbQuery
{
	private final String m_sql;

	WorkAppQbCustomQuery(String sql)
	{
		m_sql = sql;
	}

	@Override
	public String getQueryString()
	{
		return m_sql;
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		throw new IllegalArgumentException("Placeholder doesn't exist");
	}
}
