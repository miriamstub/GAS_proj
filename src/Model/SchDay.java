package Model;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class SchDay extends SchedulerInfo implements Comparable<SchedulerInfo>{

	private String date;
	private String zone;
	private String channel;
	
	
	public SchDay(String schInfoName, SchedulerInfoType schInfoType,
			String date, String zone, String channel) {
		super(schInfoName, schInfoType);
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
	
	public int compareTo(SchedulerInfo schedulerInfo) {
		if(super.compareTo(schedulerInfo) == -1)
			return -1;
		if (!this.date.equals(((SchDay)schedulerInfo).getDate()))
			return -1;
		if (!this.zone.equals(((SchDay)schedulerInfo).getZone()))
			return -1;
		if (!this.channel.equals(((SchDay)schedulerInfo).getChannel()))
			return -1;
		return 0;
	} 
	
	
}