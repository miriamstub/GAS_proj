package Model;

import java.util.Date;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class Window {
	
	
	private Date start;
	private Date duration;
	private int brk;
	private int pos;
	private Date length;
	
	
	private static final int MIN_BRK = 1;
	private static final int MAX_BRK = 999;
	private static final int MIN_POS = 1;
	private static final int MAX_POS = 999;
	
	
	
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
	
	//@Min(2, message="Invalid email address!")// @RegExp("[0-9]+")
	public void setBrk(int brk) {//TODO who & how to notify????????????????????????
		if(brk >= MIN_BRK && brk <= MAX_BRK)
			this.brk = brk;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		if(pos >= MIN_POS && pos<= MAX_POS)
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