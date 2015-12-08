package com.workappinc.workappserver.common.resources;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;

import org.junit.Test;

import com.workappinc.workappserver.common.exception.CryptoException;
import com.workappinc.workappserver.common.exception.MD5HashingException;
import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppUtility;

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
	 * 
	 * @throws SystemException
	 */
	@Test
	public void getPIDTest() throws SystemException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String pid1 = WorkAppUtility.getMyPid(logger, null);
		String pid2 = WorkAppUtility.getMyPid(logger, null);
		assertEquals(pid1, pid2);
	}

	/**
	 * Test Get Host Info
	 * 
	 * @throws SystemException
	 */
	@Test
	public void getHostInfoTest() throws SystemException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String hostname1 = WorkAppUtility.getMyHostInfo(logger, null, false);
		String hostname2 = WorkAppUtility.getMyHostInfo(logger, null, false);
		String hostIP1 = WorkAppUtility.getMyHostInfo(logger, null, true);
		String hostIP2 = WorkAppUtility.getMyHostInfo(logger, null, true);
		assertEquals(hostname1, hostname2);
		assertEquals(hostIP1, hostIP2);
	}

	/**
	 * Test Generate MD5 Hash
	 * 
	 * @throws MD5HashingException
	 */
	@Test
	public void generateMD5HashTest() throws MD5HashingException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String MD5HashString1 = WorkAppUtility.generateMD5HashString(logger, null, "sgd");
		String MD5HashString2 = WorkAppUtility.generateMD5HashString(logger, null, "sgd");
		assertEquals(MD5HashString1, MD5HashString2);
	}

	/**
	 * Test Generate MD5 Bytes Hash
	 * 
	 * @throws MD5HashingException
	 */
	@Test
	public void generateMD5HashBytesTest() throws MD5HashingException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		byte[] MD5HashBytes1 = WorkAppUtility.generateMD5HashBytes(logger, null, "sgd");
		byte[] MD5HashBytes2 = WorkAppUtility.generateMD5HashBytes(logger, null, "sgd");
		assertArrayEquals(MD5HashBytes1, MD5HashBytes2);
	}

	/**
	 * Test Generate File CheckSum
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	@Test
	public void generateFileChecksumTest() throws MD5HashingException, SystemException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		String fileChecksumString1 = WorkAppUtility.generateFileChecksumString(logger, null, absolutePath);
		String fileChecksumString2 = WorkAppUtility.generateFileChecksumString(logger, null, absolutePath);
		assertEquals(fileChecksumString1, fileChecksumString2);
	}

	/**
	 * Test Generate File Checksum String
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	@Test
	public void generateFileChecksumStringTest() throws MD5HashingException, SystemException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		byte[] fileChecksumBytes1 = WorkAppUtility.generateFileChecksumBytes(logger, null, absolutePath);
		byte[] fileChecksumBytes2 = WorkAppUtility.generateFileChecksumBytes(logger, null, absolutePath);
		assertArrayEquals(fileChecksumBytes1, fileChecksumBytes2);
	}

	/**
	 * Test Encoding String
	 */
	@Test
	public void encodeDecodeStringTest()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String sourceStr = "test-str22";
		String encodedString = WorkAppUtility.encodeString(logger, null, sourceStr);
		String decodedString = WorkAppUtility.decodeString(logger, null, encodedString);
		assertEquals(sourceStr, decodedString);
	}

	/**
	 * Test Generating UUID
	 */
	@Test
	public void generateUUIDTest()
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String uuid1 = WorkAppUtility.generateUUID(logger, null);
		String uuid2 = WorkAppUtility.generateUUID(logger, null);
		assertNotEquals(uuid1, uuid2);
	}

	/**
	 * Test Encrypting String
	 * 
	 * @throws CryptoException
	 */
	@Test
	public void encryptDecryptMessageTest() throws CryptoException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		String origString = "this is a test string\n";
		SecretKey secretKey = WorkAppUtility.generateAESRandomKey(logger);
		String encryptedText = WorkAppUtility.encryptString(logger, null, origString, secretKey);
		String decryptedText = WorkAppUtility.decryptString(logger, null, encryptedText, secretKey);
		assertEquals(origString, decryptedText);
	}

	/**
	 * Encrypt-Decrypt File
	 */
	@Test
	public void encryptDecryptFileTest()
	{
		try
		{
			IApplicationLogger logger = WorkAppLogger.getInstance(null);
			File origFile = new File("src/main/resources/testchecksumfile");
			File encryptedFile = new File("src/main/resources/testchecksumfile.encrypted");
			File decryptedFile = new File("src/main/resources/testchecksumfile.decrypted");
			SecretKey secretKey = WorkAppUtility.generateAESRandomKey(logger);
			WorkAppUtility.encryptFile(logger, null, secretKey, origFile, encryptedFile);
			WorkAppUtility.decryptFile(logger, null, secretKey, encryptedFile, decryptedFile);
			assertTrue(true);
		}
		catch (CryptoException ex)
		{
			assertTrue(false);
		}

	}

}
