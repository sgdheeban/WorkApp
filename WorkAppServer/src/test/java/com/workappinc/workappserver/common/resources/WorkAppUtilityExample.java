package com.workappinc.workappserver.common.resources;

import java.io.IOException;

/**
 * WorkAppUtilitiesExample is an example class to test all functionalities of
 * WorkAppUtilitiesExample functionalities
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtilityExample
{

	/**
	 * Testing get Process ID
	 */
	private void getPIDTest()
	{
		String pid = WorkAppUtility.getMyPid(null);
		System.out.println("My PID: " + pid);
	}

	/**
	 * Test Get Host Info
	 */
	private void getHostInfoTest()
	{
		String hostname = WorkAppUtility.getMyHostInfo(null, false);
		String hostIP = WorkAppUtility.getMyHostInfo(null, true);
		System.out.println("My Hostname: " + hostname);
		System.out.println("My Hostname: " + hostIP);
	}

	/**
	 * Test Generate MD5 Hash
	 */
	private void generateMD5HashTest()
	{

	}

	/**
	 * Test Generate MD5 Bytes Hash
	 */
	private void generateMD5HashBytesTest()
	{

	}

	/**
	 * Test Generate File CheckSum
	 */
	private void generateFileChecksumTest()
	{

	}

	/**
	 * Test Generate File Checksum String
	 */
	private void generateFileChecksumStringTest()
	{

	}

	/**
	 * Test Encoding String
	 */
	private void encodeStringTest()
	{

	}

	/**
	 * Test Decoding String
	 */
	private void decodeStringTest()
	{

	}

	/**
	 * Test Generating UUID
	 */
	private void generateUUIDTest()
	{

	}

	/**
	 * Test Encrypting String
	 */
	private void encryptMessageTest()
	{

	}

	/**
	 * Test Decrypting String
	 */
	private void decryptMessageTest()
	{

	}

	public static void main(String args[]) throws IOException
	{

		WorkAppUtilityExample example = new WorkAppUtilityExample();

		example.getPIDTest();
		example.getHostInfoTest();
		example.generateMD5HashTest();
		example.generateMD5HashBytesTest();
		example.generateFileChecksumTest();
		example.generateFileChecksumStringTest();
		example.encodeStringTest();
		example.decodeStringTest();
		example.generateUUIDTest();
		example.encryptMessageTest();
		example.decryptMessageTest();

	}
}
