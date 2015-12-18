package com.workappinc.workappserver.dataaccess.resources.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;

/**
 * Test Suits for WorkAppArgument Reading from Command Line and Properties -
 * using Enum
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppArgReaderEnumTestCases
{

	/**
	 * Testing if Enum Values can be used
	 */
	@Test
	public void testCanUseAnEnumValue()
	{
		CommandCLI cli = new CommandCLI();
		String[] args = new String[]
		{
				"-command", "START"
		};
		WorkAppCommandLineArgsReader.parse(cli, args);

		assertNotNull("Commands enum value not built", cli.getCommand());
		assertEquals("retrieved command value is not Commands.START", Commands.START, cli.getCommand());
	}

	public static class CommandCLI
	{
		@WorkAppArgument()
		private Commands command;

		public Commands getCommand()
		{
			return command;
		}
	}

	public static enum Commands
	{
		START, STOP, PAUSE;
	}
}
