package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public final class ValidateUtils {


	private static IProperties iproperties;

	public static void setIProperties(SchedulerInfoType schInfoType){
		iproperties = getPropertiesClass(schInfoType);
	}

	public static Date getDate(String date) {
		Date d = getFormattedDate(iproperties.getDateFormat(),date);
		if(iproperties.assertDateDigitsLength(date) &&  d!=null)
			return d;
		else
			return null;
	}

	public static Date getTime(String time) {
		Date d = getFormattedDate(iproperties.getTimeFormat(),time);
		if(iproperties.assertTimeDigitsLength(time) &&  d!=null)
			return d;
		else
			return null;
	}


	public static Date getStart(String start) {
		Date d = getFormattedDate(iproperties.getStartFormat(),start);
		if(iproperties.assertStartDigitsLength(start) &&  d!=null)
			return d;
		else
			return null;
	}

	public static Date getDuration(String duration) {
		Date d = getFormattedDate(iproperties.getDurationFormat(),duration);
		if(iproperties.assertDurationDigitsLength(duration) &&  d!=null)
			return d;
		else
			return null;
	}

	public static Integer getBrk(String brk) {//TODO to validate what happen when convert to int is failed e.g. try to convert "!01"
		if(iproperties.assertBrkDigitsLength(brk))
			return Integer.parseInt(brk);
		else
			return null;
	}

	public static Integer getPos(String pos) {
		if(iproperties.assertPosDigitsLength(pos))
			return Integer.parseInt(pos);
		else
			return null;
	}

	public static Date getLength(String length) {
		Date d = getFormattedDate(iproperties.getLengthFormat(),length);
		if(iproperties.assertLengthDigitsLength(length) &&  d!=null)
			return d;
		else
			return null;
	}

	public static String getAdName(String adName) {
		if(iproperties.assertAdNameDigitsLength(adName))
			return adName;
		else
			return null;
	}

	public static EventType getEventType(String eventType) {//maybe to check if value exist in enum
		if(iproperties.assertEventTypeDigitsLength(eventType))
			return EventType.valueOf(EventType.getNameByValue(eventType));
		else
			return null;
	}
	
	public static boolean isValidStatusCode(String statusCode) {
		return iproperties.assertStatusCodeDigitsLength(statusCode);
	}

	public static boolean isValidActualTime(String actualTime) {
		return iproperties.assertActualTimeDigitsLength(actualTime) ;
	}

	public static boolean isValidActualLength(String actualLength) {
		return iproperties.assertActualLengthDigitsLength(actualLength) ;
	}

	public static boolean isValidActualPos(String actualPos) {
		return iproperties.assertActualPosDigitsLength(actualPos);
	}


	//TODO schDay date
	//	public static boolean isValidFileDate(String date) {
	//			return /*ModelUtils.*/isValidFormat(DateFormats.Mdd,date);//the month in letter!!!!!!!!!!SOS
	//	}


	public static boolean notNull(Object... args) {
		for (Object arg : args) {
			if (arg==null) return false;
		}
		return true;
	}


	public static Date getFormattedDate(DateFormats format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format.toString());//specific format
			date = sdf.parse(value);//convert string to date in the ditto format
			if (!value.equals(sdf.format(date))) {//convert date to string in the ditto format and compare if we got the same string as we got as parameter.
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		System.out.println(date != null?"VV":"XX");
		return date;
	}


	public static IProperties getPropertiesClass(SchedulerInfoType schInfoType){
		IProperties propertiesClass = null;
		switch (schInfoType) {
		case CCMS:
			propertiesClass = new CCMSProperties();
			break;

		case SCTE118:
			propertiesClass = new CCMSProperties();
			break;

		default:
			break;
		}
		return propertiesClass;
	}


	//	public static Date getFormattedDate(DateFormats format, String value) {
	//		Date date = null;
	//		try {
	//			date = new SimpleDateFormat(format.toString()).parse(value);
	//		} catch (ParseException ex) {
	//			ex.printStackTrace();
	//		}
	//		return date;
	//	}


	//	public static boolean isValidFormat(DateFormats format, String value) {
	//		Date date = null;
	//		try {
	//			SimpleDateFormat sdf = new SimpleDateFormat(format.toString());//specific format
	//			date = sdf.parse(value);//convert string to date in the ditto format
	//			if (!value.equals(sdf.format(date))) {//convert date to string in the ditto format and compare if we got the same string as we got as parameter.
	//				date = null;
	//			}
	//		} catch (ParseException ex) {
	//			ex.printStackTrace();
	//		}
	//		System.out.println(date != null?"VV":"XX");
	//		return date != null;
	//	}

}