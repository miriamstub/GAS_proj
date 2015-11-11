package Deserializer;

import global.Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import API.GeneratorAPI;
import Model.Avail;
import Model.Event;
import Model.EventType;
import Model.SchedulerInfoType;
import Model.SchDay;
import Model.ValidateUtils;

public class CCMSDeserializer {
	

	private static CCMSDeserializer instance = new CCMSDeserializer();

	
	private CCMSDeserializer(){}
	
	public static CCMSDeserializer getInstance()
	{
		return instance;	
	}
	
	Date date, time, start, dur, length;
	int brk, pos;
	String adName;
	EventType eventType;

	public boolean validAndConvertRowParams(String eventRow){
		
		String[] rowObjs = new String[15];
		rowObjs = eventRow.split("\\s+");
		ValidateUtils.setIProperties(SchedulerInfoType.CCMS);


		date = ValidateUtils.getDate(rowObjs[0]);
		time = ValidateUtils.getTime(rowObjs[1]);
		start = ValidateUtils.getStart(rowObjs[2]);
		dur = ValidateUtils.getDuration(rowObjs[3]);
		brk = ValidateUtils.getBrk(rowObjs[4]);
		pos = ValidateUtils.getPos(rowObjs[5]);
		length = ValidateUtils.getLength(rowObjs[6]);
		adName = ValidateUtils.getAdName(rowObjs[10]);
		eventType = ValidateUtils.getEventType(rowObjs[14]);
		
		
		return
		ValidateUtils.isValidActualTime(rowObjs[7]) &&  ValidateUtils.isValidActualLength(rowObjs[8]) && ValidateUtils.isValidActualPos(rowObjs[9])
		&& ValidateUtils.notNull(date ,time,start ,dur ,brk,pos ,length,adName ,eventType);

	}
	

	public void run(){

		BufferedReader br = null;

		try {

			String sCurrentLine;
			File folder = new File("C:\\CCMSDE");
			Date date = null;
			for (File fileEntry : folder.listFiles()) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

				//try {

					//date = formatter.parse(fileEntry.getName().substring(0, 2));

				//} catch (ParseException e) {
					//e.printStackTrace();
				//}

				//create the file

				SchDay mySchDay = new SchDay(fileEntry.getName(), SchedulerInfoType.CCMS, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), date, (fileEntry.getName().substring(2, 4)),fileEntry.getName().substring(4, 6));
				Manager.getInstance().addSchedulerInfo(mySchDay);
				

				System.out.println(fileEntry.getName());
				
				br = new BufferedReader(new FileReader(fileEntry.getPath()));
				while ((sCurrentLine = br.readLine()) != null) {
					
					System.out.println(sCurrentLine);
					if(validAndConvertRowParams(sCurrentLine)){
						GeneratorAPI.createEvent(date,time,adName,eventType,start,dur,brk,pos,length, fileEntry.getName());
						System.out.println("New event created successfully");
					}

					else{

						System.out.println("Event cannot be created, invalid entries received from CCMS");
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
