package Model;

/**
 * EventType.java Enum
 * Identifies the spot:
 * SCHEDULED - being scheduled contractually
 * FILL - used as filler in order to complete a commercial break. 
 * 
 * 
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
			if(value.equals(e.value)) return e.name();
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getValue();
	}



}
