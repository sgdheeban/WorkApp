package com.workappinc.workappserver.dataaccess.statementgeneration.mysql.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbUpdate;
import com.workappinc.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbWhere;

/**
 * Update Query Builder Implementation
 * 
 * @author dhgovindaraj
 *
 */
class WorkAppQbUpdateImp implements WorkAppQbUpdate
{
	private String m_table;
	private Map<String, Integer> m_placeholders;
	private List<WorkAppQbField> m_fields;
	private boolean m_all;
	private WorkAppQbWhere m_where;

	WorkAppQbUpdateImp()
	{
	}

	@Override
	public String getQueryString()
	{
		if (m_fields == null || m_table == null || m_placeholders == null)
			throw new IllegalStateException("Table name or fields missing");

		if (m_where == null && m_all == false)
			throw new IllegalStateException("Must call all() to update all records");

		StringBuilder builder = new StringBuilder("UPDATE ");
		builder.append(WorkAppQbCommonImp.protectTableName(m_table));
		builder.append(" SET ");

		int fieldCnt = 0;
		for (WorkAppQbField field : m_fields)
		{
			builder.append(field.toString());
			builder.append(" = ?");

			if (fieldCnt != m_fields.size() - 1)
				builder.append(", ");
			fieldCnt++;
		}

		if (m_where != null)
			builder.append(m_where.toString());

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_placeholders == null)
			throw new IllegalArgumentException("Placeholder doesn't exist");

		Integer idx = m_placeholders.get(placeholderName);

		if (idx == null)
		{
			idx = m_where.getPlaceholderIndex(placeholderName);
			if (idx == 0)
				throw new IllegalArgumentException("Placeholder doesn't exist");
		}
		return idx;
	}

	@Override
	public WorkAppQbUpdate set(WorkAppQbField field, String placeholder)
	{
		if (m_fields == null)
			m_fields = new ArrayList<WorkAppQbField>();
		if (m_placeholders == null)
			m_placeholders = new HashMap<String, Integer>();

		if (m_placeholders.containsKey(placeholder))
			throw new IllegalArgumentException("Duplicate placeholder");

		m_fields.add(field);
		m_placeholders.put(placeholder, m_placeholders.size() + 1);
		return this;
	}

	@Override
	public WorkAppQbWhere where()
	{
		m_where = new WorkAppQbWhereImp(false, m_placeholders == null ? 1 : m_placeholders.size() + 1);
		return m_where;
	}

	@Override
	public WorkAppQbUpdate all()
	{
		m_all = true;
		return this;
	}

	@Override
	public WorkAppQbUpdate inTable(String table)
	{
		m_table = table;
		return this;
	}

}
