package com.workappinc.workappserver.dataaccess.resources.examples;

import java.util.List;

import com.workappinc.workappserver.dataaccess.resources.WorkAppArgument;
import com.workappinc.workappserver.dataaccess.resources.WorkAppCommandLineArgsReader;

/**
 * Test Example for WorkAppArgument Reading from Command Line and Properties
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppArgumentReaderExample
{

	@WorkAppArgument(alias = "hd", description = "hdfs arg")
	private static Long hdfs;

	@WorkAppArgument(alias = "r", description = "Regular expression to parse lines", required = true)
	private static String regex;

	@WorkAppArgument(alias = "k", description = "Key column", required = true)
	private static String key;

	@WorkAppArgument(alias = "p", description = "Key prefix")
	private static String prefix;

	@WorkAppArgument(alias = "c", description = "Column groups", delimiter = ",")
	private static String[] columns;

	@WorkAppArgument(alias = "n", description = "Column names", delimiter = ",")
	private static String[] names;

	@WorkAppArgument(alias = "h", description = "Redis host")
	private static String host = "localhost";

	@WorkAppArgument(alias = "p", description = "Redis port")
	private static Integer port = 6379;

	private static String printArray(String[] arr)
	{
		if (arr == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		for (String s : arr)
		{
			sb.append(s + ",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		final List<String> parse;
		try
		{
			parse = WorkAppCommandLineArgsReader.parse(WorkAppArgumentReaderExample.class, args);
		}
		catch (IllegalArgumentException e)
		{
			WorkAppCommandLineArgsReader.usage(WorkAppArgumentReaderExample.class);
			System.exit(1);
			return;
		}
		// Use all those parameters...
		System.out.println("hdfs:" + hdfs);
		System.out.println("regex:" + regex);
		System.out.println("key:" + key);
		System.out.println("prefix:" + prefix);
		System.out.println("columns:" + printArray(columns));
		System.out.println("names:" + printArray(names));
		System.out.println("host:" + host);
		System.out.println("port:" + port);
	}
}
