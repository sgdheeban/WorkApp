package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement custom field.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbCustomFieldImp implements WorkAppQbField
{
	private final String m_custom;

	WorkAppQbCustomFieldImp(String custom)
	{
		m_custom = custom;
	}

	@Override
	public String toString()
	{
		return m_custom;
	}
}
