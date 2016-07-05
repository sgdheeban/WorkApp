package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement all fields for a selected table.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbAllTableFieldImp implements WorkAppQbField
{
	private final String m_table;

	WorkAppQbAllTableFieldImp(String table)
	{
		m_table = table;
	}

	@Override
	public String toString()
	{
		return WorkAppQbCommonImp.protectTableName(m_table) + ".*";
	}
}
