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
	 * Create event (called from serelizer)
	 * @param {Event} event
	 * @param {SchedulerInfo} schedulerInfo
	 * @return {Event} event - if success rturn the event, else return null.
	 */
	public static Event createEvent(Event event, SchedulerInfo schedulerInfo) {
		if(APIHelper.validateParams(event, schedulerInfo)) {
			filesList.get(schedulerInfo.getSchInfoName()).getEventMap().put(event.getID(), event);
			logger.info("Created event: " +event.getID());
			return event;
		}
		return null;
 	}

	public static Event modifyEvent(UUID eventId, String schInfoName, Event event, SchedulerInfo schedulerInfo) {
		Event oldEvent = filesList.get(schInfoName).getEventMap().get(eventId); // get the event from his file
		if (oldEvent == null) {
			logger.error("The event does not exist");
			return null;
		}
		
		// delete key

		if (APIHelper.validateParams(event, schedulerInfo)) {
			event.setAdName(event.getAdName());
			event.setDate(event.getDate());
			event.setEventType(event.getEventType());
			event.setTime(event.getTime());
			event.getWindow().setBrk(event.getWindow().getBrk());
			event.getWindow().setDuration(event.getWindow().getDuration());
			event.getWindow().setLength(event.getWindow().getLength());
			event.getWindow().setStart(event.getWindow().getLength());
			event.getWindow().setPos(event.getWindow().getPos());
			
			// add event to the relevant file!!!!
			// TODO if create new file to delete the event from the old file list.
		}

		return event;
	}

	/**
	 * Delete existing event
	 * Called from serialize / API
	 * @param {String} schInfoName
	 * @param {UUID} eventId
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
		Manager.getInstance().deleteEvent(schInfoName,eventId);
		
		
		// if the event is the last on this file - delete the file also.
		if (filesList.get(schInfoName).getEventMap().size() == 0) {
			filesList.remove(schInfoName);
		}
		return true;
	}

	/**
	 * 
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
	 */
	public static void deserializer() {
		CCMSDeserializer.getInstance().run("CCMS");
	}

}
