package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement a standard un-qualified field.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbStdFieldImp implements WorkAppQbField
{
	private final String m_fieldName;

	WorkAppQbStdFieldImp(String field)
	{
		m_fieldName = field;
	}

	@Override
	public String toString()
	{
		return '`' + m_fieldName + '`';
	}
}
