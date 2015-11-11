package global;

import java.util.HashMap;
import java.util.Map;

import Model.SchedulerInfo;

import java.util.UUID;

import Model.Event;

/**
 * This class hold the tree of all the objects. singleton class.
 * Manager -> filesList -> availsList + eventsList.
 * @author rtolidan
 *
 */
public class Manager {

	static Manager instance = null;

	private Map<String, SchedulerInfo> filesList = new HashMap<>();
	
	private Manager() {
	}

	public Map<String, SchedulerInfo> getFilesList() {
		return filesList;
	}

	public void setFilesList(Map<String, SchedulerInfo> tree) {
		this.filesList = tree;
	}

	public static Manager getInstance() {
		if(instance == null) {
			instance = new Manager();
		}
		return instance;
	}
	
	public void addSchedulerInfo(SchedulerInfo schInfo) {
		filesList.put(schInfo.getSchInfoName(), schInfo);
	}
	
	public void deleteSchedulerInfo(SchedulerInfo schInfo) {
		schInfo.getAvailMap().clear();
		schInfo.getEventMap().clear();
		filesList.remove(schInfo);
	}
	
	public Map<UUID, Event> getAllEvents(String schInfoName) {
		return filesList.get(schInfoName).getEventMap();
	}
	
	
	public static HashMap<UUID,String> UUIDPool = new HashMap<UUID,String>();//TODO discuss pool type
	
	public static UUID getUUID(){
		UUID uuid = UUID.randomUUID();
		while(UUIDPool.containsKey(uuid)){
			uuid = UUID.randomUUID();
			System.out.println("Duplicate UUID!!!");
		}
		UUIDPool.put(uuid, "");
		return uuid;
	}

}