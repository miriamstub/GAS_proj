package API;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import Deserializer.CCMSDeserializer;
import Model.ProgramAvail;
import Model.DateUtils;
import Model.Event;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
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
	 * @return {Event} event - if success return the event, else return null.
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
	 * Deserialize all schedulers.
	 */
	public static void deserializer() {
		CCMSDeserializer.getInstance().run();
		//118Deserializer.getInstance().run();
	}
	
	/**
	 * Deserialize schedulers from a certain type.
	 * @param {SchedulerInfoType} type
	 */
	public static void deserializer(SchedulerInfoType type) {
		switch(type){
		case CCMS:
			CCMSDeserializer.getInstance().run();
			break;
		//case SCTE118:
			//SCTE118Deserializer.getInstance().run();
			//break;
		default:
			break;
		}
	}
	
	/**
	 * Serialize all schedulers (Manager.getInstance().getFilesList()) to a certain type.
	 * the serializer will assert the events' values according to the type,
	 * if there is an event that not stand in the type demands, this event will reject.
	 * @param {SchedulerInfoType} type
	 */
	public static void serializer(SchedulerInfoType type) {
		switch(type){
		case CCMS:
			CCMSSerializer.getInstance().run();
			break;
		//case SCTE118:
			//SCTE118Serializer.getInstance().run();
			//break;
		default:
			break;
		}
	}
	
	
}
