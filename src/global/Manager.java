package global;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Model.Event;
import Model.Protocol;
import Model.SchDay;

/**
 * This class hold the tree of all the objects. singleton class.
 * Manager -> filesList -> availsList + eventsList.
 * @author rtolidan
 *
 */
public class Manager {

	static Manager instance = null;

	private Map<String, Protocol> filesList = new HashMap<>();
	
	private Manager() {
	}

	public Map<String, Protocol> getFilesList() {
		return filesList;
	}

	public void setFilesList(Map<String, Protocol> tree) {
		this.filesList = tree;
	}

	public static Manager getInstance() {
		if(instance == null) {
			instance = new Manager();
		}
		return instance;
	}
	
	public void addProtocol(Protocol protocol) {
		filesList.put(protocol.getFileName(), protocol);
	}
	
	public void deleteProtocol(Protocol protocol) {
		protocol.getAvailMap().clear();
		protocol.getEventMap().clear();
		filesList.remove(protocol);
	}
	
	public Map<UUID, Event> getAllEvents(String fileName) {
		return filesList.get(fileName).getEventMap();
	}

}