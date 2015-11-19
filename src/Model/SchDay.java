package Model;

/** 
 * SchDay.java class
 * Contains characteristics of scheduler from this type.
 * @property {String} date - day in month.
 * @property {String} zone - representation of the network within a single headend.
 * @property {String} channel - representation of the headend.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class SchDay extends SchedulerInfo{

	private String date;
	private String zone;
	private String channel;
	
	
	public SchDay(String schInfoName,
			String date, String zone, String channel) {
		super(schInfoName);
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

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
			this.date = date;
	}
	
	
}