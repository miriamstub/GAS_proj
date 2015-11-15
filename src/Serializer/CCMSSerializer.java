package Serializer;

import global.Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import Model.Event;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import Model.ValidateUtils;
 

public class CCMSSerializer {
	
	private static CCMSSerializer instance = new CCMSSerializer();
	
	private CCMSSerializer(){}
	
	public static CCMSSerializer getInstance()
	{
		return instance;	
	}
 
	public void run(boolean flagDeletePreviousDirectory){
		for (Map.Entry<String, SchedulerInfo> entry : Manager.getInstance().getFilesList().entrySet())
		{
			String schInfoName = entry.getKey();
		    System.out.println(entry.getKey() + "/" + entry.getValue());
        try {
        	
        	if(flagDeletePreviousDirectory){//say to delete the deserializer file
        		/*try{
            		
            		File file = new File("c:\\logfile20100131.log");
                	
            		if(file.delete()){
            			System.out.println(file.getName() + " is deleted!");
            		}else{
            			System.out.println("Delete operation is failed.");
            		}
            	   
            	}catch(Exception e){
            		
            		e.printStackTrace();
            		
            	}*/
        	}
        	else{
        		//to rename the directory to name+"previous"+currentDate(); 
        	}
        	
            FileWriter writer = new FileWriter("C:\\CCMSSE\\" + schInfoName + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("REM Scheduled   ---------Window--------- ----actual-----");
            bufferedWriter.newLine();
            bufferedWriter.write("REM Date Time   Start dur brk pos length time    length  pos adname      stat");
            bufferedWriter.newLine();
            bufferedWriter.write("REM MMDD HHMMSS HHMM HHMM --- --- HHMMSS HHMMSS HHMMSSCC --- ");
            bufferedWriter.newLine();
            bufferedWriter.write("REM ----------------------------------------------------------------------------------------------------------------------------");
            bufferedWriter.newLine();
 
            //run on all the events 
            for(Event myEvent : entry.getValue().getEventMap().values()){
            	ValidateUtils.setIProperties(SchedulerInfoType.CCMS);
            	bufferedWriter.write("LOI ");
            	
            	bufferedWriter.write(ValidateUtils.getStringDate(myEvent.getDate()));
            	bufferedWriter.write(" " + ValidateUtils.getStringTime(myEvent.getTime()));
            	bufferedWriter.write(" " + ValidateUtils.getStringStart(myEvent.getWindow().getStart()));
            	bufferedWriter.write(" " + ValidateUtils.getStringDuration(myEvent.getWindow().getDuration()));
            	bufferedWriter.write(" " + ValidateUtils.completeIntToString(myEvent.getWindow().getBrk(),3));
            	bufferedWriter.write(" " + ValidateUtils.completeIntToString(myEvent.getWindow().getPos(),3));
            	bufferedWriter.write(" " + ValidateUtils.getStringLength(myEvent.getWindow().getLength()));
            	bufferedWriter.write(" 000030 00000000 000");//actual
            	bufferedWriter.write(" " + myEvent.getAdName());
            	bufferedWriter.write(" 0000 AL TEST    ALU Real Channel Cu test spot" + myEvent.getWindow().getPos());//TODO what happen when the pos bigger than 1 dights?(in fact, it's can be between 1 - 999???
            	bufferedWriter.write("     " + myEvent.getEventType().getValue());
            	
            	bufferedWriter.newLine();
        	}
            
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	}
}
