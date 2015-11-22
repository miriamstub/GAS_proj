package Model;

import java.util.Date;

/**
 * Window.java class
 * Opportunity window for the event.
 * @property {Date} start -  window's start time - when it'll be active.
 * @property {Date} dur - duration in minutes that the window is active.
 * @property {int} brk - break sequence number within window to occur. 
 * @property {int} pos - position sequence number for event within break.
 * @property {Date} length - scheduled event length - duration of the ad.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class Window {
	
	
	private Date start;
	private Date duration;
	private int brk;
	private int pos;
	private Date length;	
	
	
	public Window(Date start, Date duration, int brk, int pos, Date length){
		
		setStart(start);
		setDuration(duration);
		setBrk(brk);
		setPos(pos);
		setLength(length);
		
	}
	
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
			this.start = start;
	}
	
	public Date getDuration() {
		return duration;
	}
	
	public void setDuration(Date duration) {
			this.duration = duration;
	}
	
	public int getBrk() {
		return brk;
	}
	
	public void setBrk(int brk) {
			this.brk = brk;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
			this.pos = pos;
	}
	
	public Date getLength() {
		return length;
	}
	
	public void setLength(Date length) {
			this.length = length;
	}
	
	
	@Override
	public String toString() {
		return "Window [start=" + start + ", duration=" + duration + ", brk="
				+ brk + ", pos=" + pos + ", length=" + length + "]";
	}
	
	
}