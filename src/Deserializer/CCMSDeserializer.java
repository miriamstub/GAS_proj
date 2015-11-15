package Deserializer;

import global.Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import log.Log;
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

	public boolean validAndConvertRowParams(String[] rowObjs){	

		if(rowObjs.length < 13)
			return false;

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
		ValidateUtils.setIProperties(SchedulerInfoType.CCMS);

		try {

			String sCurrentLine;
			File folder = new File("C:\\CCMSDE");
			for (File fileEntry : folder.listFiles()) {	
				String schName = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf("."));
				if(ValidateUtils.isValidSchedulerName(schName))
				{
					SchDay mySchDay = new SchDay(schName, SchedulerInfoType.CCMS, new HashMap<UUID, Event>(), new HashMap<String, Avail>(), schName.substring(0, 3), schName.substring(3, 5),schName.substring(5, 8));

					Manager.getInstance().addSchedulerInfo(mySchDay);

					Log.getInstance().info("New CCMS Scheduled Info " + mySchDay.getSchInfoName() + " created successfully");

					br = new BufferedReader(new FileReader(fileEntry.getPath()));
					while ((sCurrentLine = br.readLine()) != null) {

						String[] rowObjs = sCurrentLine.split("\\s+");

						if(!rowObjs[0].equals("REM")){
							if(validAndConvertRowParams(rowObjs)){
								GeneratorAPI.createEvent(date,time,adName,eventType,start,dur,brk,pos,length, schName);
							}	
						}
					}
				}
				else
					Log.getInstance().error("Can not create CCMS Scheduled Info " + fileEntry.getName() + ". SchedulerInfo name is invalid");
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
