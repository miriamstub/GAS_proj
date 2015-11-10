package API;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import Deserializer.CCMSDeserializer;
import Model.Avail;
import Model.Event;
import Model.EventType;
import Model.Protocol;
import Model.ProtocolType;
import Model.Window;
import Serializer.Serializer;
import global.Manager;
import log.Log;

/**
 * CRUD functions
 * @author rtolidan
 *
 */
public class GeneratorAPI {

	static Logger logger = Log.getInstance();
	static Map<String, Protocol> filesList = Manager.getInstance().getFilesList();
	
	// TODO change to ... (create + modify)
	
	// called from de...
	// I have the file object, not need the all params...
	public static Event createEvent(Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String fileName) {
		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, fileName, null, null, null, null)) {
			Event newEvent =  new Event(eventDate, eventTime, windowStart, windowDuration, windowBrk, windowPos, windowLength, adName, eventType);
			filesList.get(fileName).getEventMap().put(newEvent.getID(), newEvent);
			return newEvent;
		}
		return null;
	}
	
	// called from API
	// do not know if there is a new file or older - get ID and all params
	public static Event createEvent(Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String fileName, ProtocolType protocolType, Date protocolDate, String protocolZone, String protocolChannel) {
		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, fileName, protocolType, protocolDate, protocolZone, protocolChannel)) {
			Event newEvent =  new Event(eventDate, eventTime, windowStart, windowDuration, windowBrk, windowPos, windowLength, adName, eventType);
			filesList.get(fileName).getEventMap().put(newEvent.getID(), newEvent);
			return newEvent;
		}
		return null;
	}

	/**
	 * Modify exist event
	 * Called from API
	 * @param {UUID} eventId
	 * @param {Date} eventDate
	 * @param {Date} eventTime
	 * @param {String} adName
	 * @param {EventType} eventType
	 * @param {Date} windowStart
	 * @param {Date} windowDuration
	 * @param {int} windowBrk
	 * @param {int} windowPos
	 * @param {Date} windowLength
	 * @param {String} fileName
	 * @param {ProtocolType} protocolType
	 * @param {Date} protocolDate
	 * @param {String} protocolZone
	 * @param {String} protocolChannel
	 * @return {Event} the modified event
	 */
	public static Event modifyEvent(UUID eventId, Date eventDate, Date eventTime, String adName, EventType eventType, Date windowStart, Date windowDuration, int windowBrk, int windowPos, Date windowLength, String fileName, ProtocolType protocolType, Date protocolDate, String protocolZone, String protocolChannel) {
		Event event = filesList.get(fileName).getEventMap().get(eventId); // get the event from his file
		if (event == null) {
			logger.error("The event does not exist");
			return null;
		}

		if(APIHelper.validateParams(eventTime, eventType, windowLength, windowDuration, windowBrk, windowStart, windowPos, filesList, fileName, protocolType, protocolDate, protocolZone, protocolChannel)) {
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
	}

	/**
	 * Delete existing event
	 * Called from serialize / API
	 * @param {String} fileName
	 * @param {UUID} eventId
	 */
	public static void deleteEvent(String fileName, UUID eventId) {
		Event event = filesList.get(fileName).getEventMap().get(eventId);
		if (event == null) {
			logger.error("The event does not exist");
			return;
		}

		// handled sum duration
		Window window = event.getWindow();
		Avail avail = filesList.get(fileName).getAvailMap().get(window.getStart().getTime() + window.getDuration().getTime());
		avail.setLeftDuration(avail.getLeftDuration() + window.getLength().getTime());
		
		// delete the avail if there is no events that use him.
		if(avail.getLeftDuration() == (avail.getEndTime().getTime() - avail.getStartTime().getTime())) {
			filesList.get(fileName).getAvailMap().remove(window.getStart().getTime() + window.getDuration().getTime());
		}
		
		// remove the event
		filesList.get(fileName).getEventMap().remove(eventId);
		// if the event is the last on this file - delete the file also.
		if(filesList.get(fileName).getEventMap().size() == 0) {
			filesList.remove(fileName);
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
		Serializer.run();
	}
	
	/**
	 * Deserialize the object model.
	 */
	public static void deserializer() {
		CCMSDeserializer.getInstance().run();
	}

}
