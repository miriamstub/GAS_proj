package Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * SchedulerInfo.java abstract class
 * Contains basic scheduler's properties.
 * @property {String} schInfoName - scheduler's name.
 * @property {Map<UUID, Event>} eventMap - map of all events belong to the scheduler.
 * @property {Set<String>} eventKeys - set of all events' unique keys. unique key is a string that composed from combination of: window's brk, window's pos, window's duration, window's start, event time & event type.
 * @property {Map<String, ProgramAvail>} availMap - an instrumental variable to calculate left duration, contains all programs' avails in the scheduler.
 * @property {boolean[]} overlappedMins - an instrumental variable to avoid overlapping.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public abstract class SchedulerInfo {
	
	private String schInfoName;
	private Map<UUID, Event> eventMap;
	private Set<String> eventKeys;
	private Map<String, ProgramAvail> availMap;
	private boolean[] overlappedMins = new boolean[24 * 60];
	
	public SchedulerInfo(String schInfoName) {
		super();
		this.schInfoName = schInfoName;
		this.eventMap = new HashMap<UUID, Event>();
		this.eventKeys = new HashSet<String>();
		this.availMap = new HashMap<String, ProgramAvail>();
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

	public boolean[] getOverlappedMins() {
		return overlappedMins;
	}

	public void setOverlappedMins(boolean[] overlappedMins) {
		this.overlappedMins = overlappedMins;
	}
}
