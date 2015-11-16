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

import Model.Event;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import Model.ValidateUtils;


public class CCMSSerializer implements ISerializer{

	private static CCMSSerializer instance = new CCMSSerializer();

	private CCMSSerializer(){}

	public static CCMSSerializer getInstance(){
		return instance;	
	}



	public void run(){			
		for (Map.Entry<String, SchedulerInfo> entry : Manager.getInstance().getFilesList().entrySet())
		{
			createSchedulerInfo(true, entry.getValue());			
		}
	}


	public void createSchedulerInfo(boolean flag, SchedulerInfo schInfoEntry){

		FileWriter writer = null;
		try {
			if(flag){//say to create another directory with the dateTime


				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();

				writer = new FileWriter(SerializerConfiguration.FOLDERS_SERIALIZER_PATH + schInfoEntry.getSchInfoName() + dateFormat.format(date) + ".txt");//.SCH



			}
			else{//delete the last file and create new one

				try{
					File file = new File(SerializerConfiguration.FOLDERS_SERIALIZER_PATH + schInfoEntry.getSchInfoName());

					if(file.delete()){
						System.out.println(file.getName() + " is deleted!");
					}else{
						System.out.println("Delete operation is failed.");
					}

					writer = new FileWriter(SerializerConfiguration.FOLDERS_SERIALIZER_PATH + schInfoEntry.getSchInfoName() + ".txt");//.SCH


				}catch(Exception e){

					e.printStackTrace();

				}
			}

			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			/*bufferedWriter.write("REM Scheduled   ---------Window--------- ----actual-----");
			bufferedWriter.newLine();
			bufferedWriter.write("REM Date Time   Start dur brk pos length time    length  pos adname      stat");
			bufferedWriter.newLine();
			bufferedWriter.write("REM MMDD HHMMSS HHMM HHMM --- --- HHMMSS HHMMSS HHMMSSCC --- ");
			bufferedWriter.newLine();
			bufferedWriter.write("REM ----------------------------------------------------------------------------------------------------------------------------");
			bufferedWriter.newLine();*/

			String event = "REM Scheduled   ---------Window--------- ----actual-----\nREM Date Time   Start dur brk pos length time    length  pos adname      statREM MMDD HHMMSS HHMM HHMM --- --- HHMMSS HHMMSS HHMMSSCC ---\nREM ----------------------------------------------------------------------------------------------------------------------------\n";


			//run on all the events 
			for(Event myEvent : schInfoEntry.getEventMap().values()){
				ValidateUtils.setIProperties(SchedulerInfoType.CCMS);
				/*bufferedWriter.write("LOI ");
				bufferedWriter.write(ValidateUtils.getStringDate(myEvent.getDate()));
				bufferedWriter.write(" " + ValidateUtils.getStringTime(myEvent.getTime()));
				bufferedWriter.write(" " + ValidateUtils.getStringStart(myEvent.getWindow().getStart()));
				bufferedWriter.write(" " + ValidateUtils.getStringDuration(myEvent.getWindow().getDuration()));
				bufferedWriter.write(" " + ValidateUtils.completeIntToString(myEvent.getWindow().getBrk(),3));
				bufferedWriter.write(" " + ValidateUtils.completeIntToString(myEvent.getWindow().getPos(),3));
				bufferedWriter.write(" " + ValidateUtils.getStringLength(myEvent.getWindow().getLength()));
				bufferedWriter.write(" 000000 00000000 000");//actual
				bufferedWriter.write(" " + myEvent.getAdName());
				bufferedWriter.write(" 0000 AL TEST    ALU Real Channel Cu test spot" + myEvent.getWindow().getPos());//TODO what happen when the pos bigger than 1 dights?(in fact, it's can be between 1 - 999???
				bufferedWriter.write("     " + myEvent.getEventType().getValue());

				bufferedWriter.newLine();*/

				event += "LOI " + ValidateUtils.getStringDate(myEvent.getDate()) + " " + ValidateUtils.getStringTime(myEvent.getTime()) + " " + ValidateUtils.getStringStart(myEvent.getWindow().getStart()) + " " + ValidateUtils.getStringDuration(myEvent.getWindow().getDuration()) + " " + ValidateUtils.completeIntToString(myEvent.getWindow().getBrk(),3) + " " + ValidateUtils.completeIntToString(myEvent.getWindow().getPos(),3) + " " + ValidateUtils.getStringLength(myEvent.getWindow().getLength()) + " 000000 00000000 000" + " " + myEvent.getAdName() + " 0000 AL TEST    ALU Real Channel Cu test spot" + myEvent.getWindow().getPos() + "     " + myEvent.getEventType().getValue();
				bufferedWriter.write(event);
			}

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}