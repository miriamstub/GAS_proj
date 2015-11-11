package Deserializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import API.GeneratorAPI;
import Model.Event;
import Model.EventType;
import Model.ProtocolType;
import Model.ValidateUtils;

public class CCMSDeserializer {

	static String[] rowObjs = new String[15];
	
	private static CCMSDeserializer instance = new CCMSDeserializer();
//	public static boolean checked(String eventRow){
//
//		int[] sumArray = {4,6,4,4,3,3,6,6,8,3,11,4,2,4,4};
//		String[] sCurrentObj = new String[15];
//
//		sCurrentObj =  eventRow.split(" ");
//		int degel = 0;
//		while(degel < 15){
//
//			if(sCurrentObj[degel].length() != sumArray[degel]){
//				return false;
//			}
//
//			degel++;
//		}
//
//		return true;
//	}
	
	
	private CCMSDeserializer(){}
	
	public static CCMSDeserializer getInstance()
	{
		return instance;	
	}
	
	Date date, time, start, dur, length;
	int brk, pos;
	String adName;
	boolean actualTime, actualLength, actualPos;
	EventType eventType;

	public boolean validAndConvertRowParams(String eventRow){

		rowObjs = eventRow.split(" ");
		ValidateUtils.setIProperties(ProtocolType.CCMS);

		date = ValidateUtils.getDate(rowObjs[0]);
		time = ValidateUtils.getTime(rowObjs[1]);
		start = ValidateUtils.getStart(rowObjs[2]);
		dur = ValidateUtils.getDuration(rowObjs[3]);
		brk = ValidateUtils.getBrk(rowObjs[4]);
		pos = ValidateUtils.getPos(rowObjs[5]);
		length = ValidateUtils.getLength(rowObjs[6]);
		actualTime = ValidateUtils.isValidActualTime(rowObjs[7]);
		actualLength = ValidateUtils.isValidActualLength(rowObjs[8]);
		actualPos = ValidateUtils.isValidActualPos(rowObjs[9]);
		adName = ValidateUtils.getAdName(rowObjs[10]);
		//ValidateUtils.isValidStat(rowObjs[11]);
		eventType = ValidateUtils.getEventType(rowObjs[15]);

		return ValidateUtils.notNull(date ,time,start ,dur ,brk,pos ,length,actualTime ,actualLength,actualPos,adName ,eventType);

	}
	

	public void run(){

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
					if(validAndConvertRowParams(sCurrentLine)){
						Event myEvent = GeneratorAPI.createEvent(date,time,adName,eventType,start,dur,brk,pos,length, fileEntry.getName());
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
