package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement aggregate functions.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbAggregateFieldImp implements WorkAppQbField
{
	private final WorkAppQbField m_child;
	private final String m_func;
	private final String m_alias;

	WorkAppQbAggregateFieldImp(WorkAppQbField field, String func, String alias)
	{
		m_child = field;
		m_func = func;
		m_alias = alias;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(m_func);
		builder.append('(');
		builder.append(m_child.toString());
		builder.append(')');

		if (m_alias != null)
		{
			builder.append(" AS ");
			builder.append(m_alias);
		}
		return builder.toString();
	}
}
