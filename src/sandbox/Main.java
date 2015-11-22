package sandbox;

import API.GeneratorAPI;
import Model.SchedulerInfoType;

public class Main {
	
	public static void main(String [ ] args) {
		
		System.out.println("Welcome to GAS!");
		GeneratorAPI.deserializer(SchedulerInfoType.CCMS);
		GeneratorAPI.serializer(SchedulerInfoType.CCMS);
		System.out.println("@M.E.E.R.B");
		
	
	}
}
