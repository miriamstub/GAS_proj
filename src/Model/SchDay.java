package Model;
import java.util.*;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class SchDay extends SchedulerInfo{

	private Date date;//PROBLEM!! 'couse the month is a letter:(
	private String zone;
	private String channel;
	
	
	public SchDay(String schInfoName, SchedulerInfoType schInfoType,
			Map<UUID, Event> eventMap, Map<String, Avail> availMap,
			Date date, String zone, String channel) {
		super(schInfoName, schInfoType, eventMap, availMap);
		this.date = date;
		this.zone = zone;
		this.channel = channel;
	}
	
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
			this.date = date;
	}
	
	
}