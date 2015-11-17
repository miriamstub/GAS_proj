package API;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import Model.DateUtils;
import Model.Event;
import Model.EventType;
import Model.SchDay;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import global.Manager;
import junit.framework.TestCase;

public class APITest extends TestCase {
	
//	static Manager manager;
	
	@BeforeClass
    public static void oneTimeSetUp() {
//		manager = Manager.getInstance();
    }
	
	@Ignore
	@Test
	public void testCreate() {
//		Manager manager = Manager.getInstance();
//
//		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
//
////		BitSet myBitSet = new BitSet(10);
////		// fills the bitset with ten true values
////		myBitSet.set(0, 10);
//		
//		String timeInString1 = "10:00:00";
//		Date start = null;
//		try {
//			start = time.parse(timeInString1);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		
//		String timeInString2 = "00:20:00";
//		Date dur = null;
//		try {
//			dur = time.parse(timeInString2);
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
//		
//		String timeInString3 = "00:05:00";
//		Date len = null;
//		try {
//			len = time.parse(timeInString3);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		String timeInString4 = "10:00:00";
//		Date eventTime = null;
//		try {
//			eventTime = time.parse(timeInString4);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
//		SchedulerInfo schedulerInfo = new SchDay("file", SchedulerInfoType.CCMS, "date", "zone", "channel");
//		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
//		System.out.println("created first event" + correctSceEvent.getID() + "!!!");
//		assertNotNull(correctSceEvent);
//
//		// duplicate event
//		Event duplicateSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
//		duplicateSceEvent = GeneratorAPI.createEvent(duplicateSceEvent, schedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(duplicateSceEvent);
//		
//		Event correctFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
//		correctFillEvent = GeneratorAPI.createEvent(correctFillEvent, schedulerInfo);
//		System.out.println("created first event" + correctFillEvent.getID() + "!!!");
//		assertNotNull(correctFillEvent);
//
//		// duplicate event
//		Event duplicateFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
//		duplicateFillEvent = GeneratorAPI.createEvent(duplicateFillEvent, schedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(duplicateFillEvent);
//		
//		// Length greater than duration 
//		String dateInString4 = "00:25:00";
//		Date len2 = null;
//		try {
//			len2 = time.parse(dateInString4);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		Event lengthGreaterThanDur = new Event(new Date(), new Date(), start, dur, 1, 1, len2, "event", EventType.SCHEDULED);
//		lengthGreaterThanDur = GeneratorAPI.createEvent(lengthGreaterThanDur, schedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(lengthGreaterThanDur);
//
//		// 2 events to the same file
//		assertEquals(manager.getAllEvents("file").size(), 2);
//		
//		// duplicate event on another file
//		Event correctSceEventForNewFile = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
//		SchedulerInfo schedulerInfo2 = new SchDay("file2", SchedulerInfoType.CCMS, "date", "zone", "channel");
//		correctSceEventForNewFile = GeneratorAPI.createEvent(correctSceEventForNewFile, schedulerInfo2);
//		System.out.println("created first event" + correctSceEventForNewFile.getID() + "!!!");
//		assertNotNull(correctSceEventForNewFile);
//		
//		// create event with new file
//		assertEquals(manager.getFilesList().size(), 2);
//		assertEquals(manager.getAllEvents("file").size(), 2);
//		assertEquals(manager.getAllEvents("file2").size(), 1);
//		
//		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
//		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
//		System.out.println("created first event" + correctSceEvent2.getID() + "!!!");
//		assertNotNull(correctSceEvent2);
//		
//		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 3, len, "event", EventType.SCHEDULED);
//		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
//		System.out.println("created first event" + correctSceEvent3.getID() + "!!!");
//		assertNotNull(correctSceEvent3);
//		
//		Event correctSceEvent4 = new Event(new Date(), new Date(), start, dur, 1, 4, len, "event", EventType.SCHEDULED);
//		correctSceEvent4 = GeneratorAPI.createEvent(correctSceEvent4, schedulerInfo);
//		System.out.println("created first event" + correctSceEvent4.getID() + "!!!");
//		assertNotNull(correctSceEvent4);
//		
//		assertEquals(manager.getAllEvents("file").size(), 5);
//		
//		Event correctSceEvent5 = new Event(new Date(), new Date(), start, dur, 1, 5, len, "event", EventType.SCHEDULED);
//		correctSceEvent5 = GeneratorAPI.createEvent(correctSceEvent5, schedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(correctSceEvent5);
//		
//		// overlapped
//		SchedulerInfo newSchedulerInfo = new SchDay("newFile", SchedulerInfoType.CCMS, "date", "zone", "channel");
//		// 10:00 - 10:20 - success!
//		Event correctSceEventNewFile = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
//		correctSceEventNewFile = GeneratorAPI.createEvent(correctSceEventNewFile, newSchedulerInfo);
//		System.out.println("created first event" + correctSceEventNewFile.getID() + "!!!");
//		assertNotNull(correctSceEventNewFile);
//
//		String timeInString5 = "10:10:00";
//		Date startOverlapped = null;
//		try {
//			startOverlapped = time.parse(timeInString5);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		
//		// 10:10 - 10:30 - fail
//		Event ovverlapedSceEventNewFile1 = new Event(new Date(), new Date(), startOverlapped, dur, 1, 2, len, "event", EventType.SCHEDULED);
//		ovverlapedSceEventNewFile1 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile1, newSchedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(ovverlapedSceEventNewFile1);
//		
//		String timeInString6 = "00:30:00";
//		Date endOverlapped = null;
//		try {
//			endOverlapped = time.parse(timeInString6);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		
//		// 10:00 - 10:30 - fail
//		Event ovverlapedSceEventNewFile2 = new Event(new Date(), new Date(), start, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
//		ovverlapedSceEventNewFile2 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile2, newSchedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(ovverlapedSceEventNewFile2);
//		
//		// 10:10 - 10:40 - fail
//		Event ovverlapedSceEventNewFile3 = new Event(new Date(), new Date(), startOverlapped, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
//		ovverlapedSceEventNewFile3 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile3, newSchedulerInfo);
//		System.out.println("reject!!!");
//		assertNull(ovverlapedSceEventNewFile3);
//		
//		// 10:10 - 10:30 - fail
////		Event ovverlapedSceEventNewFile4 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
////		ovverlapedSceEventNewFile4 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile4, newSchedulerInfo);
////		System.out.println("reject!!!");
////		assertNull(ovverlapedSceEventNewFile4);
//		
//		String timeInString7 = "09:50:00";
//		Date startOverlapped2 = null;
//		try {
//			startOverlapped2 = time.parse(timeInString7);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		
//		// 09:50 - 10:20 - fail
////		Event ovverlapedSceEventNewFile5 = new Event(new Date(), new Date(), startOverlapped2, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
////		ovverlapedSceEventNewFile5 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile5, newSchedulerInfo);
////		System.out.println("reject!!!");
////		assertNull(ovverlapedSceEventNewFile5);
//
//		// 09:50 - 09:55 - success!
//		Event correctSceEventNewFile2 = new Event(new Date(), new Date(), startOverlapped2, len, 1, 2, len, "event", EventType.SCHEDULED);
//		correctSceEventNewFile2 = GeneratorAPI.createEvent(correctSceEventNewFile2, newSchedulerInfo);
//		System.out.println("created first event" + correctSceEventNewFile2.getID() + "!!!");
//		assertNotNull(correctSceEventNewFile2);
	}
	
	@Test
	public void testDelte() {
		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");

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

		// create one
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("file", SchedulerInfoType.CCMS, "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event1 " + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);	
		
		// try create duplicate
		Event failSceEvent1 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		failSceEvent1 = GeneratorAPI.createEvent(failSceEvent1, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(failSceEvent1);
		
		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
		System.out.println("created first event2 " + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);
		
		// try delete not exist id
		assertFalse(GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), Manager.getUUID()));

		// delete him
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent.getID());

		// check if the id not exist
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()).getEventMap().get(correctSceEvent.getID()));
		
		// delete second
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent2.getID());
	
		// check if the file deleted
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()));
		
		// try to create again (if not deleted successfully - duplicate)
		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
		System.out.println("created first event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);	
		
	}
	
	@Ignore
	@Test
	public void testAll() {
//		SchedulerInfo schedulerInfo = new SchDay("file", SchedulerInfoType.CCMS, "date", "zone", "channel");
//		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
//
//		String timeInString1 = "00:00:00";
//		Date start = null;
//		try {
//			start = time.parse(timeInString1);
//		} catch (ParseException e2) {
//			e2.printStackTrace();
//		}
//		
//		String timeInString2 = "01:00:00";
//		Date dur = null;
//		try {
//			dur = time.parse(timeInString2);
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
//		
//		String timeInString3 = "00:01:00";
//		Date len = null;
//		try {
//			len = time.parse(timeInString3);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i < 24; i++) {
//			for (int j = 0; j < 60; j++) {
//				Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, j, len, "event", EventType.SCHEDULED);
//				correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
//			}
//			start = DateUtils.sumDates(start, dur, 1);
//		}
//		
//		GeneratorAPI.serializer();
	}

}
