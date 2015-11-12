package Serializer;

import global.Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Deserializer.CCMSDeserializer;
import Model.Event;
 

public class CCMSSerializer {
	
	private static CCMSSerializer instance = new CCMSSerializer();
	
	private CCMSSerializer(){}
	
	public static CCMSSerializer getInstance()
	{
		return instance;	
	}
 
	public void run(boolean flag, String fileName){
		fileName = "B1801001";
        try {
        	
        	if(flag){//say to delete the deserializer file
       
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
        	
            FileWriter writer = new FileWriter("C:\\CCMSSE\\" + fileName + ".txt");
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
            for (Event myEvent : Manager.getInstance().getAllEvents(fileName)){
            	bufferedWriter.write("LOI ");
            	bufferedWriter.write(myEvent.getDate().toString());
            	bufferedWriter.write(" " + myEvent.getTime().toString());
            	bufferedWriter.write(" " + myEvent.getWindow().getStart().toString());
            	bufferedWriter.write(" " + myEvent.getWindow().getDuration().toString());
            	bufferedWriter.write(" " + myEvent.getWindow().getPos());
            	bufferedWriter.write(" " + myEvent.getWindow().getLength().toString());
            	bufferedWriter.write(" 000030 000000 00000000 000");
            	bufferedWriter.write(" " + myEvent.getAdName());
            	bufferedWriter.write(" 0000 AL TEST    ALU Real Channel Cu test spot" + myEvent.getWindow().getPos());
            	bufferedWriter.write("     " + myEvent.getEventType());
            	
            	bufferedWriter.newLine();
        	}
            
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
