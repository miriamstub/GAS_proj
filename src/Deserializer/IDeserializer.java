package Deserializer;

public interface IDeserializer {
	
	void run(String value);
	boolean validAndConvertEventDataParams(String[] value);
	boolean validateData(String[] value);

}
