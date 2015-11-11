package API;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import Model.Event;
import Model.EventType;
import Model.ProtocolType;
import global.Manager;
import junit.framework.TestCase;

public class APITest extends TestCase {
	
	@Test
	public void test() {
		Manager manager = Manager.getInstance();
		
		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
//		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

		String timeInString1 = "10:00:00";
		Date start = null;
		try {
			start = time.parse(timeInString1);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		String timeInString2 = "00:20:00";
		Date dur = null;
		try {
			dur = time.parse(timeInString2);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String timeInString3 = "00:05:00";
		Date len = null;
		try {
			len = time.parse(timeInString3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String timeInString4 = "10:00:00";
		Date eventTime = null;
		try {
			eventTime = time.parse(timeInString4);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		Event correctSceEvent = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 1, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);

		// duplicate event
		Event duplicateSceEvent = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 1, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("reject!!!");
		assertNull(duplicateSceEvent);
		
		Event correctFillEvent = GeneratorAPI.createEvent(new Date(), eventTime, "event", EventType.FILL, start, dur, 1, 1, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctFillEvent.getID() + "!!!");
		assertNotNull(correctFillEvent);

		// duplicate event
		Event duplicateFillEvent = GeneratorAPI.createEvent(new Date(), eventTime, "event", EventType.FILL, start, dur, 1, 1, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("reject!!!");
		assertNull(duplicateFillEvent);
		
		// Length greater than duration 
		String dateInString4 = "00:25:00";
		Date len2 = null;
		try {
			len2 = time.parse(dateInString4);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Event lengthGreaterThanDur = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 1, len2, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("reject!!!");
		assertNull(lengthGreaterThanDur);

		// 2 events to the same file
		assertEquals(manager.getAllEvents("file").size(), 2);
		
		// duplicate event on another file
		Event correctSceEventForNewFile = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 1, len, "file2", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctSceEventForNewFile.getID() + "!!!");
		assertNotNull(correctSceEventForNewFile);
		
		// create event with new file
		assertEquals(manager.getFilesList().size(), 2);
		assertEquals(manager.getAllEvents("file").size(), 2);
		assertEquals(manager.getAllEvents("file2").size(), 1);
		
		Event correctSceEvent2 = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 2, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);
		
		Event correctSceEvent3 = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 3, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);
		
		Event correctSceEvent4 = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 4, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("created first event" + correctSceEvent4.getID() + "!!!");
		assertNotNull(correctSceEvent4);
		
		assertEquals(manager.getAllEvents("file").size(), 5);
		
		Event correctSceEvent5 = GeneratorAPI.createEvent(new Date(), new Date(), "event", EventType.SCHEDULED, start, dur, 1, 5, len, "file", ProtocolType.CCMS, new Date(), "zone", "channel");
		System.out.println("reject!!!");
		assertNull(correctSceEvent5);

	}

}
