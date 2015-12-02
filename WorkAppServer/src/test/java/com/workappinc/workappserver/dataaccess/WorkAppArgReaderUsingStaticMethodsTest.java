package com.workappinc.workappserver.dataaccess;

import static org.junit.Assert.*;

import java.nio.charset.*;

import org.junit.Before;
import org.junit.Test;

import com.workappinc.workappserver.dataaccess.resources.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.WorkAppCommandLineArgsReader;

/**
 * Test Suits for WorkAppArgument Reading from Command Line and Properties - this is for testing static methods specifically
 * @author dhgovindaraj
 *
 */
public class WorkAppArgReaderUsingStaticMethodsTest {
	private static final String UTF_8_STR = "UTF-8";
	
	@Before
	public void setUp() throws Exception {
		WorkAppCommandLineArgsReader.resetValueCreators();
	}
	
	/**
	 * Testing default charset should be null
	 */
	@Test
	public void testCharsetCannotBeParsedByDefault() {
		CommandCLI cli = new CommandCLI();
		
		String[] args = new String[] {"-charset", UTF_8_STR};
		
		try {
			WorkAppCommandLineArgsReader.parse(cli, args);
			fail("without specified ValueCreator Charset object could not be created");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	/**
	 * Testing if static method invocation is working
	 */
	@Test
	public void testCharsetCanBeParsedByRegisteringAValueCreator() {
		CommandCLI cli = new CommandCLI();
		
		WorkAppCommandLineArgsReader.registerValueCreator(WorkAppCommandLineArgsReader.byStaticMethodInvocation(Charset.class, "forName"));
		
		String[] args = new String[] {"-charset", UTF_8_STR};
		WorkAppCommandLineArgsReader.parse(cli, args);
		
		assertNotNull("charset value not built", cli.getCharset());
		assertTrue("built object is not a " + Charset.class + " class object", Charset.class.isAssignableFrom(cli.getCharset().getClass()));
		assertEquals("retrieved charset is not " + UTF_8_STR, UTF_8_STR, cli.getCharset().name());
	}

	
	public static class CommandCLI {
		@WorkAppArgument()
		private Charset charset;

		public Charset getCharset() {
			return charset;
		}
	}
}