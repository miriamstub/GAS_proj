package Model;


import global.Manager;

import java.util.*;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class Event {

	private Date date;
	private Date time;
	private Window window;
	private Actual actual;
	private String adName;
	private String stat;
	private EventType eventType;
	private UUID ID;
	


	public Event(Date date, Date time, Date start,Date duration, int brk, int pos, Date length,
			/*String actualTime, String actualLength,String actualPos, String stat,*/
			String adName, EventType eventType) {
		
		setDate(date);
		setTime(time);
		this.window = new Window(start, duration, brk, pos, length);
		setAdName(adName);
		setEventType(eventType);
		this.ID = Manager.getUUID();
	}


	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date =date;
	}

	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public EventType getEventType() {
		return eventType;
	}


	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public UUID getID() {
		return ID;
	}


	@Override
	public String toString() {
		return "Event [date=" + date + ", time=" + time + ", window=" + window
				+ ", actual=" + actual + ", adName=" + adName + ", stat="
				+ stat + ", eventType=" + eventType + ", ID=" + ID + "]";
	}
}