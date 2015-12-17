package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Immutable class to implement all fields.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbAllFieldImp implements WorkAppQbField
{
	WorkAppQbAllFieldImp()
	{
	}

	@Override
	public String toString()
	{
		return "*";
	}
}
