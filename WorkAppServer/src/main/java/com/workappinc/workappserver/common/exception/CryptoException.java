package com.workappinc.workappserver.common.exception;

/**
 * CryptoException covers all Encryption-Decryption Exceptions
 * 
 * @author dhgovindaraj
 *
 */
public class CryptoException extends Exception
{

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 5294311708597342710L;

	public CryptoException()
	{
	}

	public CryptoException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
