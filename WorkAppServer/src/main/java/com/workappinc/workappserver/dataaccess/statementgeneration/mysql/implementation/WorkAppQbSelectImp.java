package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import java.util.ArrayList;
import java.util.List;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbSelect;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbWhere;

/**
 * WorkAppQbSelectImp Select Query Builder Implementation
 * 
 * @author dhgovindaraj
 *
 */
class WorkAppQbSelectImp implements WorkAppQbSelect
{
	private int m_offset;
	private int m_limit;
	private boolean m_haveLimit;
	private WorkAppQbField[] m_orderBy;
	private QbOrderBy m_orderByOrder;
	private WorkAppQbWhere m_havingClause;
	private WorkAppQbWhere m_where;
	private WorkAppQbField[] m_groupBy;

	WorkAppQbSelectImp()
	{
	}

	private class JoinInfo
	{
		final WorkAppQbField leftSide;
		final WorkAppQbField rightSide;
		final QbJoinType joinType;
		final String table;

		JoinInfo(WorkAppQbField left, WorkAppQbField right, String tabl, QbJoinType type)
		{
			leftSide = left;
			rightSide = right;
			joinType = type;
			table = tabl;
		}
	}

	private List<JoinInfo> m_joinList;
	private WorkAppQbField[] m_selectFields;
	private boolean m_distinct;
	private String m_table;

	private String joinTypeToString(QbJoinType joinType)
	{
		switch (joinType)
		{
		case DEFAULT:
			return "";
		case LEFT_OUTER:
			return "LEFT OUTER";
		case RIGHT_OUTER:
			return "RIGHT OUTER";
		case INNER:
		case OUTER:
		case LEFT:
		case RIGHT:
		default:
			return joinType.toString();
		}
	}

	@Override
	public String getQueryString()
	{
		StringBuilder builder = new StringBuilder("SELECT ");

		if (m_table == null)
			throw new IllegalStateException("Must specify table");

		if (m_selectFields == null)
			throw new IllegalStateException("Must specify some fields");

		if (m_distinct)
			builder.append("DISTINCT ");

		WorkAppQbCommonImp.joinFieldNames(builder, m_selectFields);
		builder.append(" FROM ");
		builder.append(WorkAppQbCommonImp.protectTableName(m_table));
		builder.append(' ');

		if (m_joinList != null)
		{
			for (JoinInfo join : m_joinList)
			{
				builder.append(joinTypeToString(join.joinType));
				builder.append(" JOIN ");
				builder.append(WorkAppQbCommonImp.protectTableName(join.table));
				builder.append(" ON ");
				builder.append(join.leftSide.toString());
				builder.append(" = ");
				builder.append(join.rightSide.toString());
			}
		}

		if (m_where != null)
			builder.append(m_where.toString());

		if (m_groupBy != null)
		{
			builder.append(" GROUP BY ");
			WorkAppQbCommonImp.joinFieldNames(builder, m_groupBy);
		}

		if (m_havingClause != null)
			builder.append(m_havingClause.toString());

		if (m_orderBy != null)
		{
			builder.append(" ORDER BY ");
			WorkAppQbCommonImp.joinFieldNames(builder, m_orderBy);
			builder.append(' ');
			builder.append(m_orderByOrder.toString());
		}

		if (m_haveLimit)
		{
			builder.append(" LIMIT ");
			builder.append(m_offset);
			builder.append(", ");
			builder.append(m_limit);
		}

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		int idx = 0;
		if (m_havingClause != null)
			idx = m_havingClause.getPlaceholderIndex(placeholderName);

		if (idx == 0 && m_where != null)
			idx = m_where.getPlaceholderIndex(placeholderName);

		if (idx == 0)
			throw new IllegalArgumentException("Placeholder doesn't exist");

		return idx;
	}

	@Override
	public WorkAppQbSelect select(WorkAppQbField... fields)
	{
		m_selectFields = fields;
		return this;
	}

	@Override
	public WorkAppQbSelect from(String table)
	{
		m_table = table;
		return this;
	}

	@Override
	public WorkAppQbWhere where()
	{
		m_where = new WorkAppQbWhereImp(false, 1);
		return m_where;
	}

	@Override
	public WorkAppQbSelect distinct()
	{
		m_distinct = true;
		return this;
	}

	@Override
	public WorkAppQbSelect join(String table, WorkAppQbField field1, WorkAppQbField field2, QbJoinType joinType)
	{
		if (m_joinList == null)
			m_joinList = new ArrayList<JoinInfo>(2);

		m_joinList.add(new JoinInfo(field1, field2, table, joinType));
		return this;
	}

	@Override
	public WorkAppQbSelect join(String table, WorkAppQbField field1, WorkAppQbField field2)
	{
		if (m_joinList == null)
			m_joinList = new ArrayList<JoinInfo>(2);

		m_joinList.add(new JoinInfo(field1, field2, table, QbJoinType.DEFAULT));
		return this;
	}

	@Override
	public WorkAppQbSelect groupBy(WorkAppQbField... fields)
	{
		m_groupBy = fields;
		return this;
	}

	@Override
	public WorkAppQbWhere having()
	{
		m_havingClause = new WorkAppQbWhereImp(true, m_where == null ? 1 : m_where.getPlaceholderCount() + 1);
		return m_havingClause;
	}

	@Override
	public WorkAppQbSelect orderBy(QbOrderBy order, WorkAppQbField... fields)
	{
		m_orderBy = fields;
		m_orderByOrder = order;
		return this;
	}

	@Override
	public WorkAppQbSelect limit(int offset, int count)
	{
		m_offset = offset;
		m_limit = count;
		m_haveLimit = true;
		return this;
	}
}
