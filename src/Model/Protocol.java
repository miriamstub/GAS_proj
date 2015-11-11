package Model;

import java.util.Map;
import java.util.UUID;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public abstract class Protocol {
	
	private String protocolName;
	private ProtocolType protocolType;
	private Map<UUID, Event> eventMap;
	private Map<String, Avail> availMap;
	
	
	public Protocol(String fileName, ProtocolType protocolType,
			Map<UUID, Event> eventMap, Map<String, Avail> availMap) {
		this.protocolName = fileName;
		this.protocolType = protocolType;
		this.eventMap = eventMap;
		this.availMap = availMap;
	}
	
	public String getFileName() {
		return protocolName;
	}
	public void setFileName(String fileName) {
		this.protocolName = fileName;
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
	public ProtocolType getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}
	
}
