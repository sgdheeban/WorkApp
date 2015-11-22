package com.workappinc.workappserver.common.resources;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * WorkAppUtilitiesTestCases cover JUnit test cases to test all functionalities
 * of WorkAppUtilitiesExample functionalities
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtilityTestCases
{
	/**
	 * Testing get Process ID
	 */
	@Test
	public void getPIDTest()
	{
		String pid1 = WorkAppUtility.getMyPid(null);
		String pid2 = WorkAppUtility.getMyPid(null);
		assertEquals(pid1, pid2);
	}
	
	/**
	 * Test Get Host Info
	 */
	@Test
	public void getHostInfoTest()
	{
		String hostname1 = WorkAppUtility.getMyHostInfo(null, false);
		String hostname2 = WorkAppUtility.getMyHostInfo(null, false);
		String hostIP1 = WorkAppUtility.getMyHostInfo(null, true);
		String hostIP2 = WorkAppUtility.getMyHostInfo(null, true);
		assertEquals(hostname1, hostname2);
		assertEquals(hostIP1, hostIP2);
	}
	
	/**
	 * Test Generate MD5 Hash
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void generateMD5HashTest() throws NoSuchAlgorithmException
	{
		String MD5HashString1 = WorkAppUtility.generateMD5HashString(null, "sgd");
		String MD5HashString2 = WorkAppUtility.generateMD5HashString(null, "sgd");
		assertEquals(MD5HashString1, MD5HashString2);
	}
	
	/**
	 * Test Generate MD5 Bytes Hash
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void generateMD5HashBytesTest() throws NoSuchAlgorithmException
	{
		byte[] MD5HashBytes1 = WorkAppUtility.generateMD5HashBytes(null, "sgd");
		byte[] MD5HashBytes2 = WorkAppUtility.generateMD5HashBytes(null, "sgd");
		assertArrayEquals(MD5HashBytes1, MD5HashBytes2);
	}
	
	/**
	 * Test Generate File CheckSum
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void generateFileChecksumTest() throws NoSuchAlgorithmException, IOException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		String fileChecksumString1 = WorkAppUtility.generateFileChecksumString(null, absolutePath);
		String fileChecksumString2 = WorkAppUtility.generateFileChecksumString(null, absolutePath);
		assertEquals(fileChecksumString1, fileChecksumString2);
	}

	/**
	 * Test Generate File Checksum String
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void generateFileChecksumStringTest() throws NoSuchAlgorithmException, IOException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		byte[] fileChecksumBytes1 = WorkAppUtility.generateFileChecksumBytes(null, absolutePath);
		byte[] fileChecksumBytes2 = WorkAppUtility.generateFileChecksumBytes(null, absolutePath);
		assertArrayEquals(fileChecksumBytes1, fileChecksumBytes2);
	}

	/**
	 * Test Encoding String
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void encodeDecodeStringTest() throws NoSuchAlgorithmException, IOException
	{
		String sourceStr = "test-str22";
		String encodedString = WorkAppUtility.encodeString(null, sourceStr);
		String decodedString = WorkAppUtility.decodeString(null, encodedString);
		assertEquals(sourceStr, decodedString);
	}

	/**
	 * Test Generating UUID
	 */
	@Test
	public void generateUUIDTest()
	{
		String uuid1 = WorkAppUtility.generateUUID(null);
		String uuid2 = WorkAppUtility.generateUUID(null);
		assertNotEquals(uuid1, uuid2);
	}

	

}
