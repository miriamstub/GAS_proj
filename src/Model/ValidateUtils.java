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
	
	public static void setIProperties(ProtocolType protocolType){
		iproperties = getPropertiesClass(protocolType);
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
	
	public static Integer getBrk(String brk) {//to validate what happen when convert to int is failed e.g. try to convert "!01"
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
	
	public static String getStat(String stat) {
		if( iproperties.assertStatusCodeDigitsLength(stat))
			return stat;
		else
			return null;
		//return iproperties.getStatAdvertiserNameDigitsLength() +  iproperties.getStatAdvertiserSpotNameDigitsLength() + iproperties.getStatusCodeDigitsLength() == stat.length();
	}
	
	public static EventType getEventType(String eventType) {//maybe to check if value exist in enum
		if(iproperties.assertEventTypeDigitsLength(eventType))
			return EventType.valueOf(EventType.getNameByValue(eventType));
		else
			return null;
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
	

	
	
	
	//file date
	public static boolean isValidFileDate(String date) {
			return /*ModelUtils.*/isValidFormat(DateFormats.Mdd,date);//the month in letter!!!!!!!!!!SOS
	}
	
//	public EventType getEventType(String eventType) {
//		return EventType.valueOf(EventType.getNameByValue(eventType));
//	}
//	
//	public Date getStart(String start) {
//		return ModelUtils.getFormattedDate(iproperties.getStartFormat(),start);
//	}
//	
//	public Date getDuration(String duration) {
//		return ModelUtils.getFormattedDate(iproperties.getDurationFormat(),duration);
//	}
//	
//	public int getBrk(String brk) {
//		return Integer.parseInt(brk);
//	}
//	
//	public int getPos(String pos) {
//		return Integer.parseInt(pos);
//	}
//	
//	public Date getLength(String length) {
//		return ModelUtils.getFormattedDate(iproperties.getLengthFormat(),length);
//	}
//	
//	//file date
//	public Date getFileDate(String date) {
//			return ModelUtils.getFormattedDate(DateFormats.Mdd,date);//the month in letter!!!!!!!!!!SOS
//	}
	
	
	public static boolean isValidFormat(DateFormats format, String value) {
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
		return date != null;
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
	
	
//	public static Date getFormattedDate(DateFormats format, String value) {
//		Date date = null;
//		try {
//			date = new SimpleDateFormat(format.toString()).parse(value);
//		} catch (ParseException ex) {
//			ex.printStackTrace();
//		}
//		return date;
//	}
	
	
	public static IProperties getPropertiesClass(ProtocolType protocolType){
		IProperties propertiesClass = null;
		switch (protocolType) {
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

}

//----------------------------------------------------------------------------------------

///*package ModelGAS;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public final class ValidateUtils {
//	
//	
//	private static IProperties iproperties;
//	
//	public static void setIProperties(ProtocolType protocolType){
//		iproperties = getPropertiesClass(protocolType);//ModelUtils.getPropertiesClass(protocolType);
//	}
//	
//	public static Integer isValidDate1(String date) {
//		//if( iproperties.assertDateDigitsLength(date) &&  /*ModelUtils.*/isValidFormat(iproperties.getDateFormat(),date))
//			//return getFormattedDate(iproperties.getDateFormat(),date);
//		//else
//		Integer i = 1;
//		string s;
//		if(i<=3 && i>=4)
//			return null;
//	}
//	
//	public static Date isValidDate(String date) {
//		Date d = getFormattedDate(iproperties.getDateFormat(),date);
//		if( iproperties.assertDateDigitsLength(date) &&  d!=null)
//			return d;
//	}
//	
//	public static boolean isValidTime(String time) {
//		return iproperties.getTimeDigitsLength() == time.length() &&  /*ModelUtils.*/isValidFormat(iproperties.getTimeFormat(),time);
//	}
//	
//	
//	public static boolean isValidStart(String start) {
//		return iproperties.getStartDigitsLength() == start.length() && /*ModelUtils.*/isValidFormat(iproperties.getStartFormat(),start) ;
//	}
//	
//	public static boolean isValidDuration(String duration) {
//		return iproperties.getDurationDigitsLength() == duration.length() && /*ModelUtils.*/isValidFormat(iproperties.getDurationFormat(),duration);
//	}
//	
//	public static boolean isValidBrk(String brk) {
//		return iproperties.getBrkDigitsLength() == brk.length();
//	}
//	
//	public static boolean isValidPos(String pos) {
//		return iproperties.getPosDigitsLength() == pos.length();
//	}
//	
//	public static boolean isValidLength(String length) {
//		return iproperties.getLengthDigitsLength() == length.length() && /*ModelUtils.*/isValidFormat(iproperties.getLengthFormat(), length);
//	}
//	
//	public static boolean isValidAdName(String adName) {
//		return iproperties.getAdNameDigitsLength() == adName.length();
//	}
//	
//	public static boolean isValidStat(String stat) {
//		return iproperties.getStatAdvertiserNameDigitsLength() +  iproperties.getStatAdvertiserSpotNameDigitsLength() + iproperties.getStatusCodeDigitsLength() == stat.length();
//	}
//	
//	public static boolean isValidEventType(String eventType) {//maybe to check if value exist in enum
//		return iproperties.getEventTypeDigitsLength() == eventType.length();
//	}
//	
//	public static boolean isValidActualTime(String actualTime) {
//		return iproperties.getActualTimeDigitsLength() == actualTime.length();
//	}
//	
//	public static boolean isValidActualLength(String actualLength) {
//		return iproperties.getActualLengthDigitsLength() == actualLength.length();
//	}
//	
//	public static boolean isValidActualPos(String actualPos) {
//		return iproperties.getActualPosDigitsLength() == actualPos.length();
//	}
//	
//	public boolean assertDigits(,val){
//		iproperties.getActualPosDigitsLength() == val.length();
//	}
//	
//	
//	public Date getStart(String start) {
//		
//
//		return getFormattedDate(iproperties.getStartFormat(),start);
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	//file date
//	public static boolean isValidFileDate(String date) {
//			return /*ModelUtils.*/isValidFormat(DateFormats.Mdd,date);//the month in letter!!!!!!!!!!SOS
//	}
//	
////	public EventType getEventType(String eventType) {
////		return EventType.valueOf(EventType.getNameByValue(eventType));
////	}
////	
////	public Date getStart(String start) {
////		return ModelUtils.getFormattedDate(iproperties.getStartFormat(),start);
////	}
////	
////	public Date getDuration(String duration) {
////		return ModelUtils.getFormattedDate(iproperties.getDurationFormat(),duration);
////	}
////	
////	public int getBrk(String brk) {
////		return Integer.parseInt(brk);
////	}
////	
////	public int getPos(String pos) {
////		return Integer.parseInt(pos);
////	}
////	
////	public Date getLength(String length) {
////		return ModelUtils.getFormattedDate(iproperties.getLengthFormat(),length);
////	}
////	
////	//file date
////	public Date getFileDate(String date) {
////			return ModelUtils.getFormattedDate(DateFormats.Mdd,date);//the month in letter!!!!!!!!!!SOS
////	}
//	
//	
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
//
////	public static Date getFormattedDate(DateFormats format, String value) {
////		Date date = null;
////		try {
////			SimpleDateFormat sdf = new SimpleDateFormat(format.toString());//specific format
////			date = sdf.parse(value);//convert string to date in the ditto format
////			if (!value.equals(sdf.format(date))) {//convert date to string in the ditto format and compare if we got the same string as we got as parameter.
////				date = null;
////			}
////		} catch (ParseException ex) {
////			ex.printStackTrace();
////		}
////		System.out.println(date != null?"VV":"XX");
////		return date;
////	}
//	
//	
//	public static Date getFormattedDate(DateFormats format, String value) {
//		Date date = null;
//		try {
//			date = new SimpleDateFormat(format.toString()).parse(value);
//		} catch (ParseException ex) {
//			ex.printStackTrace();
//		}
//		return date;
//	}
//	
//	
//	public static IProperties getPropertiesClass(ProtocolType protocolType){
//		IProperties propertiesClass = null;
//		switch (protocolType) {
//		case CCMS:
//			propertiesClass = new CCMSProperties();
//			break;
//
//		case SCTE118:
//			propertiesClass = new CCMSProperties();
//			break;
//
//		default:
//			break;
//		}
//		return propertiesClass;
//	}
//
//}*/