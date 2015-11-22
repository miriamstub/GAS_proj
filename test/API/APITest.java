package API;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import Model.DateUtils;
import Model.Event;
import Model.EventType;
import Model.SchDay;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import Serializer.SerializerConfiguration;
import global.Manager;
import log.Log;

public class APITest {

	static Manager manager;
	static SimpleDateFormat time;
	static Logger logger;

	@BeforeClass
	public static void oneTimeSetUp() {
		manager = Manager.getInstance();
		time = new SimpleDateFormat("hh:mm:ss");
		logger = Log.getInstance();
	}

	@Test
	public void testCreate() {
		logger.info("\n\nCreate Tests");
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

		logger.info("Create successful event of scheduled");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("fileCreate", SchedulerInfoType.CCMS, "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event" + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);

		// duplicate event
		logger.info("Try create duplicate event of scheduled");
		Event duplicateSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		duplicateSceEvent = GeneratorAPI.createEvent(duplicateSceEvent, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(duplicateSceEvent);

		logger.info("Create successful event of fill");
		Event correctFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
		correctFillEvent = GeneratorAPI.createEvent(correctFillEvent, schedulerInfo);
		System.out.println("created first event" + correctFillEvent.getID() + "!!!");
		assertNotNull(correctFillEvent);

		// duplicate event
		logger.info("Try create duplicate event of fill");
		Event duplicateFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
		duplicateFillEvent = GeneratorAPI.createEvent(duplicateFillEvent, schedulerInfo);
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

		logger.info("Try create event with length greater than duration");
		Event lengthGreaterThanDur = new Event(new Date(), new Date(), start, dur, 1, 1, len2, "event", EventType.SCHEDULED);
		lengthGreaterThanDur = GeneratorAPI.createEvent(lengthGreaterThanDur, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(lengthGreaterThanDur);

		// 2 events to the same file
		assertEquals(manager.getAllEvents("fileCreate").size(), 2);

		// duplicate event on another file
		logger.info("Create successfully event that duplicate on another file");
		Event correctSceEventForNewFile = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo2 = new SchDay("file2", SchedulerInfoType.CCMS, "date", "zone", "channel");
		correctSceEventForNewFile = GeneratorAPI.createEvent(correctSceEventForNewFile, schedulerInfo2);
		System.out.println("created first event" + correctSceEventForNewFile.getID() + "!!!");
		assertNotNull(correctSceEventForNewFile);

		// create event with new file
		assertEquals(manager.getAllEvents("fileCreate").size(), 2);
		assertEquals(manager.getAllEvents("file2").size(), 1);

		logger.info("Create events for check the length of the events");
		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
		System.out.println("created first event" + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);

		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 3, len, "event", EventType.SCHEDULED);
		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
		System.out.println("created first event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);

		Event correctSceEvent4 = new Event(new Date(), new Date(), start, dur, 1, 4, len, "event", EventType.SCHEDULED);
		correctSceEvent4 = GeneratorAPI.createEvent(correctSceEvent4, schedulerInfo);
		System.out.println("created first event" + correctSceEvent4.getID() + "!!!");
		assertNotNull(correctSceEvent4);

		assertEquals(manager.getAllEvents("fileCreate").size(), 5);

		logger.info("Try create event that he has no place for his length on the avail");
		Event correctSceEvent5 = new Event(new Date(), new Date(), start, dur, 1, 5, len, "event", EventType.SCHEDULED);
		correctSceEvent5 = GeneratorAPI.createEvent(correctSceEvent5, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(correctSceEvent5);

		// overlapped
		SchedulerInfo newSchedulerInfo = new SchDay("newFile", SchedulerInfoType.CCMS, "date", "zone", "channel");
		// 10:00 - 10:20 - success!
		logger.info("Create first event for checking overlapped");
		Event correctSceEventNewFile = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEventNewFile = GeneratorAPI.createEvent(correctSceEventNewFile, newSchedulerInfo);
		System.out.println("created first event" + correctSceEventNewFile.getID() + "!!!");
		assertNotNull(correctSceEventNewFile);

		String timeInString5 = "10:10:00";
		Date startOverlapped = null;
		try {
			startOverlapped = time.parse(timeInString5);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 10:10 - 10:30 - fail
		logger.info("Overlapped - 10:10 - 10:30 - fail");
		Event ovverlapedSceEventNewFile1 = new Event(new Date(), new Date(), startOverlapped, dur, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile1 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile1, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile1);

		String timeInString6 = "00:30:00";
		Date endOverlapped = null;
		try {
			endOverlapped = time.parse(timeInString6);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 10:00 - 10:30 - fail
		logger.info("Overlapped - 10:10 - 10:30 - fail");
		Event ovverlapedSceEventNewFile2 = new Event(new Date(), new Date(), start, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile2 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile2, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile2);

		// 10:10 - 10:40 - fail
		logger.info("Overlapped - 10:10 - 10:40 - fail");
		Event ovverlapedSceEventNewFile3 = new Event(new Date(), new Date(), startOverlapped, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile3 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile3, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile3);

		String timeInString7 = "09:50:00";
		Date startOverlapped2 = null;
		try {
			startOverlapped2 = time.parse(timeInString7);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 09:50 - 10:20 - fail
		logger.info("Overlapped - 09:50 - 10:20 - fail");
		Event ovverlapedSceEventNewFile5 = new Event(new Date(), new Date(), startOverlapped2, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile5 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile5, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile5);

		String timeInString8 = "00:40:00";
		Date endOverlapped2 = null;
		try {
			endOverlapped2 = time.parse(timeInString8);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 09:50 - 10:30 - fail
		logger.info("Overlapped - 09:50 - 10:30 - fail");
		Event ovverlapedSceEventNewFile6 = new Event(new Date(), new Date(), startOverlapped2, endOverlapped2, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile6 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile6, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile6);

		// 09:50 - 09:55 - success!
		logger.info("Create event - not overlapped - 09:50 - 09:55 - success!");
		Event correctSceEventNewFile2 = new Event(new Date(), new Date(), startOverlapped2, len, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEventNewFile2 = GeneratorAPI.createEvent(correctSceEventNewFile2, newSchedulerInfo);
		System.out.println("created first event" + correctSceEventNewFile2.getID() + "!!!");
		assertNotNull(correctSceEventNewFile2);
	}

	@Test
	public void testModify() {
		logger.info("\n\nModify tests");
		String timeInString1 = "11:00:00";
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
		logger.info("Create successful event");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("mFile", SchedulerInfoType.CCMS, "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event1 " + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);	
		String oldKey = APIHelper.generateKey(correctSceEvent.getWindow(), correctSceEvent.getTime(), correctSceEvent.getEventType());
		assertTrue("The key exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(oldKey));

		// Modify the key properties
		logger.info("Modify the key properties");
		Event modifiedSceEvent = new Event(new Date(), new Date(), start, dur, 2, 1, len, "event", EventType.SCHEDULED);
		modifiedSceEvent = GeneratorAPI.modifyEvent(correctSceEvent.getID(), schedulerInfo.getSchInfoName(), modifiedSceEvent, schedulerInfo);
		System.out.println("modified event " + modifiedSceEvent.getID() + "!!!");
		assertNotNull(modifiedSceEvent);	
		assertFalse("The old key not exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(oldKey));
		String newKey = APIHelper.generateKey(modifiedSceEvent.getWindow(), modifiedSceEvent.getTime(), modifiedSceEvent.getEventType());
		assertTrue("The new key exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(newKey));

		// Modify the file
		logger.info("Modify the file");
		Event modifiedSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		SchedulerInfo modifySchedulerInfo = new SchDay("mFile2", SchedulerInfoType.CCMS, "date2", "zone2", "channel2");
		modifiedSceEvent2 = GeneratorAPI.modifyEvent(correctSceEvent.getID(), schedulerInfo.getSchInfoName(), modifiedSceEvent2, modifySchedulerInfo);
		System.out.println("modified event " + modifiedSceEvent.getID() + "!!!");
		assertNotNull(modifiedSceEvent);
		assertFalse("The old file not contains the event", manager.getFilesList().get("mFile").getEventMap().containsKey(correctSceEvent.getID()));
		assertTrue("The new file contains the event", manager.getFilesList().get("mFile2").getEventMap().containsKey(modifiedSceEvent2.getID()));
	}

	@Test
	public void testDelte() {
		logger.info("\n\nDelete tests");
		String timeInString1 = "11:00:00";
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
		logger.info("Create successful event");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("file", SchedulerInfoType.CCMS, "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event1 " + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);	

		// try create duplicate
		logger.info("try create duplicate");
		Event failSceEvent1 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		failSceEvent1 = GeneratorAPI.createEvent(failSceEvent1, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(failSceEvent1);

		logger.info("Create second successful event");
		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
		System.out.println("created first event2 " + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);

		// try delete not exist id
		logger.info("try delete not exist id");
		assertFalse(GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), Manager.getUUID()));

		// delete him
		logger.info("delete first event");
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent.getID());

		// check if the id not exist
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()).getEventMap().get(correctSceEvent.getID()));

		// delete second
		logger.info("delete second event");
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent2.getID());

		// check if the file deleted
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()));

		// try to create again (if not deleted successfully - duplicate)
		logger.info("try to create again and check that the event deleted (if not deleted successfully it will be duplicate)");
		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
		System.out.println("created event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);
	}

	@Test
	public void testAll() {
		logger.info("\n\n\nTest all - create 1440 successful events");
		
		String timeInString1 = "00:00:00";
		Date start = null;
		try {
			start = time.parse(timeInString1);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		String timeInString2 = "01:00:00";
		Date dur = null;
		try {
			dur = time.parse(timeInString2);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String timeInString3 = "00:01:00";
		Date len = null;
		try {
			len = time.parse(timeInString3);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SchedulerInfo schedulerInfo = new SchDay("B1801001", SchedulerInfoType.CCMS, "date", "zone", "channel");

		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 60; j++) {
				Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, j, len, "event123456", EventType.SCHEDULED);
				correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
			}
			start = DateUtils.sumDates(start, dur, 1);
		}

		System.out.println("start se" + System.nanoTime());
		GeneratorAPI.serializer();
		System.out.println("finish se" + System.nanoTime());
		BufferedReader br = null;
		String beforeDe = "";
		String afterDe = "";
		String sCurrentLine;

		try {
			br = new BufferedReader(new FileReader("C:\\CCMS\\CCMS\\B1801001.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				beforeDe.concat(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		System.out.println("start de" + System.nanoTime());
		GeneratorAPI.deserializer("CCMS");
		System.out.println("finish de" + System.nanoTime());
		System.out.println("start se" + System.nanoTime());
		GeneratorAPI.serializer();
		System.out.println("finish se" + System.nanoTime());
		try {
			br = new BufferedReader(new FileReader("C:\\CCMS\\CCMS\\B1801001.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				afterDe.concat(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// check if the files are equals
		assertTrue(beforeDe.equals(afterDe));
	}
package API;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import Model.DateUtils;
import Model.Event;
import Model.EventType;
import Model.SchDay;
import Model.SchedulerInfo;
import Model.SchedulerInfoType;
import global.Manager;
import log.Log;

public class APITest {

	static Manager manager;
	static SimpleDateFormat time;
	static Logger logger;

	@BeforeClass
	public static void oneTimeSetUp() {
		manager = Manager.getInstance();
		time = new SimpleDateFormat("hh:mm:ss");
		logger = Log.getInstance();
	}

	@Test
	public void testCreate() {
		logger.info("\n\nCreate Tests");
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

		logger.info("Create successful event of scheduled");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("fileCreate", "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event" + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);

		// duplicate event
		logger.info("Try create duplicate event of scheduled");
		Event duplicateSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		duplicateSceEvent = GeneratorAPI.createEvent(duplicateSceEvent, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(duplicateSceEvent);

		logger.info("Create successful event of fill");
		Event correctFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
		correctFillEvent = GeneratorAPI.createEvent(correctFillEvent, schedulerInfo);
		System.out.println("created first event" + correctFillEvent.getID() + "!!!");
		assertNotNull(correctFillEvent);

		// duplicate event
		logger.info("Try create duplicate event of fill");
		Event duplicateFillEvent = new Event(new Date(), eventTime, start, dur, 1, 1, len, "event", EventType.FILL);
		duplicateFillEvent = GeneratorAPI.createEvent(duplicateFillEvent, schedulerInfo);
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

		logger.info("Try create event with length greater than duration");
		Event lengthGreaterThanDur = new Event(new Date(), new Date(), start, dur, 1, 1, len2, "event", EventType.SCHEDULED);
		lengthGreaterThanDur = GeneratorAPI.createEvent(lengthGreaterThanDur, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(lengthGreaterThanDur);

		// 2 events to the same file
		assertEquals(manager.getAllEvents("fileCreate").size(), 2);

		// duplicate event on another file
		logger.info("Create successfully event that duplicate on another file");
		Event correctSceEventForNewFile = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo2 = new SchDay("file2", "date", "zone", "channel");
		correctSceEventForNewFile = GeneratorAPI.createEvent(correctSceEventForNewFile, schedulerInfo2);
		System.out.println("created first event" + correctSceEventForNewFile.getID() + "!!!");
		assertNotNull(correctSceEventForNewFile);

		// create event with new file
		assertEquals(manager.getAllEvents("fileCreate").size(), 2);
		assertEquals(manager.getAllEvents("file2").size(), 1);

		logger.info("Create events for check the length of the events");
		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
		System.out.println("created first event" + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);

		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 3, len, "event", EventType.SCHEDULED);
		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
		System.out.println("created first event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);

		Event correctSceEvent4 = new Event(new Date(), new Date(), start, dur, 1, 4, len, "event", EventType.SCHEDULED);
		correctSceEvent4 = GeneratorAPI.createEvent(correctSceEvent4, schedulerInfo);
		System.out.println("created first event" + correctSceEvent4.getID() + "!!!");
		assertNotNull(correctSceEvent4);

		assertEquals(manager.getAllEvents("fileCreate").size(), 5);

		logger.info("Try create event that he has no place for his length on the avail");
		Event correctSceEvent5 = new Event(new Date(), new Date(), start, dur, 1, 5, len, "event", EventType.SCHEDULED);
		correctSceEvent5 = GeneratorAPI.createEvent(correctSceEvent5, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(correctSceEvent5);

		// overlapped
		SchedulerInfo newSchedulerInfo = new SchDay("newFile", "date", "zone", "channel");
		// 10:00 - 10:20 - success!
		logger.info("Create first event for checking overlapped");
		Event correctSceEventNewFile = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEventNewFile = GeneratorAPI.createEvent(correctSceEventNewFile, newSchedulerInfo);
		System.out.println("created first event" + correctSceEventNewFile.getID() + "!!!");
		assertNotNull(correctSceEventNewFile);

		String timeInString5 = "10:10:00";
		Date startOverlapped = null;
		try {
			startOverlapped = time.parse(timeInString5);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 10:10 - 10:30 - fail
		logger.info("Overlapped - 10:10 - 10:30 - fail");
		Event ovverlapedSceEventNewFile1 = new Event(new Date(), new Date(), startOverlapped, dur, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile1 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile1, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile1);

		String timeInString6 = "00:30:00";
		Date endOverlapped = null;
		try {
			endOverlapped = time.parse(timeInString6);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 10:00 - 10:30 - fail
		logger.info("Overlapped - 10:10 - 10:30 - fail");
		Event ovverlapedSceEventNewFile2 = new Event(new Date(), new Date(), start, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile2 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile2, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile2);

		// 10:10 - 10:40 - fail
		logger.info("Overlapped - 10:10 - 10:40 - fail");
		Event ovverlapedSceEventNewFile3 = new Event(new Date(), new Date(), startOverlapped, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile3 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile3, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile3);

		String timeInString7 = "09:50:00";
		Date startOverlapped2 = null;
		try {
			startOverlapped2 = time.parse(timeInString7);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 09:50 - 10:20 - fail
		logger.info("Overlapped - 09:50 - 10:20 - fail");
		Event ovverlapedSceEventNewFile5 = new Event(new Date(), new Date(), startOverlapped2, endOverlapped, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile5 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile5, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile5);

		String timeInString8 = "00:40:00";
		Date endOverlapped2 = null;
		try {
			endOverlapped2 = time.parse(timeInString8);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		// 09:50 - 10:30 - fail
		logger.info("Overlapped - 09:50 - 10:30 - fail");
		Event ovverlapedSceEventNewFile6 = new Event(new Date(), new Date(), startOverlapped2, endOverlapped2, 1, 2, len, "event", EventType.SCHEDULED);
		ovverlapedSceEventNewFile6 = GeneratorAPI.createEvent(ovverlapedSceEventNewFile6, newSchedulerInfo);
		System.out.println("reject!!!");
		assertNull(ovverlapedSceEventNewFile6);

		// 09:50 - 09:55 - success!
		logger.info("Create event - not overlapped - 09:50 - 09:55 - success!");
		Event correctSceEventNewFile2 = new Event(new Date(), new Date(), startOverlapped2, len, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEventNewFile2 = GeneratorAPI.createEvent(correctSceEventNewFile2, newSchedulerInfo);
		System.out.println("created first event" + correctSceEventNewFile2.getID() + "!!!");
		assertNotNull(correctSceEventNewFile2);
	}

	@Test
	public void testModify() {
		logger.info("\n\nModify tests");
		String timeInString1 = "11:00:00";
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
		logger.info("Create successful event");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("mFile", "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event1 " + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);	
		String oldKey = APIHelper.generateKey(correctSceEvent.getWindow(), correctSceEvent.getTime(), correctSceEvent.getEventType());
		assertTrue("The key exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(oldKey));

		// Modify the key properties
		logger.info("Modify the key properties");
		Event modifiedSceEvent = new Event(new Date(), new Date(), start, dur, 2, 1, len, "event", EventType.SCHEDULED);
		modifiedSceEvent = GeneratorAPI.modifyEvent(correctSceEvent.getID(), schedulerInfo.getSchInfoName(), modifiedSceEvent, schedulerInfo);
		System.out.println("modified event " + modifiedSceEvent.getID() + "!!!");
		assertNotNull(modifiedSceEvent);	
		assertFalse("The old key not exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(oldKey));
		String newKey = APIHelper.generateKey(modifiedSceEvent.getWindow(), modifiedSceEvent.getTime(), modifiedSceEvent.getEventType());
		assertTrue("The new key exist on keys set", manager.getFilesList().get("mFile").getEventKeys().contains(newKey));

		// Modify the file
		logger.info("Modify the file");
		Event modifiedSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		SchedulerInfo modifySchedulerInfo = new SchDay("mFile2", "date2", "zone2", "channel2");
		modifiedSceEvent2 = GeneratorAPI.modifyEvent(correctSceEvent.getID(), schedulerInfo.getSchInfoName(), modifiedSceEvent2, modifySchedulerInfo);
		System.out.println("modified event " + modifiedSceEvent.getID() + "!!!");
		assertNotNull(modifiedSceEvent);
		assertFalse("The old file not contains the event", manager.getFilesList().get("mFile").getEventMap().containsKey(correctSceEvent.getID()));
		assertTrue("The new file contains the event", manager.getFilesList().get("mFile2").getEventMap().containsKey(modifiedSceEvent2.getID()));
	}

	@Test
	public void testDelte() {
		logger.info("\n\nDelete tests");
		String timeInString1 = "11:00:00";
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
		logger.info("Create successful event");
		Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		SchedulerInfo schedulerInfo = new SchDay("file", "date", "zone", "channel");
		correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
		System.out.println("created first event1 " + correctSceEvent.getID() + "!!!");
		assertNotNull(correctSceEvent);	

		// try create duplicate
		logger.info("try create duplicate");
		Event failSceEvent1 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		failSceEvent1 = GeneratorAPI.createEvent(failSceEvent1, schedulerInfo);
		System.out.println("reject!!!");
		assertNull(failSceEvent1);

		logger.info("Create second successful event");
		Event correctSceEvent2 = new Event(new Date(), new Date(), start, dur, 1, 2, len, "event", EventType.SCHEDULED);
		correctSceEvent2 = GeneratorAPI.createEvent(correctSceEvent2, schedulerInfo);
		System.out.println("created first event2 " + correctSceEvent2.getID() + "!!!");
		assertNotNull(correctSceEvent2);

		// try delete not exist id
		logger.info("try delete not exist id");
		assertFalse(GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), Manager.getUUID()));

		// delete him
		logger.info("delete first event");
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent.getID());

		// check if the id not exist
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()).getEventMap().get(correctSceEvent.getID()));

		// delete second
		logger.info("delete second event");
		GeneratorAPI.deleteEvent(schedulerInfo.getSchInfoName(), correctSceEvent2.getID());

		// check if the file deleted
		assertNull(Manager.getInstance().getFilesList().get(schedulerInfo.getSchInfoName()));

		// try to create again (if not deleted successfully - duplicate)
		logger.info("try to create again and check that the event deleted (if not deleted successfully it will be duplicate)");
		Event correctSceEvent3 = new Event(new Date(), new Date(), start, dur, 1, 1, len, "event", EventType.SCHEDULED);
		correctSceEvent3 = GeneratorAPI.createEvent(correctSceEvent3, schedulerInfo);
		System.out.println("created event" + correctSceEvent3.getID() + "!!!");
		assertNotNull(correctSceEvent3);
	}

	@Test
	public void testAll() {
		logger.info("\n\n\nTest all - create 1440 successful events");
		
		String timeInString1 = "00:00:00";
		Date start = null;
		try {
			start = time.parse(timeInString1);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		String timeInString2 = "01:00:00";
		Date dur = null;
		try {
			dur = time.parse(timeInString2);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String timeInString3 = "00:01:00";
		Date len = null;
		try {
			len = time.parse(timeInString3);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SchedulerInfo schedulerInfo = new SchDay("B1801001", "date", "zone", "channel");

		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 60; j++) {
				Event correctSceEvent = new Event(new Date(), new Date(), start, dur, 1, j, len, "event123456", EventType.SCHEDULED);
				correctSceEvent = GeneratorAPI.createEvent(correctSceEvent, schedulerInfo);
			}
			start = DateUtils.sumDates(start, dur, 1);
		}

		System.out.println("start se" + System.nanoTime());
		GeneratorAPI.serializer(SchedulerInfoType.CCMS);
		System.out.println("finish se" + System.nanoTime());
		BufferedReader br = null;
		String beforeDe = "";
		String afterDe = "";
		String sCurrentLine;

		try {
			br = new BufferedReader(new FileReader("C:\\CCMS\\CCMS\\B1801001.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				beforeDe.concat(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		System.out.println("start de" + System.nanoTime());
		GeneratorAPI.deserializer(SchedulerInfoType.CCMS);//deserilaize by type
		System.out.println("finish de" + System.nanoTime());
		System.out.println("start se" + System.nanoTime());
		GeneratorAPI.serializer(SchedulerInfoType.CCMS);
		System.out.println("finish se" + System.nanoTime());
		try {
			br = new BufferedReader(new FileReader("C:\\CCMS\\CCMS\\B1801001.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				afterDe.concat(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// check if the files are equals
		assertTrue(beforeDe.equals(afterDe));
	}
}