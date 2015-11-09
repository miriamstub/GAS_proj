package Deserializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Deserializer {

	static String[] sCurrentObj = new String[15];

	public static boolean checked(String eventRow){

		int[] sumArray = {4,6,4,4,3,3,6,6,8,3,11,4,2,4,4};
		String[] sCurrentObj = new String[15];

		sCurrentObj =  eventRow.split(" ");
		int degel = 0;
		while(degel < 15){

			if(sCurrentObj[degel].length() != sumArray[degel]){
				return false;
			}

			degel++;
		}

		return true;
	}

	public static void run(){

		BufferedReader br = null;


		try {

			String sCurrentLine;
			File folder = new File("C:\\CCMSDE");
			for (File fileEntry : folder.listFiles()) {

				//create the file
				//SchDay mySchDay = new SchDay(fileEntry.getName(), ProtocolType.CCMS, new HashMap<String, Event>, new HashMap<String, Avail>, fileEntry.getName().substring(0, 2), fileEntry.getName().substring(2, 4),fileEntry.getName().substring(4, 6),)
				//  Manager.addFile(mySchDay);
				System.out.println(fileEntry.getName());

				br = new BufferedReader(new FileReader(fileEntry.getPath()));
				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
					if(checked(sCurrentLine)){
						//Event myEvent = createEvent(sCurrentObj[0],sCurrentObj[1],sCurrentObj[2],sCurrentObj[3],sCurrentObj[4],sCurrentObj[5],sCurrentObj[6],sCurrentObj[7],sCurrentObj[8],sCurrentObj[9],sCurrentObj[10],sCurrentObj[11],sCurrentObj[12],sCurrentObj[13],sCurrentObj[14], fileEntry.getName())
						//if(myEvent){
						//eventMap.add(myEvent);
						System.out.println("create new event");
						//}
					}

					else{

						System.out.println("I can't create new event the num of the digits not ok");
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
