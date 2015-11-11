package Model;

import java.util.Map;
import java.util.UUID;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public abstract class SchedulerInfo {
	
	private String schInfoName;
	private SchedulerInfoType schInfoType;
	private Map<UUID, Event> eventMap;
	private Map<String, Avail> availMap;
	
	
	/**
	 * @param schInfoName
	 * @param schInfoType
	 * @param eventMap
	 * @param availMap
	 */
	public SchedulerInfo(String schInfoName, SchedulerInfoType schInfoType,
			Map<UUID, Event> eventMap, Map<String, Avail> availMap) {
		super();
		this.schInfoName = schInfoName;
		this.schInfoType = schInfoType;
		this.eventMap = eventMap;
		this.availMap = availMap;
	}

	public String getSchInfoName() {
		return schInfoName;
	}
	
	public void setSchInfoName(String schInfoName) {
		this.schInfoName = schInfoName;
	}
	public Map<UUID, Event> getEventMap() {
		return eventMap;
	}
	public void setEventMap(Map<UUID, Event> eventMap) {
		this.eventMap = eventMap;
	}
	public Map<String, Avail> getAvailMap() {
		return availMap;
	}
	public void setAvailMap(Map<String, Avail> availMap) {
		this.availMap = availMap;
	}

	public SchedulerInfoType getSchInfoType() {
		return schInfoType;
	}
	
	public void setSchInfoType(SchedulerInfoType schInfoType) {
		this.schInfoType = schInfoType;
	}

	
}
