package Deserializer;



/**
 * IDeserializer.java Interface
 * Declare basic deserializing functions.
 * Each schedulerInfoType implements the interface according to its certain deserializing process.
 * 
 **/


public interface IDeserializer {
	
	void run(String value);
	boolean validAndConvertEventDataParams(String[] value);
	boolean validateData(String[] value);

}
