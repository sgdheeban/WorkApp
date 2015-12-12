package com.workappinc.workappserver.common.resources.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.workappinc.workappserver.common.exception.MD5HashingException;
import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.interfaces.IUtil;

/**
 * WorkAppUtility is a class with static methods to serve as utility methods
 * across files
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtil implements IUtil
{
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
	private static final String COMMA = ",";
	private static InetAddress ip = null;
	private static String hostname = null;

	/**
	 * Get current Process Id of the running Application
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @return
	 * @throws SystemException
	 */
	public synchronized static String getMyPid(IApplicationLogger logger, Object ctx) throws SystemException
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
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new SystemException("Get ProcessID method threw RunTimeException", ex);
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
	 * @throws SystemException
	 */
	public synchronized static String getMyHostInfo(IApplicationLogger logger, Object ctx, boolean isHostIP)
			throws SystemException
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
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new SystemException("Get HostInfo method threw UnknownHostException", ex);
		}
		return returnInfo;
	}

	/**
	 * Generates a String representation of MD5 Hash value
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param id
	 * @return String MD5 Value
	 * @throws MD5HashingException
	 */
	public synchronized static String generateMD5HashString(IApplicationLogger logger, Object ctx, String id)
			throws MD5HashingException
	{
		byte byteData[] = generateMD5HashBytes(logger, ctx, id);
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
	 * @throws MD5HashingException
	 */
	public synchronized static byte[] generateMD5HashBytes(IApplicationLogger logger, Object ctx, String id)
			throws MD5HashingException
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id.getBytes());
			byte[] byteData = md.digest();
			return byteData;
		}
		catch (NoSuchAlgorithmException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new MD5HashingException("Get MD5HashString method threw NoSuchAlgorithmException", ex);
		}

	}

	/**
	 * Generates a Byte Array representation of Checksum of a file
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param filePath
	 * @return byte[]
	 * @throws MD5HashingException
	 * @throws SystemException
	 */
	public synchronized static byte[] generateFileChecksumBytes(IApplicationLogger logger, Object ctx, String filePath)
			throws MD5HashingException, SystemException
	{
		try
		{
			FileInputStream fis = null;
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				fis = new FileInputStream(filePath);
				byte[] dataBytes = new byte[1024];
				int nread = 0;
				while ((nread = fis.read(dataBytes)) != -1)
				{
					md.update(dataBytes, 0, nread);
				}
				;
				byte[] mdbytes = md.digest();
				return mdbytes;
			}
			catch (NoSuchAlgorithmException ex)
			{
				if (logger != null)
					logger.LogException(ex, WorkAppUtil.class);
				throw new MD5HashingException("GenerateFileChecksumBytes method threw NoSuchAlgorithmException", ex);
			}
			finally
			{
				if (fis != null)
					fis.close();
			}
		}
		catch (IOException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new SystemException("GenerateFileChecksumBytes method threw RunTimeException", ex);
		}
	}

	/**
	 * Generates a String representation of Checksum of a file
	 * 
	 * @param ctx
	 *            - Nullable object intended for passing context for logging
	 * @param filePath
	 * @return String
	 * @throws SystemException
	 * @throws MD5HashingException
	 */
	public synchronized static String generateFileChecksumString(IApplicationLogger logger, Object ctx, String filePath)
			throws MD5HashingException, SystemException
	{
		byte[] mdbytes = generateFileChecksumBytes(logger, ctx, filePath);
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
	public synchronized static String encodeString(IApplicationLogger logger, Object ctx, String incomingString)
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
	public synchronized static String encodeBytes(IApplicationLogger logger, Object ctx, byte[] incomingBytes)
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
	public synchronized static String decodeString(IApplicationLogger logger, Object ctx, String incomingString)
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
	public synchronized static byte[] decodedBytes(IApplicationLogger logger, Object ctx, String incomingString)
	{
		byte[] decodedByteArray = Base64.getDecoder().decode(incomingString.trim());
		return decodedByteArray;
	}

	/**
	 * Generates a UUID
	 * 
	 * @return String
	 */
	public synchronized static String generateUUID(IApplicationLogger logger, Object ctx)
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
	 * @throws CryptoException
	 */
	public static String encryptString(IApplicationLogger logger, Object ctx, String originalString,
			SecretKey secretKey) throws CryptoException
	{
		try
		{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			byte[] plainTextByte = originalString.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			String encryptedText = encodeBytes(logger, null, encryptedByte);
			return encryptedText;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new CryptoException("encryptString throws Error encrypting string", ex);
		}

	}

	/**
	 * Decrypt encrypted string back to the original
	 * 
	 * @param ctx
	 * @param encryptedString
	 * @param secretKey
	 * @return
	 * @throws CryptoException
	 */
	public static String decryptString(IApplicationLogger logger, Object ctx, String encryptedString,
			SecretKey secretKey) throws CryptoException
	{
		try
		{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			byte[] encryptedTextByte = decodedBytes(logger, null, encryptedString);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			String decryptedText = new String(decryptedByte);
			return decryptedText;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new CryptoException("decryptString throws Error decrypting String", ex);
		}
	}

	/**
	 * Generates AES Random Key
	 * 
	 * @return
	 * @throws CryptoException
	 */
	public static SecretKey generateAESRandomKey(IApplicationLogger logger) throws CryptoException
	{
		try
		{
			KeyGenerator keyGenerator = KeyGenerator.getInstance(TRANSFORMATION);
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey;
		}
		catch (NoSuchAlgorithmException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppUtil.class);
			throw new CryptoException("generateAESRandomKey throws error while generating AES Random Key", ex);
		}

	}

	/**
	 * Encrypt File using AES algorithm
	 * 
	 * @param ctx
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @throws CryptoException
	 */
	public static void encryptFile(IApplicationLogger logger, Object ctx, SecretKey secretKey, File inputFile,
			File outputFile) throws CryptoException
	{
		doCryptoFile(logger, ctx, Cipher.ENCRYPT_MODE, secretKey, inputFile, outputFile);
	}

	/**
	 * Decrypt File using AES algorithm
	 * 
	 * @param ctx
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @throws CryptoException
	 */
	public static void decryptFile(IApplicationLogger logger, Object ctx, SecretKey secretKey, File inputFile,
			File outputFile) throws CryptoException
	{
		doCryptoFile(logger, ctx, Cipher.DECRYPT_MODE, secretKey, inputFile, outputFile);
	}

	private static void doCryptoFile(IApplicationLogger logger, Object ctx, int cipherMode, SecretKey secretKey,
			File inputFile, File outputFile) throws CryptoException
	{
		try
		{
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
			throw new CryptoException("doCryptoFile throws Error encrypting/decrypting file", ex);
		}
	}

}
