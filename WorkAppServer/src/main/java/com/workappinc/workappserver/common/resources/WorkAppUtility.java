package com.workappinc.workappserver.common.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppUtility is a class with static methods to serve as utility methods
 * across files
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtility
{
	private static final String COMMA = ",";
	private static IApplicationLogger mLogger = null;
	private static InetAddress ip = null;
	private static String hostname = null;
	private static WorkAppUtility mInstance = null;

	private WorkAppUtility(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @return
	 */
	public static WorkAppUtility getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppUtility.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppUtility(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			SingletonInitException singletonEx = new SingletonInitException(
					"Error during Singleton Object Creation for WorkAppUtility Class", ex);
			mLogger.LogException(singletonEx, WorkAppUtility.class);
			throw singletonEx;
		}
	}

	/**
	 * Get current Process Id of the running Application
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @return
	 */
	public synchronized static String getMyPid(Object ctx)
	{
		String pid = "-1";
		try
		{
			final String nameStr = ManagementFactory.getRuntimeMXBean().getName();

			// TODO: Basic parsing assuming that nameStr will be of the form
			// "pid@hostname", which is probably not guaranteed.
			pid = nameStr.split("@")[0];
		}
		catch (RuntimeException ex)
		{
			mLogger.LogException(ex, WorkAppUtility.class);
		}
		return pid;
	}

	/**
	 * Get the HostName or IPAddress of the current machine
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param isHostIP
	 *            which when set to true, returns IpAddress , else returns
	 *            HostName IPAddress
	 * @return
	 */
	public synchronized static String getMyHostInfo(Object ctx, boolean isHostIP)
	{
		String returnInfo = null;
		try
		{
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();

			if (isHostIP)
				returnInfo = ip.getHostAddress();
			else returnInfo = hostname;

		}
		catch (UnknownHostException ex)
		{
			mLogger.LogException(ex, WorkAppUtility.class);
		}
		return returnInfo;
	}

	// converttoPNG, Generate UUID,

	/**
	 * Generates a String representation of MD5 Hash value
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param id
	 * @return String MD5 Value
	 * @throws NoSuchAlgorithmException
	 */
	public synchronized static String generateMD5Hash(Object ctx, String id) throws NoSuchAlgorithmException
	{
		byte byteData[] = generateMD5HashBytes(ctx, id);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
		{
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * Generates a Byte array representation of MD5 Hash value
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param id
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 */
	public synchronized static byte[] generateMD5HashBytes(Object ctx, String id) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(id.getBytes());
		byte[] byteData = md.digest();
		return byteData;

	}

	/**
	 * Generates a Byte Array representation of Checksum of a file
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param filePath
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public synchronized static byte[] generateFileChecksum(Object ctx, String filePath)
			throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream("filePath");
		byte[] dataBytes = new byte[1024];
		int nread = 0;
		while ((nread = fis.read(dataBytes)) != -1)
		{
			md.update(dataBytes, 0, nread);
		}
		;
		byte[] mdbytes = md.digest();
		fis.close();
		return mdbytes;
	}

	/**
	 * Generates a String representation of Checksum of a file
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param filePath
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public synchronized static String generateFileChecksumString(Object ctx, String filePath)
			throws NoSuchAlgorithmException, IOException
	{
		byte[] mdbytes = generateFileChecksum(ctx, filePath);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++)
		{
			String hex = Integer.toHexString(0xff & mdbytes[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * Encode String using Base64 with Padding
	 * 
	 * @param incomingString
	 * @return String
	 */
	public synchronized static String encodeString(Object ctx, String incomingString)
	{
		String encodedString = Base64.getEncoder().encodeToString(incomingString.getBytes());
		return encodedString;
	}

	/**
	 * Decode String with Base64 Decoder
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param incomingString
	 * @return String
	 */
	public synchronized static String decodeString(Object ctx, String incomingString)
	{
		byte[] decodedByteArray = Base64.getDecoder().decode(incomingString);
		return decodedByteArray.toString();
	}

	/**
	 * Generates a UUID
	 * 
	 * @return String
	 */
	public synchronized static String generateUUID(Object ctx)
	{
		return UUID.randomUUID().toString();
	}

	/**
	 * Encrypt String using DES
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param inputString
	 *            - String to be encrypted
	 * @param keyString
	 *            - Send to the Receiver
	 * @param ivString
	 *            - Send to the Receiver
	 * @return String - Encrypted string
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws ShortBufferException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public synchronized static String encryptMessage(Object ctx, String inputString, String keyString, String ivString)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] input = inputString.getBytes();
		byte[] keyBytes = keyString.getBytes();
		byte[] ivBytes = ivString.getBytes();

		// wrap key data in Key/IV specs to pass to cipher
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		// create the cipher with the algorithm you choose
		// see javadoc for Cipher class for more info, e.g.
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		byte[] encrypted = new byte[cipher.getOutputSize(input.length)];
		int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
		enc_len += cipher.doFinal(encrypted, enc_len);

		// You can add the message to ctx as required
		String msg = "Encoded-Message-Info: [" + inputString + COMMA + encrypted.toString() + COMMA + enc_len + COMMA
				+ keyString + COMMA + ivString + "]";
		mLogger.LogInfo(msg, WorkAppUtility.class);

		return encrypted.toString();
	}

	/**
	 * Decrypt String using DES
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param inputString
	 *            - Received Encrypted Message
	 * @param keyString
	 *            - Given by the Sender
	 * @param ivString
	 *            - Given by the Sender
	 * @param encLen
	 *            - Given by the Sender
	 * @return String
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws ShortBufferException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public synchronized static String decryptMessage(Object ctx, String inputString, String keyString, String ivString,
			int encLen) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
					NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] encrypted = inputString.getBytes();
		byte[] keyBytes = keyString.getBytes();
		byte[] ivBytes = ivString.getBytes();
		int enc_len = encLen;

		// wrap key data in Key/IV specs to pass to cipher
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
		int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
		dec_len += cipher.doFinal(decrypted, dec_len);

		String msg = "Decoded-Message-Info: [" + inputString + COMMA + enc_len + COMMA + decrypted.toString() + COMMA
				+ dec_len + COMMA + keyString + COMMA + ivString + "]";
		mLogger.LogInfo(msg, WorkAppUtility.class);

		return decrypted.toString();

	}

}
