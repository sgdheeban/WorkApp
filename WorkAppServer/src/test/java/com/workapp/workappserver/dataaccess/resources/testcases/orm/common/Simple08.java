package com.workapp.workappserver.dataaccess.resources.testcases.orm.common;

import com.workapp.workappserver.dataaccess.orm.annotations.Column;
import com.workapp.workappserver.dataaccess.orm.annotations.NoColumn;
import com.workapp.workappserver.dataaccess.orm.annotations.Table;

@Table(name = "simple")
public class Simple08
{

	private long id;
	private String stringCol;
	private long intCol;

	@Column(autoGenerated = true)
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	@NoColumn
	public String getHelloWorld()
	{
		return "hello world";
	}

	public void setHelloWorld(String stringCol)
	{
		/* do nothing */ }

	@NoColumn
	@Column(name = "int_col") // conflicting annotations -- will blow
	public long getIntCol()
	{
		return intCol;
	}

	public void setIntCol(long intCol)
	{
		this.intCol = intCol;
	}

	public String getStringCol()
	{
		return stringCol;
	}

	public void setStringCol(String stringCol)
	{
		this.stringCol = stringCol;
	}

}
