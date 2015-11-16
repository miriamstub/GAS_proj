package sandbox;

import Deserializer.CCMSDeserializer;
import Serializer.CCMSSerializer;

public class Main {
	
	public static void main(String [ ] args) {
		
		
		//System.out.println("GAS project");
		//System.out.println("GAS project try commitasf");
		//System.out.println("GAS project try 45asd654commit2");
		//Log.getInstance().error("errorrrrrrrrrrrrrrr");
		//completeNumber(2); //
		//int a = 3;
		//System.out.print(Integer.toString(a));
		CCMSDeserializer.getInstance().run();
		CCMSSerializer.getInstance().run();
		
	
	}
	
	 public static void completeNumber(int myNumber) {
		 
		 String[] sCurrentObj = {"","0","00","000","0000","00000","000000","0000000","00000000"};
		 System.out.print(sCurrentObj[myNumber]);
		 
//		 while(myNumber>0){
//			 System.out.print("0");
//			 myNumber--;
//		 }
		
	}
}
