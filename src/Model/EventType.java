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

	SCHEDULED("Sch.","SCHED"),
	FILL("Fill","FILL");
	//	BONUS("BONUS")


	private final String CCMSvalue;
	private final String SCTE118value;


	EventType(String CCMSvaluep, String SCTE118valuep) {
		this.CCMSvalue = CCMSvaluep;
		this.SCTE118value = SCTE118valuep;
	}

	public String getCCMsValue() {
		return CCMSvalue;
	}

	public String getSCTE118value() {
		return SCTE118value;
	}

	public static EventType getNameByValue(String value) {
		for(EventType e : EventType.values()){
			if(value.equals(e.CCMSvalue) || value.equals(e.SCTE118value)) return e;
		}
		return null;
	}
}
