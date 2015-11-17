package API;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import Model.Avail;
import Model.Event;
import Model.EventType;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import Model.SchDay;
import Model.Window;
import global.Manager;
import log.Log;

/**
 * Helper for API functions.
 * @author rtolidan
 *
 */
public class APIHelper {

	static Logger logger = Log.getInstance();
	static Map<String, SchedulerInfo> filesList = Manager.getInstance().getFilesList();

	/*static boolean validateParams(Date eventTime, EventType eventType, Date windowLength, Date windowDuration, int windowBrk, Date windowStart, int windowPos, Map<String, SchedulerInfo> filesList,  String schInfoName, SchedulerInfoType schedulerInfoType, Date schedulerInfoDate, String schedulerInfoZone, String schedulerInfoChannel) {

		if (windowLength.compareTo(windowDuration) == 1) {
			logger.error("The event length is bigger than the window duration");
			return false;
		}

		// handle SchedulerInfo
		SchedulerInfo schInfo = filesList.get(schInfoName);
		if (schInfo == null) { // create a new file
			if(schedulerInfoType == SchedulerInfoType.CCMS || schedulerInfoType == SchedulerInfoType.SCTE118) {
				schInfo = new SchDay(schInfoName, schedulerInfoType, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), schedulerInfoDate.toString(), schedulerInfoZone, schedulerInfoChannel);
			}
			filesList.put(schInfoName, schInfo);
		}

		// TODO modify file!!

		schInfo.getAvailMap();
		for (Event event : schInfo.getEventMap().values()) {
			Window window = event.getWindow();
			if (eventType == EventType.SCHEDULED && window.getBrk() == windowBrk && window.getDuration() == windowDuration && window.getPos() == windowPos && window.getStart() == windowStart 
					|| eventType == EventType.FILL && event.getTime() == eventTime) { // duplicate, reject
				logger.error("duplicate event");
				return false;
			}
		}

		if (eventType == EventType.SCHEDULED) { // avail is relevant only for sceduled.

			// handle avail
			Avail avail = filesList.get(schInfoName).getAvailMap().get(windowStart.toString() + windowDuration.toString());

			if (avail == null ) { // There is no existing avail - need to create a new one!
				
				// check if this avail do not overlaped another avail.
				for (Avail oldAvail : filesList.get(schInfoName).getAvailMap().values()) {
					if (isOverlapping(windowStart, oldAvail.getStartTime(), sumDates(windowStart, windowDuration, 1), oldAvail.getEndTime())) {
						logger.error("This avail overlaped another avail");
						return false;
					}
				}

				avail = new Avail(windowStart, sumDates(windowStart, windowDuration, 1), windowDuration);
				filesList.get(schInfoName).getAvailMap().put(windowStart.toString() + windowDuration.toString(), avail);
			} else { // exist avail
				if (avail.getLeftDuration().compareTo(windowLength) == -1) {
					logger.error("The event length is bigger than the left window duration");
					return false;
				}
			}

			// set the left duration of the avail.
			avail.setLeftDuration(sumDates(avail.getLeftDuration(), windowLength, -1));
		}

		return true;
	}*/

	/*static boolean isOverlapping(Date start1, Date start2, Date end1, Date end2) {
		return start1.before(end2) && start2.before(end1);
	}*/

	public static Date sumDates(Date date1, Date date2, int type) {
		Calendar dur = Calendar.getInstance();
		dur.setTime(date2);
		int hour = dur.get(Calendar.HOUR_OF_DAY);
		int min = dur.get(Calendar.MINUTE);
		int sec = dur.get(Calendar.SECOND);
		int milli = dur.get(Calendar.MILLISECOND);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		cal.add(Calendar.HOUR, hour * type);
		cal.add(Calendar.MINUTE, min * type);
		cal.add(Calendar.SECOND, sec * type);
		cal.add(Calendar.MILLISECOND, milli * type);

		return cal.getTime();
	}

	public static boolean validateParams(Event event, SchedulerInfo schedulerInfo) {
		Window window = event.getWindow();
		if (window.getLength().compareTo(window.getDuration()) == 1) {
			logger.error("The event length is bigger than the window duration");
			return false;
		}

		// handle SchedulerInfo
		SchedulerInfo schInfo = filesList.get(schedulerInfo.getSchInfoName());
		if (schInfo == null) { // create a new file
			if(schedulerInfo.getSchInfoType() == SchedulerInfoType.CCMS || schedulerInfo.getSchInfoType() == SchedulerInfoType.SCTE118) {
				schInfo = new SchDay(schedulerInfo.getSchInfoName(), schedulerInfo.getSchInfoType(), ((SchDay)schedulerInfo).getDate(), ((SchDay)schedulerInfo).getZone(), ((SchDay)schedulerInfo).getChannel());
			}
			filesList.put(schedulerInfo.getSchInfoName(), schInfo);
		}

		// TODO modify file!!

		String key = event.getEventType() == EventType.SCHEDULED ?
				"sce" + event.getWindow().getBrk() + event.getWindow().getPos() + event.getWindow().getDuration() + event.getWindow().getStart() : 
				"fill" + event.getTime();
		
		if (schInfo.getEventKeys().contains(key)) {
			logger.error("duplicate event");
			return false;
		} else {
			// add new key to set
			schInfo.getEventKeys().add(key);
		}

		if (event.getEventType() == EventType.SCHEDULED) { // avail is relevant only for sceduled.

			// handle avail
			Avail avail = filesList.get(schedulerInfo.getSchInfoName()).getAvailMap().get(window.getStart().toString() + window.getDuration().toString());

			if (avail == null ) { // There is no existing avail - need to create a new one!
		
				// check if overlapping
				// TODO check if work...
				Date windowEndDate = sumDates(window.getStart(), window.getDuration(), 1);
				
				Calendar calendarStart = Calendar.getInstance();
				calendarStart.setTime(window.getStart());
				int hours = calendarStart.get(Calendar.HOUR_OF_DAY);
				int minutes = calendarStart.get(Calendar.MINUTE);
				
				Calendar calendarEnd = Calendar.getInstance();
				calendarEnd.setTime(windowEndDate);
				int hours1 = calendarEnd.get(Calendar.HOUR_OF_DAY);
				int minutes1 = calendarEnd.get(Calendar.MINUTE);
				
//				boolean[] aaa = Arrays.copyOfRange(schInfo.getOverlappedMins(), hours * 60 + minutes, hours1 * 60 + minutes1);
//				if (Arrays.asList(aaa).contains(true)) {
				if(schInfo.getOverlappedMins()[hours * 60 + minutes] || schInfo.getOverlappedMins()[hours1 * 60 + minutes1]) {
					logger.error("This avail overlaped another avail");
					return false;
				} else {
				// fill the array
					Arrays.fill(schInfo.getOverlappedMins(), hours * 60 + minutes, hours1 * 60 + minutes1, true);
				}
				
				avail = new Avail(window.getStart(), windowEndDate, window.getDuration());
				filesList.get(schedulerInfo.getSchInfoName()).getAvailMap().put(window.getStart().toString() + window.getDuration().toString(), avail);
			} else { // exist avail
				if (avail.getLeftDuration().compareTo(window.getLength()) == -1) {
					logger.error("The event length is bigger than the left window duration");
					return false;
				}
			}

			// set the left duration of the avail.
			avail.setLeftDuration(sumDates(avail.getLeftDuration(), window.getLength(), -1));
		}

		return true;
	}

}

