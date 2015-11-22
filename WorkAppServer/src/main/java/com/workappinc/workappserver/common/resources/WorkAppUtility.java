package com.workappinc.workappserver.common.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.workappinc.workappserver.common.exception.CryptoException;
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
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
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
	public synchronized static String generateMD5HashString(Object ctx, String id) throws NoSuchAlgorithmException
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
	public synchronized static byte[] generateFileChecksumBytes(Object ctx, String filePath)
			throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(filePath);
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
		byte[] mdbytes = generateFileChecksumBytes(ctx, filePath);
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
		String encodedString = Base64.getEncoder().encodeToString(incomingString.trim().getBytes());
		return encodedString;
	}

	/**
	 * Encode Bytes using Base64 with Padding
	 * 
	 * @param incomingBytes
	 * @return String
	 */
	public synchronized static String encodeBytes(Object ctx, byte[] incomingBytes)
	{
		String encodedString = Base64.getEncoder().encodeToString(incomingBytes);
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
		byte[] decodedByteArray = Base64.getDecoder().decode(incomingString.trim());
		return new String(decodedByteArray);
	}

	/**
	 * Decoded byte array with Base64 Decoder - without converting to final
	 * string
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param incomingString
	 * @return String
	 */
	public synchronized static byte[] decodedBytes(Object ctx, String incomingString)
	{
		byte[] decodedByteArray = Base64.getDecoder().decode(incomingString.trim());
		return decodedByteArray;
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
	 * Encrypts a given string
	 * 
	 * @param ctx
	 * @param originalString
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encryptString(Object ctx, String originalString, SecretKey secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("AES");
		byte[] plainTextByte = originalString.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		String encryptedText = encodeBytes(null, encryptedByte);
		return encryptedText;
	}

	/**
	 * Decrypt encrypted string back to the original
	 * 
	 * @param ctx
	 * @param encryptedString
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptString(Object ctx, String encryptedString, SecretKey secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("AES");
		byte[] encryptedTextByte = decodedBytes(null, encryptedString);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	/**
	 * Generates AES Random Key
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey generateAESRandomKey() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}

	/**
	 * Encrypt File
	 * @param ctx
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @throws CryptoException
	 */
	public static void encryptFile(Object ctx, String key, File inputFile, File outputFile) throws CryptoException
	{
		doCryptoFile(ctx, Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	/**
	 * Decrypt File
	 * @param ctx
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @throws CryptoException
	 */
	public static void decryptFile(Object ctx, String key, File inputFile, File outputFile) throws CryptoException
	{
		doCryptoFile(ctx, Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}

	private static void doCryptoFile(Object ctx, int cipherMode, String key, File inputFile, File outputFile) throws CryptoException
	{
		try
		{
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		}
		catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException ex)
		{
			throw new CryptoException("Error encrypting/decrypting file", ex);
		}
	}

}
