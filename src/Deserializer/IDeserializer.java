package Deserializer;

public interface IDeserializer {
	
	CCMSDeserializer getInstance();
	void run();
	boolean validAndConvertEventDataParams(String[] value);
	boolean validateData(String[] value);

}
