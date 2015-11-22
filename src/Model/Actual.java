package Model;

import java.util.Date;

/**
 * Actual.java class
 * Related to the verification side, whether or not the ad  plays as a reference to Billing for aired ads.
 * @property {Date} actualTime - actual aired time.
 * @property {Date} actualLength - actual aired length.
 * @property {int} actualPos - actual aired position within break.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class Actual {

	private Date actualTime;
	private Date actualLength;
	private int actualPos;
	

	public Actual(Date actualTime, Date actualLength, int actualPos) {
		super();
		setActualTime(actualTime);
		setActualLength(actualLength);
		setActualPos(actualPos);
	}

	public Date getActualTime() {
		return actualTime;
	}



	public void setActualTime(Date actualTime) {
			this.actualTime = actualTime;
	}



	public Date getActualLength() {
		return actualLength;
	}



	public void setActualLength(Date actualLength) {
			this.actualLength =  actualLength;
	}



	public int getActualPos() {
		return actualPos;
	}


	public void setActualPos(int actualPos) {
		this.actualPos = actualPos;
	}


	@Override
	public String toString() {
		return "Actual [actualTime=" + actualTime + ", actualLength="
				+ actualLength + ", actualPos=" + actualPos + "]";
	}

}