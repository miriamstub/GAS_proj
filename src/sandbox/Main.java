package sandbox;

import java.util.Date;

import global.Manager;
import API.GeneratorAPI;
import Model.*;

public class Main {

	public static void main(String [ ] args) {

		System.out.println("Welcome to GAS!");
		GeneratorAPI.deserializer(SchedulerInfoType.CCMS);

		SchedulerInfo si = Manager.getInstance().getFilesList().get("B1801001");
		Event e;
		
		e = new Event(new Date(), new Date(), new Date(), new Date(), 1, 1, new Date(), "eventqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", EventType.SCHEDULED);
		GeneratorAPI.createEvent(e, si);
		
		e = new Event(new Date(), new Date(), new Date(), new Date(), 1000, 1, new Date(), "a", EventType.SCHEDULED);
		GeneratorAPI.createEvent(e, si);

		e = new Event(new Date(), new Date(), new Date(), new Date(), 0, 1, new Date(), "a", EventType.FILL);
		GeneratorAPI.createEvent(e, si);
		
		e = new Event(null, null, null, null, 0, 0, null, null, null);
		GeneratorAPI.createEvent(e, si);

		GeneratorAPI.serializer(SchedulerInfoType.CCMS);
		System.out.println("@M.E.E.R.B");
	}
}