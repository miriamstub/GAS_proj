package Serializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
 

public class Serializer {
 
	public static void run(){
        try {
        	
        	
            FileWriter writer = new FileWriter("C:\\CCMSSE\\" + "test1.txt");//fileName
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
 
            //run on all the events 
            //for (Event myEvent : Manager.getAllEvent(fileName)){
            //bufferedWriter.write(myEvent.) + " ";
            //bufferedWriter.write(myEvent.) + " ";
            
            //bufferedWriter.newLine();
        	//}
            
            bufferedWriter.write("Hello World");
            bufferedWriter.newLine();
            bufferedWriter.write("See You Again!");
            
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
