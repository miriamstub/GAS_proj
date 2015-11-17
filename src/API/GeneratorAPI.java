package API;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import Deserializer.CCMSDeserializer;
import Model.Avail;
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
	
	/*// TODO change to ... (create + modify)
	
	// called from de...
	// I have the file object, not need the all params...
	public static Event createEvent(Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String schInfoName) {
		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, schInfoName, null, null, null, null)) {
			Event newEvent =  new Event(eventDate, eventTime, windowStart, windowDuration, windowBrk, windowPos, windowLength, adName, eventType);
			filesList.get(schInfoName).getEventMap().put(newEvent.getID(), newEvent);
			return newEvent;
		}
		return null;
	}
	
	// called from API
	// do not know if there is a new file or older - get ID and all params
	public static Event createEvent(Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String schInfoName, SchedulerInfoType schInfoType, Date protocolDate, String protocolZone, String protocolChannel) {
		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, schInfoName, schInfoType, protocolDate, protocolZone, protocolChannel)) {
			Event newEvent =  new Event(eventDate, eventTime, windowStart, windowDuration, windowBrk, windowPos, windowLength, adName, eventType);
			filesList.get(schInfoName).getEventMap().put(newEvent.getID(), newEvent);
			return newEvent;
		}
		return null;
	}*/
	// TODO create from API!!
	
	public static Event createEvent(Event event, SchedulerInfo schedulerInfo) {
		if(APIHelper.validateParams(event, schedulerInfo)) {
			filesList.get(schedulerInfo.getSchInfoName()).getEventMap().put(event.getID(), event);
			logger.info("Created event: " +event.getID());
			return event;
		}
		return null;
 	}

	// TODO modify from API!!
/*	public static Event modifyEvent(UUID eventId, Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String schInfoName, SchedulerInfoType schInfoType, Date protocolDate, String protocolZone, String protocolChannel) {
		Event event = filesList.get(schInfoName).getEventMap().get(eventId); // get the event from his file
		if (event == null) {
			logger.error("The event does not exist");
			return null;
		}
		// delete key

		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, schInfoName, schInfoType, protocolDate, protocolZone, protocolChannel)) {
			event.setAdName(adName);
			event.setDate(eventDate);
			event.setEventType(eventType);
			event.setTime(eventTime);
			event.getWindow().setBrk(windowBrk);
			event.getWindow().setDuration(windowDuration);
			event.getWindow().setLength(windowLength);
			event.getWindow().setStart(windowStart);
			event.getWindow().setPos(windowPos);
			
			// add event to the relevant file!!!!
			// TODO if create new file to delete the event from the old file list.
		}

		return event;
	}*/
	
	public static Event modifyEvent(Event event, SchedulerInfo schedulerInfo) {
//		Event event = filesList.get(schInfoName).getEventMap().get(eventId); // get the event from his file
		// TODO ???		
		if (event == null) {
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
	 * @param {String} fileName
	 * @param {UUID} eventId
	 */
	public static void deleteEvent(String schInfoName, UUID eventId) {
		Event event = filesList.get(schInfoName).getEventMap().get(eventId);
		if (event == null) {
			logger.error("The event does not exist");
			return;
		}

		// handled sum duration
		Window window = event.getWindow();
		Avail avail = filesList.get(schInfoName).getAvailMap().get(window.getStart().getTime() + window.getDuration().getTime());
		avail.setLeftDuration(APIHelper.sumDates(avail.getLeftDuration(), window.getLength(), 1));
		
		// delete the avail if there is no events that use him.
		if(avail.getLeftDuration() == APIHelper.sumDates(avail.getEndTime(), avail.getStartTime(), -1)) {
			filesList.get(schInfoName).getAvailMap().remove(window.getStart().getTime() + window.getDuration().getTime());
		}
		
		// TODO remove key
		
		// remove the event
		filesList.get(schInfoName).getEventMap().remove(eventId);
		// if the event is the last on this file - delete the file also.
		if(filesList.get(schInfoName).getEventMap().size() == 0) {
			filesList.remove(schInfoName);
		}
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
		CCMSDeserializer.getInstance().run();
	}

}
