package com.workapp.workappserver.dataaccess.resources.interfaces;

import com.workapp.workappserver.dataaccess.entry.IEntry;

/**
 * IDataManager is a generic interface which queues IEntry objects for a
 * periodic database update
 * 
 * @author dhgovindaraj
 *
 */
public interface IDataManager
{
	/**
	 * Add a dbEntry to the DataManager Queue for batch JDBC processing
	 * 
	 * @param entry
	 */
	public void addEntry(IEntry entry);
}
