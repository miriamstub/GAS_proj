package API;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import Model.Avail;
import Model.Event;
import Model.EventType;
import Model.Protocol;
import Model.ProtocolType;
import Model.SchDay;
import Model.Window;
import log.Log;

/**
 * Helper for API functions.
 * @author rtolidan
 *
 */
public class APIHelper {

	static Logger logger = Log.getInstance();

	static boolean isOverlapping(Date start1, Date start2, Date end1, Date end2) {
		 return start1.before(end2) || start2.before(end1);
	}

	static boolean validateParams(Date eventTime, EventType eventType, Date windowLength, Date windowDuration, int windowBrk, Date windowStart, int windowPos, Map<String, Protocol> filesList,  String fileName, ProtocolType protocolType, Date protocolDate, String protocolZone, String protocolChannel) {

		if (windowLength.compareTo(windowDuration) == 1) {
			logger.error("The event length is bigger than the window duration");
			return false;
		}

		// handle protocol
		Protocol protocol = filesList.get(fileName);
		if (protocol == null) { // create a new file
			if(protocolType == ProtocolType.CCMS || protocolType == ProtocolType.SCTE118) {
				protocol = new SchDay(fileName, protocolType, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), protocolDate, protocolZone, protocolChannel);
			}
			filesList.put(fileName, protocol);
		}
		
		// handle avail
		Avail avail = filesList.get(fileName).getAvailMap().get(windowStart.toString() + windowDuration.toString());

		if (avail == null ) { // There is no existing avail - need to create a new one!
			// check if this avail do not overlaped another avail.
			for (Avail oldAvail : filesList.get(fileName).getAvailMap().values()) {
				if (isOverlapping(windowStart, oldAvail.getStartTime(), sumDates(windowStart, windowDuration), oldAvail.getEndTime())) {
					logger.error("This avail overlaped another avail");
					return false;
				}
			}
			
			avail = new Avail(windowStart, sumDates(windowStart, windowDuration), windowDuration.getTime());
			filesList.get(fileName).getAvailMap().put(windowStart.toString() + windowDuration.toString(), avail);
		} else { // exist avail
			if (avail.getLeftDuration() < windowLength.getTime()) {
				logger.error("The event length is bigger than the left window duration");
				return false;
			}
		}
		// set the left duration of the avail.
		avail.setLeftDuration(avail.getLeftDuration() - windowLength.getTime());

		// TODO modify file!!

		protocol.getAvailMap();
		for (Event event : protocol.getEventMap().values()) {
			Window window = event.getWindow();
			if (eventType == EventType.SCHEDULED && window.getBrk() == windowBrk && window.getDuration() == windowDuration && window.getPos() == windowPos && window.getStart() == windowStart 
					|| eventType == EventType.FILL && event.getTime() == eventTime) { // duplicate, reject
				logger.error("duplicate event");
				return false;
			}
		}

		return true;
	}
	
	public static Date sumDates(Date date1, Date date2) {
		Calendar dur = Calendar.getInstance();
		dur.setTime(date2);
		int hour = dur.get(Calendar.HOUR_OF_DAY);
		int min = dur.get(Calendar.MINUTE);
		int sec = dur.get(Calendar.SECOND);
		int milli = dur.get(Calendar.MILLISECOND);
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date1);

	    cal.add(Calendar.HOUR, hour);
	    cal.add(Calendar.MINUTE, min);
	    cal.add(Calendar.SECOND, sec);
	    cal.add(Calendar.MILLISECOND, milli);
	    
	   return cal.getTime();
	}
}


