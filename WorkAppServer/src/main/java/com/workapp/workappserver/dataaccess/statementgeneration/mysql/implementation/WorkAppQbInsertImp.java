package com.workapp.workappserver.dataaccess.statementgeneration.mysql.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbField;
import com.workapp.workappserver.dataaccess.statementgeneration.mysql.interfaces.WorkAppQbInsert;

/**
 * The default implementation of WorkAppQbInsertImp.
 * 
 * @author dhgovindaraj
 */
class WorkAppQbInsertImp implements WorkAppQbInsert
{
	WorkAppQbInsertImp()
	{
	}

	private String m_table;
	private Map<String, Integer> m_placeholders;
	private List<WorkAppQbField> m_fields;

	@Override
	public String getQueryString()
	{
		if (m_fields == null || m_table == null || m_placeholders == null)
			throw new IllegalStateException("Table name or fields missing");

		StringBuilder builder = new StringBuilder("INSERT INTO ");
		builder.append(WorkAppQbCommonImp.protectTableName(m_table));
		builder.append(" (");
		WorkAppQbCommonImp.joinFieldNames(builder, m_fields);
		builder.append(") VALUES (");
		WorkAppQbCommonImp.createPlaceholders(builder, m_fields.size());
		builder.append(')');

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_placeholders == null)
			throw new IllegalArgumentException("Placeholder doesn't exist");

		Integer idx = m_placeholders.get(placeholderName);

		if (idx == null)
			throw new IllegalArgumentException("Placeholder doesn't exist");
		else return idx;
	}

	@Override
	public WorkAppQbInsert set(WorkAppQbField field, String placeholder)
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
	public WorkAppQbInsert inTable(String table)
	{
		m_table = table;
		return this;
	}
}
