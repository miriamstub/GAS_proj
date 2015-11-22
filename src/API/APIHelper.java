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
	
	private static final int MIN_BRK = 1;
	private static final int MAX_BRK = 999;
	private static final int MIN_POS = 1;
	private static final int MAX_POS = 999;
	

	/**
	 * Validate params of event - the relations between the properties.
	 * @param {Event} event
	 * @param {SchedulerInfo} schedulerInfo
	 * @return {boolean} true if success
	 */
	public static boolean validateParams(Event event, SchedulerInfo schedulerInfo) {
		Window window = event.getWindow();

		if (window.getLength().compareTo(window.getDuration()) == 1) {
			logger.error("The event length is bigger than the window duration");
			return false;
		}

		// handle SchedulerInfo
		SchedulerInfo schInfo = filesList.get(schedulerInfo.getSchInfoName());
		if (schInfo == null) { // create a new file
				schInfo = new SchDay(schedulerInfo.getSchInfoName(), ((SchDay)schedulerInfo).getDate(), ((SchDay)schedulerInfo).getZone(), ((SchDay)schedulerInfo).getChannel());
			filesList.put(schedulerInfo.getSchInfoName(), schInfo);
		}

		// TODO modify file!!

		String key = generateKey(event.getWindow(), event.getTime(), event.getEventType());
		if (schInfo.getEventKeys().contains(key)) {
			logger.error("duplicate event");
			return false;
		}

		if (event.getEventType() == EventType.SCHEDULED) { // avail is relevant only for scheduled.
			
			// assert brk & pos value 
			if (window.getBrk() >= MIN_BRK && window.getBrk() <= MAX_BRK) {
				logger.error("The event brk is not in the valid range (" + MIN_BRK + "-"+ MAX_BRK +")");
				return false;
			}
			if (window.getPos() >= MIN_POS && window.getPos() <= MAX_POS) {
				logger.error("The event pos is not in the valid range (" + MIN_POS + "-"+ MAX_POS +")");
				return false;
			}

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
	
	/**
	 * Generate key to the eventKeys of SchedulerInfo
	 * @param {Window} window
	 * @param {Date} eventTime
	 * @param {EventType} eventType
	 * @return {String} key - the generated key
	 */
	public static String generateKey(Window window, Date eventTime, EventType eventType) {
		String key = eventType == EventType.SCHEDULED ?
				"sce" + window.getBrk() + window.getPos() + window.getDuration() + window.getStart() : 
				"fill" + eventTime;
		return key;
	}
}