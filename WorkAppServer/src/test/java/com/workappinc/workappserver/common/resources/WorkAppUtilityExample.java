package com.workappinc.workappserver.common.resources;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;

import com.workappinc.workappserver.common.exception.CryptoException;
import com.workappinc.workappserver.common.exception.MD5HashingException;
import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppUtility;

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
	 * 
	 * @throws SystemException
	 */
	private void getPIDTest(IApplicationLogger logger) throws SystemException
	{
		String pid = WorkAppUtility.getMyPid(logger, null);
		System.out.println("My PID: " + pid);
	}

	/**
	 * Test Get Host Info
	 * 
	 * @throws SystemException
	 */
	private void getHostInfoTest(IApplicationLogger logger) throws SystemException
	{
		String hostname = WorkAppUtility.getMyHostInfo(logger, null, false);
		String hostIP = WorkAppUtility.getMyHostInfo(logger, null, true);
		System.out.println("My Hostname: " + hostname);
		System.out.println("My Hostname: " + hostIP);
	}

	/**
	 * Test Generate MD5 Hash
	 * 
	 * @throws MD5HashingException
	 */
	private void generateMD5HashTest(IApplicationLogger logger) throws MD5HashingException
	{
		String MD5HashString = WorkAppUtility.generateMD5HashString(logger, null, "sgd");
		System.out.println("MD5HashString: " + MD5HashString);

	}

	/**
	 * Test Generate MD5 Bytes Hash
	 * 
	 * @throws MD5HashingException
	 */
	private void generateMD5HashBytesTest(IApplicationLogger logger) throws MD5HashingException
	{
		byte[] MD5HashString = WorkAppUtility.generateMD5HashBytes(logger, null, "sgd");
		System.out.println("MD5HashBytes: " + MD5HashString);
	}

	/**
	 * Test Generate File CheckSum
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	private void generateFileChecksumTest(IApplicationLogger logger) throws MD5HashingException, SystemException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		String fileChecksumString = WorkAppUtility.generateFileChecksumString(logger, null, absolutePath);
		System.out.println("fileChecksumString: " + fileChecksumString);
	}

	/**
	 * Test Generate File Checksum String
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	private void generateFileChecksumStringTest(IApplicationLogger logger) throws MD5HashingException, SystemException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		byte[] fileChecksumBytes = WorkAppUtility.generateFileChecksumBytes(logger, null, absolutePath);
		System.out.println("fileChecksumBytes: " + fileChecksumBytes);
	}

	/**
	 * Test Encoding String
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private void encodeDecodeStringTest(IApplicationLogger logger)
	{
		String sourceStr = "test-str22";
		String encodedString = WorkAppUtility.encodeString(logger, null, sourceStr);
		String decodedString = WorkAppUtility.decodeString(logger, null, encodedString);
		System.out.println(sourceStr + " encoded to :" + encodedString);
		System.out.println(encodedString + " decoded to :" + decodedString);
	}

	/**
	 * Test Generating UUID
	 */
	private void generateUUIDTest(IApplicationLogger logger)
	{
		String uuid = WorkAppUtility.generateUUID(logger, null);
		System.out.println("uuid: " + uuid);
	}

	/**
	 * Test Encrypting String
	 * 
	 * @throws CryptoException
	 */
	private void encryptDecryptMessageTest(IApplicationLogger logger) throws CryptoException
	{
		String origString = "this is a test string\tabc";
		SecretKey secretKey = WorkAppUtility.generateAESRandomKey(logger);
		String encryptedText = WorkAppUtility.encryptString(logger, null, origString, secretKey);
		String decryptedText = WorkAppUtility.decryptString(logger, null, encryptedText, secretKey);
		System.out.println(origString + " encoded to :" + encryptedText);
		System.out.println(encryptedText + " decoded to :" + decryptedText);
	}

	/**
	 * Encrypt-Decrypt File
	 * 
	 * @throws CryptoException
	 */
	private void encryptDecryptFileTest(IApplicationLogger logger) throws CryptoException
	{
		File origFile = new File("src/main/resources/testchecksumfile");
		File encryptedFile = new File("src/main/resources/testchecksumfile.encrypted");
		File decryptedFile = new File("src/main/resources/testchecksumfile.decrypted");
		SecretKey secretKey = WorkAppUtility.generateAESRandomKey(logger);
		WorkAppUtility.encryptFile(logger, null, secretKey, origFile, encryptedFile);
		WorkAppUtility.decryptFile(logger, null, secretKey, encryptedFile, decryptedFile);
		System.out.println("File successfully encrypted and decrepreted.");
	}

	/**
	 * Main Method
	 * 
	 * @param args
	 * @throws SystemException
	 * @throws MD5HashingException
	 * @throws CryptoException
	 */
	public static void main(String args[]) throws SystemException, MD5HashingException, CryptoException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		WorkAppUtilityExample example = new WorkAppUtilityExample();
		example.getPIDTest(logger);
		example.getHostInfoTest(logger);
		example.generateMD5HashTest(logger);
		example.generateMD5HashBytesTest(logger);
		example.generateFileChecksumTest(logger);
		example.generateFileChecksumStringTest(logger);
		example.generateUUIDTest(logger);
		example.encodeDecodeStringTest(logger);
		example.encryptDecryptMessageTest(logger);
		example.encryptDecryptFileTest(logger);

	}
}
