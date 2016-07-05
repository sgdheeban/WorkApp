package com.workapp.workappserver.dataaccess.resources.testcases;

import org.junit.Test;

import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppArgument;
import com.workapp.workappserver.dataaccess.resources.implementation.WorkAppCommandLineArgsReader;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

/**
 * Test Suits for WorkAppArgument Reading from Command Line and Properties
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppArgReaderTestCase
{

	/**
	 * Testing if Arguments can be passed
	 */
	@Test
	public void testArgsParse()
	{
		TestCommand tc = new TestCommand();
		WorkAppCommandLineArgsReader.usage(tc);
		String[] args =
		{
				"-input", "inputfile", "-o", "outputfile", "extra1", "extra2", "-someflag", "-m", "10", "-values", "1:2:3", "-strings", "sam;dave;jolly"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(tc, args);
		assertEquals("inputfile", tc.inputFilename);
		assertEquals(new File("outputfile"), tc.outputFile);
		assertEquals(true, tc.someflag);
		assertEquals(10, tc.minimum.intValue());
		assertEquals(3, tc.values.length);
		assertEquals(2, tc.values[1].intValue());
		assertEquals("dave", tc.strings[1]);
		assertEquals(2, extra.size());
	}

	/**
	 * Testing if multiple Arguments can be passed
	 */
	@Test
	public void testMultipleArgs()
	{
		TestCommand tc = new TestCommand();
		WorkAppCommandLineArgsReader.usage(tc);
		String[] args =
		{
				"-input", "inputfile", "-o", "outputfile", "-someflag", "-m", "10", "extra1", "extra2", "-values", "1", "-values", "2", "-values", "3", "-strings", "sam", "-strings", "dave", "-strings", "jolly"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(tc, args);
		assertEquals("inputfile", tc.inputFilename);
		assertEquals(new File("outputfile"), tc.outputFile);
		assertEquals(true, tc.someflag);
		assertEquals(10, tc.minimum.intValue());
		assertEquals(3, tc.values.length);
		assertEquals(2, tc.values[1].intValue());
		assertEquals("dave", tc.strings[1]);
		assertEquals(2, extra.size());
	}

	/**
	 * Testing if static fields can be used
	 */
	@Test
	public void testStaticFields()
	{
		WorkAppCommandLineArgsReader.usage(TestCommand4.class);
		String[] args =
		{
				"-input", "inputfile", "-output", "outputfile"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(TestCommand4.class, args);
		assertEquals("inputfile", TestCommand4.input);
		assertEquals("outputfile", TestCommand4.output);
	}

	/**
	 * Testing if the code catches the bad args edge cases
	 */
	@Test
	public void testBadArgsParse()
	{
		String[] args =
		{
				"-fred", "inputfile", "-output", "outputfile"
		};
		try
		{
			List<String> extra = WorkAppCommandLineArgsReader.parse(TestCommand4.class, args);
			fail("Should have thrown an exception");
		}
		catch (IllegalArgumentException iae)
		{
			assertEquals("Invalid argument: -fred", iae.getMessage());
		}
		args = new String[]
		{
				"-input", "inputfile"
		};
		try
		{
			List<String> extra = WorkAppCommandLineArgsReader.parse(TestCommand4.class, args);
			fail("Should have thrown an exception");
		}
		catch (IllegalArgumentException iae)
		{
			assertEquals("You must set argument output", iae.getMessage());
		}
	}

	/**
	 * Testing if extra args and method args are caught properly
	 */
	@Test
	public void testMethodArgsParse()
	{
		TestCommand2 tc = new TestCommand2();
		WorkAppCommandLineArgsReader.usage(tc);
		String[] args =
		{
				"-input", "inputfile", "-o", "outputfile", "extra1", "-someflag", "extra2", "-m", "10", "-values", "1:2:3"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(tc, args);
		assertEquals("inputfile", tc.inputFilename);
		assertEquals(new File("outputfile"), tc.outputFile);
		assertEquals(true, tc.someflag);
		assertEquals(10, tc.minimum.intValue());
		assertEquals(3, tc.values.length);
		assertEquals(2, tc.values[1].intValue());
		assertEquals(2, extra.size());
	}

	/**
	 * Tests if mixed org are caught - basic safety edge case testing
	 */
	@Test
	public void testMixedArgsParse()
	{
		TestCommand3 tc = new TestCommand3();
		WorkAppCommandLineArgsReader.usage(tc);
		String[] args =
		{
				"-input", "inputfile", "-o", "outputfile", "extra1", "-someflag", "extra2", "-m", "10", "-values", "1:2:3"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(tc, args);
		assertEquals("inputfile", tc.inputFilename);
		assertEquals(new File("outputfile"), tc.outputFile);
		assertEquals(true, tc.someflag);
		assertEquals(10, tc.minimum.intValue());
		assertEquals(3, tc.values.length);
		assertEquals(2, tc.values[1].intValue());
		assertEquals(2, extra.size());
	}

	/**
	 * Testing if static commands are caught properly
	 */
	@Test
	public void testStaticCommand()
	{
		WorkAppCommandLineArgsReader.usage(StaticTestCommand.class);
		String[] args =
		{
				"-num", "1", "extra"
		};
		List<String> extra = WorkAppCommandLineArgsReader.parse(StaticTestCommand.class, args);
		assertEquals(1, (int) StaticTestCommand.num);
		assertEquals("extra", extra.get(0));
	}

	/**
	 * Testing scenrio where command would be passed as in a command line
	 */
	@Test
	public void testDerivedCommand()
	{
		String[] args =
		{
				"-help", "-verbose"
		};
		TestCommand6 tc = new TestCommand6();
		WorkAppCommandLineArgsReader.parse(tc, args);
		assertTrue(tc.help);
		assertTrue(tc.verbose);
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
		public void setValues(Integer[] values)
		{
			this.values = values;
		}

		public Integer[] getValues()
		{
			return values;
		}

		private Integer[] values;

		@WorkAppArgument(description = "List of strings", delimiter = ";")
		private String[] strings;

		@WorkAppArgument(description = "not required")
		private boolean notRequired;

	}

	public static class StaticTestCommand
	{
		@WorkAppArgument
		private static Integer num;
	}

	public static class TestCommand2
	{
		private String inputFilename;

		private File outputFile;

		private boolean someflag;

		private Integer minimum = 0;

		private Integer[] values = new Integer[]
		{
				10
		};

		public String getInputFilename()
		{
			return inputFilename;
		}

		@WorkAppArgument(value = "input", description = "This is the input file", required = true)
		public void setInputFilename(String inputFilename)
		{
			this.inputFilename = inputFilename;
		}

		public File getOutputFile()
		{
			return outputFile;
		}

		@WorkAppArgument(value = "output", alias = "o", description = "This is the output file", required = true)
		public void setOutputFile(File outputFile)
		{
			this.outputFile = outputFile;
		}

		public boolean isSomeflag()
		{
			return someflag;
		}

		@WorkAppArgument(description = "This flag can optionally be set")
		public void setSomeflag(boolean someflag)
		{
			this.someflag = someflag;
		}

		public Integer getMinimum()
		{
			return minimum;
		}

		@WorkAppArgument(description = "Minimum", alias = "m")
		public void setMinimum(Integer minimum)
		{
			this.minimum = minimum;
		}

		public Integer[] getValues()
		{
			return values;
		}

		@WorkAppArgument(description = "List of values", delimiter = ":")
		public void setValues(Integer[] values)
		{
			this.values = values;
		}
	}

	public static class TestCommand3
	{
		private String inputFilename;

		@WorkAppArgument(value = "output", alias = "o", description = "This is the output file", required = true)
		private File outputFile;

		private boolean someflag;

		private boolean someotherflag;

		private Integer minimum;

		private Integer[] values;

		public String getInputFilename()
		{
			return inputFilename;
		}

		@WorkAppArgument(value = "input", description = "This is the input file", required = true)
		public void setInputFilename(String inputFilename)
		{
			this.inputFilename = inputFilename;
		}

		public File getOutputFile()
		{
			return outputFile;
		}

		public void setOutputFile(File outputFile)
		{
			this.outputFile = outputFile;
		}

		public boolean isSomeflag()
		{
			return someflag;
		}

		@WorkAppArgument(description = "This flag can optionally be set")
		public void setSomeflag(boolean someflag)
		{
			this.someflag = someflag;
		}

		@WorkAppArgument(description = "This flag can optionally be set")
		public void setSomeotherflag(boolean someotherflag)
		{
			this.someotherflag = someotherflag;
		}

		public Integer getMinimum()
		{
			return minimum;
		}

		@WorkAppArgument(description = "Minimum", alias = "m")
		public void setMinimum(Integer minimum)
		{
			this.minimum = minimum;
		}

		public Integer[] getValues()
		{
			return values;
		}

		@WorkAppArgument(description = "List of values", delimiter = ":")
		public void setValues(Integer[] values)
		{
			this.values = values;
		}
	}

	public static class TestCommand4
	{
		@WorkAppArgument()
		private static String input;

		@WorkAppArgument(required = true)
		private static String output;
	}

	public static abstract class TestCommand5
	{
		@WorkAppArgument
		public boolean help;
	}

	public static class TestCommand6 extends TestCommand5
	{
		@WorkAppArgument
		public boolean verbose;
	}
}
