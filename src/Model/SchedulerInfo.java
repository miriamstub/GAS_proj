package Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public abstract class SchedulerInfo implements Comparable<SchedulerInfo> {
	
	private String schInfoName;
	private SchedulerInfoType schInfoType;
	private Map<UUID, Event> eventMap;
	private Set<String> eventKeys;
	private Map<String, ProgramAvail> availMap;
	private boolean[] overlappedMins = new boolean[24 * 60];

	/**
	 * @param schInfoName
	 * @param schInfoType
	 */
	public SchedulerInfo(String schInfoName, SchedulerInfoType schInfoType) {
		super();
		this.schInfoName = schInfoName;
		this.schInfoType = schInfoType;
		this.eventMap = new LinkedHashMap<UUID, Event>();
		this.eventKeys = new HashSet<String>();
		this.availMap = new LinkedHashMap<String, ProgramAvail>();
		Arrays.fill(this.overlappedMins, false);
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
	
	public Set<String> getEventKeys() {
		return eventKeys;
	}

	public void setEventKeys(Set<String> eventKeys) {
		this.eventKeys = eventKeys;
	}
	
	public Map<String, ProgramAvail> getAvailMap() {
		return availMap;
	}
	public void setAvailMap(Map<String, ProgramAvail> availMap) {
		this.availMap = availMap;
	}

	public SchedulerInfoType getSchInfoType() {
		return schInfoType;
	}
	
	public void setSchInfoType(SchedulerInfoType schInfoType) {
		this.schInfoType = schInfoType;
	}

	public boolean[] getOverlappedMins() {
		return overlappedMins;
	}

	public void setOverlappedMins(boolean[] overlappedMins) {
		this.overlappedMins = overlappedMins;
	}
	
	public int compareTo(SchedulerInfo schedulerInfo) {
		if (!this.schInfoName.equals(schedulerInfo.schInfoName))
			return -1;
		return 0;
	} 
}
