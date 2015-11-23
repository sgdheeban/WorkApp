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
	private void getPIDTest() throws SystemException
	{
		String pid = WorkAppUtility.getMyPid(null);
		System.out.println("My PID: " + pid);
	}

	/**
	 * Test Get Host Info
	 * 
	 * @throws SystemException
	 */
	private void getHostInfoTest() throws SystemException
	{
		String hostname = WorkAppUtility.getMyHostInfo(null, false);
		String hostIP = WorkAppUtility.getMyHostInfo(null, true);
		System.out.println("My Hostname: " + hostname);
		System.out.println("My Hostname: " + hostIP);
	}

	/**
	 * Test Generate MD5 Hash
	 * 
	 * @throws MD5HashingException
	 */
	private void generateMD5HashTest() throws MD5HashingException
	{
		String MD5HashString = WorkAppUtility.generateMD5HashString(null, "sgd");
		System.out.println("MD5HashString: " + MD5HashString);

	}

	/**
	 * Test Generate MD5 Bytes Hash
	 * 
	 * @throws MD5HashingException
	 */
	private void generateMD5HashBytesTest() throws MD5HashingException
	{
		byte[] MD5HashString = WorkAppUtility.generateMD5HashBytes(null, "sgd");
		System.out.println("MD5HashBytes: " + MD5HashString);
	}

	/**
	 * Test Generate File CheckSum
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	private void generateFileChecksumTest() throws MD5HashingException, SystemException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		String fileChecksumString = WorkAppUtility.generateFileChecksumString(null, absolutePath);
		System.out.println("fileChecksumString: " + fileChecksumString);
	}

	/**
	 * Test Generate File Checksum String
	 * 
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	private void generateFileChecksumStringTest() throws MD5HashingException, SystemException
	{
		File file = new File("src/main/resources/testchecksumfile");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);
		byte[] fileChecksumBytes = WorkAppUtility.generateFileChecksumBytes(null, absolutePath);
		System.out.println("fileChecksumBytes: " + fileChecksumBytes);
	}

	/**
	 * Test Encoding String
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private void encodeDecodeStringTest()
	{
		String sourceStr = "test-str22";
		String encodedString = WorkAppUtility.encodeString(null, sourceStr);
		String decodedString = WorkAppUtility.decodeString(null, encodedString);
		System.out.println(sourceStr + " encoded to :" + encodedString);
		System.out.println(encodedString + " decoded to :" + decodedString);
	}

	/**
	 * Test Generating UUID
	 */
	private void generateUUIDTest()
	{
		String uuid = WorkAppUtility.generateUUID(null);
		System.out.println("uuid: " + uuid);
	}

	/**
	 * Test Encrypting String
	 * 
	 * @throws CryptoException
	 */
	private void encryptDecryptMessageTest() throws CryptoException
	{
		String origString = "this is a test string\tabc";
		SecretKey secretKey = WorkAppUtility.generateAESRandomKey();
		String encryptedText = WorkAppUtility.encryptString(null, origString, secretKey);
		String decryptedText = WorkAppUtility.decryptString(null, encryptedText, secretKey);
		System.out.println(origString + " encoded to :" + encryptedText);
		System.out.println(encryptedText + " decoded to :" + decryptedText);
	}

	/**
	 * Encrypt-Decrypt File
	 * 
	 * @throws CryptoException
	 */
	private void encryptDecryptFileTest() throws CryptoException
	{
		File origFile = new File("src/main/resources/testchecksumfile");
		File encryptedFile = new File("src/main/resources/testchecksumfile.encrypted");
		File decryptedFile = new File("src/main/resources/testchecksumfile.decrypted");
		SecretKey secretKey = WorkAppUtility.generateAESRandomKey();
		WorkAppUtility.encryptFile(null, secretKey, origFile, encryptedFile);
		WorkAppUtility.decryptFile(null, secretKey, encryptedFile, decryptedFile);
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
		WorkAppUtilityExample example = new WorkAppUtilityExample();
		example.getPIDTest();
		example.getHostInfoTest();
		example.generateMD5HashTest();
		example.generateMD5HashBytesTest();
		example.generateFileChecksumTest();
		example.generateFileChecksumStringTest();
		example.generateUUIDTest();
		example.encodeDecodeStringTest();
		example.encryptDecryptMessageTest();
		example.encryptDecryptFileTest();

	}
}
