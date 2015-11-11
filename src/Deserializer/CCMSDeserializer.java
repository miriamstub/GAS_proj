package Deserializer;

import global.Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import log.Log;
import API.GeneratorAPI;
import Model.Avail;
import Model.DateFormats;
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

	public boolean validAndConvertRowParams(String[] rowObjs){	

		if(rowObjs.length < 13)
			return false;
		
		ValidateUtils.setIProperties(SchedulerInfoType.CCMS);
       	
        	date = ValidateUtils.getDate(rowObjs[1]);
    		time = ValidateUtils.getTime(rowObjs[2]);
    		start = ValidateUtils.getStart(rowObjs[3]);
    		dur = ValidateUtils.getDuration(rowObjs[4]);
    		brk = ValidateUtils.getBrk(rowObjs[5]);
    		pos = ValidateUtils.getPos(rowObjs[6]);
    		length = ValidateUtils.getLength(rowObjs[7]);
    		adName = ValidateUtils.getAdName(rowObjs[11]);
    		
    		if(rowObjs.length < 22){
    			eventType = EventType.SCHEDULED;
    		}
    		else{
    			eventType = ValidateUtils.getEventType(rowObjs[21]);
    		}
		
		return
		ValidateUtils.isValidActualTime(rowObjs[8]) &&  ValidateUtils.isValidActualLength(rowObjs[9]) && ValidateUtils.isValidActualPos(rowObjs[10]) && ValidateUtils.isValidStatusCode(rowObjs[12])
		&& ValidateUtils.notNull(date ,time,start ,dur ,brk,pos ,length,adName ,eventType);

	}
	

	public void run(){

		BufferedReader br = null;

		try {

			String sCurrentLine;
			File folder = new File("C:\\CCMSDE");
			Date date = null;
			for (File fileEntry : folder.listFiles()) {				

				try {
                    //check the file name
					date = new SimpleDateFormat(DateFormats.MMdd.toString()).parse(Integer.parseInt(fileEntry.getName().substring(0, 1), 16) + fileEntry.getName().substring(1, 3));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				//create the file
				SchDay mySchDay = new SchDay(fileEntry.getName(), SchedulerInfoType.CCMS, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), date, (fileEntry.getName().substring(3, 5)),fileEntry.getName().substring(5, 8));
				Manager.getInstance().addSchedulerInfo(mySchDay);
				
				Log.getInstance().info("New CCMS Scheduled Info " + mySchDay.getSchInfoName() + " created successfully");
				
				br = new BufferedReader(new FileReader(fileEntry.getPath()));
				while ((sCurrentLine = br.readLine()) != null) {
					
					String[] rowObjs = sCurrentLine.split("\\s+");

					if(!rowObjs[0].equals("REM")){
						if(validAndConvertRowParams(rowObjs)){
							GeneratorAPI.createEvent(date,time,adName,eventType,start,dur,brk,pos,length, fileEntry.getName());
						}	
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
