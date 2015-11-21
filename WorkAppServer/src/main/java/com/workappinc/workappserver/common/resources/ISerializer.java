package com.workappinc.workappserver.common.resources;

/**
 * ISerializer is a top-level interface from which all Serializer classes are
 * derived
 * 
 * @author dhgovindaraj
 *
 */
public interface ISerializer
{
	// TODO add Serialize and DeSerialize support
	public Object serialize(Object deserializedObject);

	public Object deSerialize(Object serializedObject);

}
