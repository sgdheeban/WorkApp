package com.workappinc.workappserver.dataaccess.resources.testcases;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertiesArgsReader;

/**
 * Test Suits for WorkAppArgument Reading from Command Line and Properties -
 * this is for testing properties specifically
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertiesArgsTestCases
{

	/**
	 * Testing if property parsing works fine
	 */
	@Test
	public void testArgsParseWithProperties()
	{
		TestCommand tc = new TestCommand();
		WorkAppCommandLineArgsReader.usage(tc);
		Properties p = new Properties();
		p.put("input", "inputfile");
		p.put("o", "outputfile");
		p.put("someflag", "true");
		p.put("m", "10");
		p.put("values", "1:2:3");
		p.put("strings", "sam;dave;jolly");
		WorkAppPropertiesArgsReader.parse(tc, p);
		assertEquals("inputfile", tc.inputFilename);
		assertEquals(new File("outputfile"), tc.outputFile);
		assertEquals(true, tc.someflag);
		assertEquals(10, tc.minimum.intValue());
		assertEquals(3, tc.values.length);
		assertEquals(2, tc.values[1].intValue());
		assertEquals("dave", tc.strings[1]);
	}

	public static class TestCommand
	{
		@WorkAppArgument(value = "input", description = "This is the input file", required = true)
		private String inputFilename;

		@WorkAppArgument(value = "output", alias = "o", description = "This is the output file", required = true)
		private File outputFile;

		@WorkAppArgument(description = "This flag can optionally be set")
		private boolean someflag;

		@WorkAppArgument(description = "Minimum", alias = "m")
		private Integer minimum;

		@WorkAppArgument(description = "List of values", delimiter = ":")
		private Integer[] values;

		@WorkAppArgument(description = "List of strings", delimiter = ";")
		private String[] strings;

		@WorkAppArgument(description = "not required")
		private boolean notRequired;

	}
}
