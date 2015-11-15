package com.workappinc.workappserver.businesslogic;

/**
 * IPage interface is a top-level interface after which all other pages are modeled. 
 * @author dhgovindaraj
 *
 */
public interface IPage {

	/**
	 * Loads Full Page and returns a JSON to the view
	 * @return returns JSON string
	 */
	public String loadPage();
	
	/**
	 * Loads a specific section, identified by name, of the page and returns a JSON to the view
	 * @param name
	 * @return JSON string
	 */
	public String loadSection(String name) ;
	
}
