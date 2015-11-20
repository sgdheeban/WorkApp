package com.workappinc.workappserver.common.resources;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppUtility is a class with static methods to serve as utility methods across files
 * @author dhgovindaraj
 *
 */
public class WorkAppUtility
{
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
	 * @return
	 */
	public synchronized static String getMyPid()
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
	 * @param isHostIP
	 *            which when set to true, returns IpAddress , else returns HostName
	 *            IPAddress
	 * @return
	 */
	public synchronized static String getMyHostInfo(boolean isHostIP)
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
	
	// getHash, Encrypt user, Decrypt user, converttoPNG, Generate UUID, checksum

	String generateMD5Hash(String id) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(id.getBytes());
        byte byteData[] = md.digest();
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
	}
	
	byte[] generateMD5HashBytes(String id) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(id.getBytes());
        byte[] byteData = md.digest();
        return byteData;
       
	}
	
	

	
}
