/**
 * 
 */
package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils.java class
 * Contains functions that deal with Date objects.
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class DateUtils {
	
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
		return date;
	}

	public static Date sumDates(Date date1, Date date2, int type) {
		Calendar dur = Calendar.getInstance();
		dur.setTime(date2);
		int hour = dur.get(Calendar.HOUR_OF_DAY);
		int min = dur.get(Calendar.MINUTE);
		int sec = dur.get(Calendar.SECOND);
		int milli = dur.get(Calendar.MILLISECOND);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		cal.add(Calendar.HOUR, hour * type);
		cal.add(Calendar.MINUTE, min * type);
		cal.add(Calendar.SECOND, sec * type);
		cal.add(Calendar.MILLISECOND, milli * type);

		return cal.getTime();
	}	
	
	public static String convertDateToString(DateFormats format,Date date){//what happen if we get incorrect format? 
		return new SimpleDateFormat(format.toString()).format(date);
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
