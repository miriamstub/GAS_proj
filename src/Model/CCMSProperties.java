package Model;

import java.util.Date;

/**
 * CCMSProperties.java class
 * Implements IProperties interface
 * 
 * 
 * @author bfeldman 
 * Nov 10, 2015
 */

public class CCMSProperties implements IProperties {

	public final int brkDigits = 3;
	public final int posDigits = 3;
	
	@Override
	public DateFormats getDateFormat() {
		return DateFormats.MMdd;
	}

	@Override
	public DateFormats getTimeFormat() {
		return DateFormats.HHmmss;
	}

	@Override
	public DateFormats getStartFormat() {
		return DateFormats.HHmm;
	}

	@Override
	public DateFormats getDurationFormat() {
		return DateFormats.HHmm;
	}

	@Override
	public DateFormats getLengthFormat() {
		return DateFormats.HHmmss;
	}

	public int getDigitsLength() {
		return 0;
	}

	@Override
	public boolean assertDateDigitsLength(String value) {
		return value.length() == 4;
	}

	@Override
	public boolean assertTimeDigitsLength(String value) {
		return value.length() == 6;
	}

	@Override
	public boolean assertStartDigitsLength(String value) {
		return value.length() == 4;
	}

	@Override
	public boolean assertDurationDigitsLength(String value) {
		return value.length() == 4;
	}

	@Override
	public boolean assertBrkDigitsLength(String value) {
		return value.length() == brkDigits;
	}

	@Override
	public boolean assertPosDigitsLength(String value) {
		return value.length() == posDigits;
	}

	@Override
	public boolean assertLengthDigitsLength(String value) {
		return value.length() == 6;
	}

	@Override
	public boolean assertActualTimeDigitsLength(String value) {
		return value.length() == 6;
	}

	@Override
	public boolean assertActualLengthDigitsLength(String value) {
		return value.length() == 8;
	}

	@Override
	public boolean assertActualPosDigitsLength(String value) {
		return value.length() == 3;
	}

	@Override
	public boolean assertAdNameDigitsLength(String value) {
		return value.length() == 11;
	}

	@Override
	public boolean assertStatusCodeDigitsLength(String value) {
		return value.length() == 4;
	}

	@Override
	public boolean assertStatAdvertiserNameDigitsLength(String value) {
		return value.length() == 0;
	}

	@Override
	public boolean assertStatAdvertiserSpotNameDigitsLength(String value) {
		return value.length() == 0;
	}

	@Override
	public boolean assertEventTypeDigitsLength(String value) {
		return value.length() == 4;
	}

	/* (non-Javadoc)
	 * @see Model.IProperties#assertSchedulerName(java.lang.String)
	 */
	@Override
	public boolean assertSchedulerName(String schName) {
		String schInfoNameRgx = "[1-9 A-C][0-3][0-9][0-9 A-Z]{5}";
		String date = schName.substring(0, 3);
		int month = Integer.parseInt(date.substring(0, 1), 16);
		Date formattedDate = DateUtils.getFormattedDate(DateFormats.MMdd, (month<10 ? "0" : "") + month + date.substring(1, 3));		
		return schName.matches(schInfoNameRgx) && formattedDate!=null;
}


}
