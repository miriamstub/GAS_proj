package Deserializer;



/**
 * IDeserializer.java Interface
 * Declare basic deserializing functions.
 * Each schedulerInfoType implements the interface according to its certain deserializing process.
 * 
 **/


public interface IDeserializer {
	
	void run();
	boolean validAndConvertEventDataParams(String[] value);
	boolean validateData(String[] value);

}
