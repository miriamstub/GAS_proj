package Serializer;

import global.Manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import log.Log;

import org.apache.log4j.Logger;

import Model.Event;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import Model.ConvertAndValidateUtils;

/**
 * The CCMS Serialize object read java model and create CCMS protocol.
 * @author eelmisha
 *
 */
public class CCMSSerializer implements ISerializer{

	private static CCMSSerializer instance = new CCMSSerializer();
	Logger log = Log.getInstance();

	private CCMSSerializer(){}

	public static CCMSSerializer getInstance(){

		return instance;	
	}

	public void run(){	

		//delete all the last file in the CCMS directory

		File file = new File(SerializerConfiguration.FOLDERS_SERIALIZER_PATH );

		for (File myFile : file.listFiles()) {
			myFile.delete();
		}

		for (Map.Entry<String, SchedulerInfo> entry : Manager.getInstance().getFilesList().entrySet())
		{
			createSchedulerInfo(entry.getValue());			
		}
	}

	public void createSchedulerInfo(SchedulerInfo schInfoEntry){

		FileWriter writer = null;
		try {

			writer = new FileWriter(SerializerConfiguration.FOLDERS_SERIALIZER_PATH + schInfoEntry.getSchInfoName() + ".txt");//.SCH     

			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			String event = "REM Scheduled   ---------Window--------- ----actual-----\r\nREM Date Time   Start dur brk pos length time    length  pos adname      stat\r\nREM MMDD HHMMSS HHMM HHMM --- --- HHMMSS HHMMSS HHMMSSCC ---\r\nREM ----------------------------------------------------------------------------------------------------------------------------\r\n";
			bufferedWriter.write(event);

			//run on all the events and write them to txt file 
			for(Event myEvent : schInfoEntry.getEventMap().values()){

				ConvertAndValidateUtils.setIProperties(SchedulerInfoType.CCMS);

				event = new StringBuffer().append("LOI ").append(ConvertAndValidateUtils.getStringDate(myEvent.getDate())).append(" ").append(ConvertAndValidateUtils.getStringTime(myEvent.getTime()))
						.append(" ").append(ConvertAndValidateUtils.getStringStart(myEvent.getWindow().getStart())).append(" ").append(ConvertAndValidateUtils.getStringDuration(myEvent.getWindow().getDuration()))
						.append(" ").append(ConvertAndValidateUtils.completeIntToString(myEvent.getWindow().getBrk(),3)).append(" ").append(ConvertAndValidateUtils.completeIntToString(myEvent.getWindow().getPos(),3))
						.append(" ").append(ConvertAndValidateUtils.getStringLength(myEvent.getWindow().getLength())).append(" 000000 00000000 000").append(" ").append(myEvent.getAdName())
						.append(" 0000 AL TEST    ALU Real Channel Cu test spot").append(myEvent.getWindow().getPos()).append("     ")
						.append(myEvent.getEventType().getValue()).append("\r\n").toString();

				bufferedWriter.write(event);
			}

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}