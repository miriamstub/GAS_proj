package API;

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

	static boolean isOverlapping(Date date1, Date date2) {
		 return date1.before(date2) || date1.after(date2);
	}

	static boolean validateParams(Date eventTime, EventType eventType, Date windowLength, Date windowDuration, int windowBrk, Date windowStart, int windowPos, Map<String, Protocol> filesList,  String fileName, ProtocolType protocolType, Date protocolDate, String protocolZone, String protocolChannel) {

		if (windowLength.compareTo(windowDuration) == 1) {
			logger.log(null, "The event length is bigger than the window duration");
			return false;
		}

		// check if the avail is overlapped.
		if (isOverlapping(windowStart, new Date(windowStart.getTime() + windowDuration.getTime()))) {
			logger.log(null, "There is overlapping on the avail dates");
			return false;
		}	

		// handle avail
		Avail avail = filesList.get(fileName).getAvailMap().get(windowStart.getTime() + windowDuration.getTime());

		if (avail == null ) { // There is no existing avail 
			Avail newAvail = new Avail(windowStart, new Date(windowStart.getTime() + windowDuration.getTime()), windowDuration.getTime());
			avail = filesList.get(fileName).getAvailMap().put(windowStart.toString() + windowDuration.toString(), newAvail);
		} else { // exist avail
			if (avail.getLeftDuration() < windowLength.getTime()) {
				logger.log(null, "The event length is bigger than the left window duration");
				return false;
			}
		}
		// set the left duration of the avail.
		avail.setLeftDuration(avail.getLeftDuration() - windowLength.getTime());

		// TODO modify file!!
		
		// handle protocol
		Protocol protocol = filesList.get(fileName);
		if(protocol == null) { // create a new file
			if(protocolType == ProtocolType.CCMS || protocolType == ProtocolType.SCTE118) {
				protocol = new SchDay(fileName, protocolType, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), protocolDate, protocolZone, protocolChannel);
			}
			filesList.put(fileName, protocol);
			return true; // if the file is new, we don't have to check his break position and etc.
		}

		protocol.getAvailMap();
		for (Event event : protocol.getEventMap().values()) {
			Window window = event.getWindow();
			if (eventType == EventType.SCHEDULED && window.getBrk() == windowBrk && window.getDuration() == windowDuration && window.getPos() == windowPos && window.getStart() == windowStart 
					|| eventType == EventType.FILL && event.getTime() == eventTime) { // duplicate, reject
				logger.log(null, "duplicate event");
				return false;
			}
		}

		return true;
	}

}
