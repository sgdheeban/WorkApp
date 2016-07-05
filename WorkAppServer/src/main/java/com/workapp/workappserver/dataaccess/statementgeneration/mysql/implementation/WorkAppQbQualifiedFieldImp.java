package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement qualified fields.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbQualifiedFieldImp implements WorkAppQbField
{
	private final String m_table;
	private final String m_field;

	WorkAppQbQualifiedFieldImp(String table, String field)
	{
		m_table = table;
		m_field = field;
	}

	@Override
	public String toString()
	{
		return WorkAppQbCommonImp.protectTableName(m_table) + ".`" + m_field + '`';
	}
}
