package API;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import Model.ProgramAvail;
import Model.DateUtils;
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
		}

		if (event.getEventType() == EventType.SCHEDULED) { // avail is relevant only for sceduled.

			// handle avail
			ProgramAvail avail = filesList.get(schedulerInfo.getSchInfoName()).getAvailMap().get(window.getStart().toString() + window.getDuration().toString());

			if (avail == null ) { // There is no existing avail - need to create a new one!
		
				// check if overlapping
				// TODO check if work...
				Date windowEndDate = DateUtils.sumDates(window.getStart(), window.getDuration(), 1);
				
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
				if(schInfo.getOverlappedMins()[hours * 60 + minutes] || schInfo.getOverlappedMins()[((hours1 * 60 + minutes1) == 0) ? 1439 : (hours1 * 60 + minutes1 - 1)]) {
					logger.error("This avail overlaped another avail");
					return false;
				} else {
				// fill the array
					Arrays.fill(schInfo.getOverlappedMins(), hours * 60 + minutes, ((hours1 * 60 + minutes1) == 0) ? 1439 : (hours1 * 60 + minutes1), true);
				}
				
				avail = new ProgramAvail(window.getStart(), windowEndDate, window.getDuration());
				filesList.get(schedulerInfo.getSchInfoName()).getAvailMap().put(window.getStart().toString() + window.getDuration().toString(), avail);
			} else { // exist avail
				if (avail.getLeftDuration().compareTo(window.getLength()) == -1) {
					logger.error("The event length is bigger than the left window duration");
					return false;
				}
			}

			// set the left duration of the avail.
			avail.setLeftDuration(DateUtils.sumDates(avail.getLeftDuration(), window.getLength(), -1));
		}
		
		// add new key to set
		schInfo.getEventKeys().add(key);

		return true;
	}
}