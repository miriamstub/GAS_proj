package Model;


import java.util.Date;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class Avail {
	
	private Date startTime;
	private Date endTime;
	private Date leftDuration;
	
	
	public Avail(Date startTime, Date endTime, Date leftDuration) {
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