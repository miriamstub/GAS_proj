package Deserializer;

public interface IDeserializer {
	
	void run();
	boolean validAndConvertEventDataParams(String[] value);
	boolean validateData(String[] value);

}
