package com.workapp.workappserver.common.resources.implementation;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workapp.workappserver.common.exception.JSONSerializationException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.resources.interfaces.ISerializer;

/**
 * JSONSerializer is an implementation of ISerializer interface for serializing
 * and de-serializing JSON and POJO
 * 
 * @author dhgovindaraj
 *
 */
public class JSONSerializer implements ISerializer
{
	/**
	 * Serializes POJO to JSON String
	 * 
	 * @param pojo
	 *            Object to be serialized to JSON
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static String serialize(Object pojo, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try
		{
			jsonInString = mapper.writeValueAsString(pojo);
			logger.LogDebug(jsonInString, JSONSerializer.class);
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
		return jsonInString;
	}

	/**
	 * Serializes POJO to JSON String in Pretty Print
	 * 
	 * @param pojo
	 *            Object to be serialized to JSON
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static String serializeInPrettyPrint(Object pojo, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try
		{
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
			logger.LogDebug(jsonInString, JSONSerializer.class);
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
		return jsonInString;
	}

	/**
	 * Serializes POJO to JSON File
	 * 
	 * @param pojo
	 *            Object to be serialized from JSON
	 * @param jsonFilePath
	 *            JSON String Filepath
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static boolean serializeToFile(Object pojo, String jsonFilePath, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			mapper.writeValue(new File(jsonFilePath), pojo);
			return true;
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
	}

	/**
	 * Serializes POJO to JSON File
	 * 
	 * @param pojo
	 *            Object to be serialized from JSON
	 * @param jsonFilePath
	 *            JSON String Filepath
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static boolean serializeToFileInPrettyPrint(Object pojo, String jsonFilePath, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), pojo);
			return true;
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
	}

	/**
	 * Deserializes JSON String to POJO, , given a class type
	 * 
	 * @param pojo
	 *            Object to be serialized from JSON
	 * @param json
	 *            JSON String filepath
	 * @param classname
	 *            Target Classname
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static Object deSerialize(Object pojo, String json, Class<?> classname, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			pojo = mapper.readValue(json, classname);
			String pojoString = mapper.writeValueAsString(pojo);
			logger.LogDebug(pojoString, JSONSerializer.class);
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
		return pojo;
	}

	/**
	 * Deserializes JSON File to POJO, given a class type
	 * 
	 * @param pojo
	 *            Object to be serialized from JSON
	 * @param jsonFilePath
	 *            JSON String filepath
	 * @param classname
	 *            Target Classname
	 * @param logger
	 * @return
	 * @throws JSONSerializationException
	 */
	public synchronized static Object deSerializeFromFile(Object pojo, String jsonFilePath, Class<?> classname, IApplicationLogger logger) throws JSONSerializationException
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			pojo = mapper.readValue(new File(jsonFilePath), classname);
			String pojoString = mapper.writeValueAsString(pojo);
			logger.LogDebug(pojoString, JSONSerializer.class);
		}
		catch (JsonGenerationException | JsonMappingException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}

		catch (IOException ex)
		{
			logger.LogException(ex, JSONSerializer.class);
			throw new JSONSerializationException("JSON Generation   or Mapping Exception", ex);
		}
		return pojo;
	}

}
