package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbDelete;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbFactory;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbInsert;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbQuery;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbUpdate;

/**
 * Query Builder Factory Implementation
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppQbFactoryImp implements WorkAppQbFactory
{
	private static WorkAppQbField m_allField = new WorkAppQbAllFieldImp();

	@Override
	public WorkAppQbField newAllField()
	{
		return m_allField;
	}

	@Override
	public WorkAppQbField newAllField(String table)
	{
		return new WorkAppQbAllTableFieldImp(table);
	}

	@Override
	public WorkAppQbField newStdField(String name)
	{
		return new WorkAppQbStdFieldImp(name);
	}

	@Override
	public WorkAppQbField newQualifiedField(String table, String name)
	{
		return new WorkAppQbQualifiedFieldImp(table, name);
	}

	@Override
	public WorkAppQbField newSum(WorkAppQbField field, String alias)
	{
		return new WorkAppQbAggregateFieldImp(field, "SUM", alias);
	}

	@Override
	public WorkAppQbField newCount(WorkAppQbField field, String alias)
	{
		return new WorkAppQbAggregateFieldImp(field, "COUNT", alias);
	}

	@Override
	public WorkAppQbField newAvg(WorkAppQbField field, String alias)
	{
		return new WorkAppQbAggregateFieldImp(field, "AVG", alias);
	}

	@Override
	public WorkAppQbField newMin(WorkAppQbField field, String alias)
	{
		return new WorkAppQbAggregateFieldImp(field, "MIN", alias);
	}

	@Override
	public WorkAppQbField newMax(WorkAppQbField field, String alias)
	{
		return new WorkAppQbAggregateFieldImp(field, "MAX", alias);
	}

	@Override
	public WorkAppQbField newCustomField(String custom)
	{
		return new WorkAppQbCustomFieldImp(custom);
	}

	@Override
	public WorkAppQbSelect newSelectQuery()
	{
		return new WorkAppQbSelectImp();
	}

	@Override
	public WorkAppQbUpdate newUpdateQuery()
	{
		return new WorkAppQbUpdateImp();
	}

	@Override
	public WorkAppQbDelete newDeleteQuery()
	{
		return new WorkAppQbDeleteImp();
	}

	@Override
	public WorkAppQbInsert newInsertQuery()
	{
		return new WorkAppQbInsertImp();
	}

	@Override
	public WorkAppQbQuery newQuery(String sql)
	{
		return new WorkAppQbCustomQuery(sql);
	}
}
