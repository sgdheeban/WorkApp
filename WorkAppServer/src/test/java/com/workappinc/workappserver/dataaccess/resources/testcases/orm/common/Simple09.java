
//$Id$

package com.workappinc.workappserver.dataaccess.resources.testcases.orm.common;

import com.workappinc.workappserver.dataaccess.orm.annotations.Column;
import com.workappinc.workappserver.dataaccess.orm.annotations.Table;

@Table(name = "simple")
public class Simple09
{

	private long id;
	// private String stringCol;
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

	// getter returns void -- will blow
	public void getStringCol()
	{
	}

	public void setStringCol(String stringCol)
	{
		/* this.stringCol = stringCol; */ }

	public long getIntCol()
	{
		return intCol;
	}

	public void setIntCol(long intCol)
	{
		this.intCol = intCol;
	}

}
