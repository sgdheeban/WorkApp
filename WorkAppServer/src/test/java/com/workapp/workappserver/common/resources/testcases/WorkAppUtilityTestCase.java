package com.workapp.workappserver.common.resources.testcases;

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

import com.workapp.workappserver.common.exception.CryptoException;
import com.workapp.workappserver.common.exception.MD5HashingException;
import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.WorkAppLogger;
import com.workapp.workappserver.common.resources.implementation.WorkAppUtil;

/**
 * WorkAppUtilitiesTestCases cover JUnit test cases to test all functionalities
 * of WorkAppUtilitiesExample functionalities
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtilityTestCase
{
	/**
	 * Testing get Process ID
	 * 
	 * @throws SystemException
	 */
	@Test
	public void getPIDTest()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String pid1 = WorkAppUtil.getMyPid(logger, null);
			String pid2 = WorkAppUtil.getMyPid(logger, null);
			assertEquals(pid1, pid2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Test Get Host Info
	 * 
	 * @throws SystemException
	 */
	@Test
	public void getHostInfoTest()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String hostname1 = WorkAppUtil.getMyHostInfo(logger, null, false);
			String hostname2 = WorkAppUtil.getMyHostInfo(logger, null, false);
			String hostIP1 = WorkAppUtil.getMyHostInfo(logger, null, true);
			String hostIP2 = WorkAppUtil.getMyHostInfo(logger, null, true);
			assertEquals(hostname1, hostname2);
			assertEquals(hostIP1, hostIP2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Test Generate MD5 Hash
	 * 
	 * @throws MD5HashingException
	 */
	@Test
	public void generateMD5HashTest() throws MD5HashingException
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String MD5HashString1 = WorkAppUtil.generateMD5HashString(logger, null, "sgd");
			String MD5HashString2 = WorkAppUtil.generateMD5HashString(logger, null, "sgd");
			assertEquals(MD5HashString1, MD5HashString2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Generate MD5 Bytes Hash
	 * 
	 * @throws MD5HashingException
	 */
	@Test
	public void generateMD5HashBytesTest() throws MD5HashingException
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			byte[] MD5HashBytes1 = WorkAppUtil.generateMD5HashBytes(logger, null, "sgd");
			byte[] MD5HashBytes2 = WorkAppUtil.generateMD5HashBytes(logger, null, "sgd");
			assertArrayEquals(MD5HashBytes1, MD5HashBytes2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Generate File CheckSum
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	@Test
	public void generateFileChecksumTest() throws MD5HashingException
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			File file = new File("src/main/resources/testchecksumfile");
			String absolutePath = file.getAbsolutePath();
			String fileChecksumString1 = WorkAppUtil.generateFileChecksumString(logger, null, absolutePath);
			String fileChecksumString2 = WorkAppUtil.generateFileChecksumString(logger, null, absolutePath);
			assertEquals(fileChecksumString1, fileChecksumString2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Generate File Checksum String
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	@Test
	public void generateFileChecksumStringTest() throws MD5HashingException
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			File file = new File("src/main/resources/testchecksumfile");
			String absolutePath = file.getAbsolutePath();
			byte[] fileChecksumBytes1 = WorkAppUtil.generateFileChecksumBytes(logger, null, absolutePath);
			byte[] fileChecksumBytes2 = WorkAppUtil.generateFileChecksumBytes(logger, null, absolutePath);
			assertArrayEquals(fileChecksumBytes1, fileChecksumBytes2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Encoding String
	 */
	@Test
	public void encodeDecodeStringTest()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String sourceStr = "test-str22";
			String encodedString = WorkAppUtil.encodeString(logger, null, sourceStr);
			String decodedString = WorkAppUtil.decodeString(logger, null, encodedString);
			assertEquals(sourceStr, decodedString);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Generating UUID
	 */
	@Test
	public void generateUUIDTest()
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String uuid1 = WorkAppUtil.generateUUID(logger, null);
			String uuid2 = WorkAppUtil.generateUUID(logger, null);
			assertNotEquals(uuid1, uuid2);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test Encrypting String
	 * 
	 * @throws CryptoException
	 */
	@Test
	public void encryptDecryptMessageTest() throws CryptoException
	{
		IApplicationLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			String origString = "this is a test string\n";
			SecretKey secretKey = WorkAppUtil.generateAESRandomKey(logger);
			String encryptedText = WorkAppUtil.encryptString(logger, null, origString, secretKey);
			String decryptedText = WorkAppUtil.decryptString(logger, null, encryptedText, secretKey);
			assertEquals(origString, decryptedText);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Encrypt-Decrypt File
	 */
	@Test
	public void encryptDecryptFileTest()
	{
		try
		{
			IApplicationLogger logger = new WorkAppLogger(null);
			File origFile = new File("src/main/resources/testchecksumfile");
			File encryptedFile = new File("src/main/resources/testchecksumfile.encrypted");
			File decryptedFile = new File("src/main/resources/testchecksumfile.decrypted");
			SecretKey secretKey = WorkAppUtil.generateAESRandomKey(logger);
			WorkAppUtil.encryptFile(logger, null, secretKey, origFile, encryptedFile);
			WorkAppUtil.decryptFile(logger, null, secretKey, encryptedFile, decryptedFile);
			assertTrue(true);
		}
		catch (CryptoException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

}
