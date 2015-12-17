package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import java.util.List;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;

/**
 * Static functions that are used by other implementation classes.
 * 
 * @author DanFickle
 */
class WorkAppQbCommonImp
{
	static String protectTableName(String table)
	{
		return '`' + table + '`';
	}

	static void joinFieldNames(StringBuilder builder, WorkAppQbField[] fields)
	{
		for (int i = 0; i < fields.length; i++)
		{
			builder.append(fields[i].toString());

			if (i != fields.length - 1)
				builder.append(", ");
		}
	}

	static void joinFieldNames(StringBuilder builder, List<WorkAppQbField> fields)
	{
		for (int i = 0; i < fields.size(); i++)
		{
			builder.append(fields.get(i).toString());

			if (i != fields.size() - 1)
				builder.append(", ");
		}
	}

	static void createPlaceholders(StringBuilder builder, int cnt)
	{
		for (int i = 0; i < cnt; i++)
		{
			builder.append('?');

			if (i != cnt - 1)
				builder.append(", ");
		}
	}
}
