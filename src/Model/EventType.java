package Model;
/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public enum EventType {

	SCHEDULED("Sch."),
	FILL("Fill");

	private final String value;

	EventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static String getNameByValue(String value) {
		for(EventType e : EventType.values()){
			if(value == e.value) return e.name();
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getValue();
	}



}
