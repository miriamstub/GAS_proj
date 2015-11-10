import java.util.HashMap;
import java.util.Map;

/**
 * This class hold the tree of all the objects. singleton class.
 * ManageTree -> filesList -> availsList + eventsList.
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

}