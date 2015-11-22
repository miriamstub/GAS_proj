package Model;


import java.util.Date;

/**
 * ProgramAvail.java class
 * An instrumental class to calculate left duration.
 * @property {Date} startTime - start time of program avail.
 * @property {Date} endTime - end time of program avail.
 * @property {Date} leftDuration - the duration that remains for more advertising in this program avail.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class ProgramAvail{
	
	private Date startTime;
	private Date endTime;
	private Date leftDuration;
	
	
	public ProgramAvail(Date startTime, Date endTime, Date leftDuration) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.leftDuration = leftDuration;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getLeftDuration() {
		return leftDuration;
	}
	
	public void setLeftDuration(Date leftDuration) {
		this.leftDuration = leftDuration;
	}
	
}