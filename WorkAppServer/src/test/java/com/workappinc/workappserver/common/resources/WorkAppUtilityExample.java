package com.workappinc.workappserver.common.resources;

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
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private void generateMD5HashTest() throws NoSuchAlgorithmException
	{
		String MD5HashString = WorkAppUtility.generateMD5HashString(null, "sgd");
		System.out.println("MD5HashString: " + MD5HashString);

	}

	/**
	 * Test Generate MD5 Bytes Hash
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private void generateMD5HashBytesTest() throws NoSuchAlgorithmException
	{
		byte[] MD5HashString = WorkAppUtility.generateMD5HashBytes(null, "sgd");
		System.out.println("MD5HashBytes: " + MD5HashString);
	}

	/**
	 * Test Generate File CheckSum
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private void generateFileChecksumTest() throws NoSuchAlgorithmException, IOException
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
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private void generateFileChecksumStringTest() throws NoSuchAlgorithmException, IOException
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
	private void encodeDecodeStringTest() throws NoSuchAlgorithmException, IOException
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
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws Exception
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws ShortBufferException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	private void encryptDecryptMessageTest() throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		String origString = "this is a test string\tabc";
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		String encryptedText = WorkAppUtility.encryptString(null, origString, secretKey);
		String decryptedText = WorkAppUtility.decryptString(null, encryptedText, secretKey);
		System.out.println(origString + " encoded to :" + encryptedText);
		System.out.println(encryptedText + " decoded to :" + decryptedText);
	}

	public static void main(String args[]) throws NoSuchAlgorithmException, IOException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
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

	}
}
