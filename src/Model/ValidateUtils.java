package Model;

import java.util.Date;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public final class ValidateUtils {


	private static IProperties iproperties;//maybe static problem to check

	public static void setIProperties(SchedulerInfoType schInfoType){
		iproperties = getPropertiesClass(schInfoType);
	}

	public static Date getDate(String date) {
		Date d = DateUtils.getFormattedDate(iproperties.getDateFormat(),date);
		if(iproperties.assertDateDigitsLength(date) &&  d!=null)
			return d;
		else
			return null;
	}

	public static Date getTime(String time) {
		Date d = DateUtils.getFormattedDate(iproperties.getTimeFormat(),time);
		if(iproperties.assertTimeDigitsLength(time) &&  d!=null)
			return d;
		else
			return null;
	}


	public static Date getStart(String start) {
		Date d = DateUtils.getFormattedDate(iproperties.getStartFormat(),start);
		if(iproperties.assertStartDigitsLength(start) &&  d!=null)
			return d;
		else
			return null;
	}

	public static Date getDuration(String duration) {
		Date d = DateUtils.getFormattedDate(iproperties.getDurationFormat(),duration);
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
		Date d = DateUtils.getFormattedDate(iproperties.getLengthFormat(),length);
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

	public static boolean isValidSchedulerName(String schName){
		return iproperties.assertSchedulerName(schName);
	}

	public static boolean notNull(Object... args) {
		for (Object arg : args) {
			if (arg==null) return false;
		}
		return true;
	}

	public static String getStringDate(Date date){
		return DateUtils.convertDateToString(iproperties.getDateFormat(), date);
	}

	public static String getStringTime(Date date){
		return DateUtils.convertDateToString(iproperties.getTimeFormat(), date);
	}

	public static String getStringStart(Date date){
		return DateUtils.convertDateToString(iproperties.getStartFormat(), date);
	}

	public static String getStringDuration(Date date){
		return DateUtils.convertDateToString(iproperties.getDurationFormat(), date);
	}

	public static String getStringLength(Date date){
		return DateUtils.convertDateToString(iproperties.getLengthFormat(), date);
	}

	public static String completeIntToString(int num,int expectedDigits){
		String sNum = Integer.toString(num);
		if (sNum.length()< expectedDigits)
			sNum  = completeDigits(expectedDigits - sNum.length()) + sNum;
		return sNum;
	}
	
//	public static String getStringPos(int i){
//		String a = Integer.toString(i);
//		if (a.length()< IProperties.posDigits)
//			a  = completeDigits(IProperties.posDigits-a.length()) + a;
//		return a;
//	}

	public static String completeDigits(int missDigits){
		String[] array0 = {"","0","00","000","0000","00000","000000","0000000","00000000"};
		return array0[missDigits];
		//   while(myNumber>0){
		//		    System.out.print("0");
		//		    myNumber--;
		//   }

	}

	//	public static String getConvertString(String value, Date date){
	//		DateFormats df;
	//		switch (value) {
	//		case "date":
	//			df = iproperties.getDateFormat();
	//			break;
	//
	//		default:
	//			df = iproperties.getDurationFormat();
	//			break;
	//		}
	//		return DateUtils.convertDateToString(df, date);
	//	}
	//	

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


}