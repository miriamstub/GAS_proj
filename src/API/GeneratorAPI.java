package API;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import Deserializer.CCMSDeserializer;
import Model.ProgramAvail;
import Model.DateUtils;
import Model.Event;
import Model.SchedulerInfo;
import Model.Window;
import Serializer.CCMSSerializer;
import global.Manager;
import log.Log;

/**
 * CRUD functions
 * @author rtolidan
 *
 */
public class GeneratorAPI {

	static Logger logger = Log.getInstance();
	static Map<String, SchedulerInfo> filesList = Manager.getInstance().getFilesList();
	
	/**
	 * Create event (called from serializer)
	 * @param {Event} event
	 * @param {SchedulerInfo} schedulerInfo
	 * @return {Event} event - if success return the event, else return null.
	 */
	public static Event createEvent(Event event, SchedulerInfo schedulerInfo) {
		if(APIHelper.validateParams(event, schedulerInfo)) {
			UUID newUUID = event.generateUUID();
			filesList.get(schedulerInfo.getSchInfoName()).getEventMap().put(newUUID, event);
			logger.info("Created event: " +event.getID());
			return event;
		}
		return null;
 	}

	/**
	 * Modify event (called from serializer)
	 * @param {String} eventId 
	 * @param {String} schInfoName
	 * @param {Event} event - the changed properties
	 * @param {SchedulerInfo} schedulerInfo - the changed properties
	 * @return {Event} event - if success return the event, else return null.
	 */
	public static Event modifyEvent(UUID eventId, String schInfoName, Event event, SchedulerInfo schedulerInfo) {
		Event oldEvent = filesList.get(schInfoName).getEventMap().get(eventId);
		if (oldEvent == null) {
			logger.error("The event does not exist");
			return null;
		}

		// check if the uniqe properties update - delete key
		if (oldEvent.compareTo(event) != 0) {
			String key = APIHelper.generateKey(oldEvent.getWindow(), oldEvent.getTime(), oldEvent.getEventType());
			filesList.get(schInfoName).getEventKeys().remove(key);
		}
		
		if (APIHelper.validateParams(event, schedulerInfo)) {
			oldEvent.setAdName(event.getAdName());
			oldEvent.setDate(event.getDate());
			oldEvent.setEventType(event.getEventType());
			oldEvent.setTime(event.getTime());
			oldEvent.getWindow().setBrk(event.getWindow().getBrk());
			oldEvent.getWindow().setDuration(event.getWindow().getDuration());
			oldEvent.getWindow().setLength(event.getWindow().getLength());
			oldEvent.getWindow().setStart(event.getWindow().getStart());
			oldEvent.getWindow().setPos(event.getWindow().getPos());
			
			// check if the file update - delete from old
			if (filesList.get(schInfoName).compareTo(schedulerInfo) != 0) { // there is new params
				// remove from old file. not need to create the new file - it done on validateParams.
				filesList.get(schInfoName).getEventMap().remove(eventId);
				// add event to the relevant file
				filesList.get(schedulerInfo.getSchInfoName()).getEventMap().put(oldEvent.getID(), oldEvent);
			}
			
			logger.info("Modified event: " + oldEvent.getID());
			return oldEvent;
		}

		return null;
	}

	/**
	 * Delete existing event
	 * Called from serialize / API
	 * @param {String} schInfoName
	 * @param {UUID} eventId
	 * @return {boolean} true if success, false if the event not exist.
	 */
	public static boolean deleteEvent(String schInfoName, UUID eventId) {
		Event event = filesList.get(schInfoName).getEventMap().get(eventId);
		if (event == null) {
			logger.error("The event does not exist");
			return false;
		}

		// handled sum duration
		Window window = event.getWindow();
		ProgramAvail avail = filesList.get(schInfoName).getAvailMap().get(window.getStart().toString() + window.getDuration().toString());
		avail.setLeftDuration(DateUtils.sumDates(avail.getLeftDuration(), window.getLength(), 1));
		
		// delete the avail if there is no events that use him.
		if(avail.getLeftDuration() == DateUtils.sumDates(avail.getEndTime(), avail.getStartTime(), -1)) {
			filesList.get(schInfoName).getAvailMap().remove(window.getStart().getTime() + window.getDuration().getTime());
		}
		
		// remove key
		String key = APIHelper.generateKey(event.getWindow(), event.getTime(), event.getEventType());
		filesList.get(schInfoName).getEventKeys().remove(key);
		 
		// remove the event
		Manager.getInstance().deleteEvent(schInfoName, eventId);

		// if the event is the last on this file - delete the file also.
		if (filesList.get(schInfoName).getEventMap().size() == 0) {
			filesList.remove(schInfoName);
		}
		return true;
	}

	/**
	 * Delete Day
	 */
	public static void deleteDay() {

	}

	/**
	 * Serialize the files.
	 */
	public static void serializer() {
		CCMSSerializer.getInstance().run();
	}
	
	/**
	 * Deserialize the object model.
	 * @param folderName
	 */
	public static void deserializer(String folderName) {
		CCMSDeserializer.getInstance().run(folderName);
	}

}
