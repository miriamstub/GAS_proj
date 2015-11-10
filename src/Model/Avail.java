package Model;


import java.util.Date;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class Avail {
	
	private Date startTime;
	private Date endTime;
	private Long leftDuration;
	
	
	public Avail(Date startTime, Date endTime, Long leftDuration) {
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
	
	public Long getLeftDuration() {
		return leftDuration;
	}
	
	public void setLeftDuration(Long leftDuration) {
		this.leftDuration = leftDuration;
	}
	
	
	
	
	
	
	
}