package Model;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public interface IProperties {

//	int minBrk = 1;
//	int maxBrk = 999;
//	int minPos = 1;
//	int maxPos = 999;
	
	DateFormats getDateFormat();
	
	DateFormats getTimeFormat();
	
	DateFormats getStartFormat();
	
	DateFormats getDurationFormat();
	
	DateFormats getLengthFormat();
	
	boolean assertDateDigitsLength(String value);
	
	boolean assertTimeDigitsLength(String value);
	
	boolean assertStartDigitsLength(String value);
	
	boolean assertDurationDigitsLength(String value);
	
	boolean assertBrkDigitsLength(String value);
	
	boolean assertPosDigitsLength(String value);
	
	boolean assertLengthDigitsLength(String value);
	
	boolean assertActualTimeDigitsLength(String value);
	
	boolean assertActualLengthDigitsLength(String value);
	
	boolean assertActualPosDigitsLength(String value);
	
	boolean assertAdNameDigitsLength(String value);
	
	boolean assertStatusCodeDigitsLength(String value);
	
	boolean assertStatAdvertiserNameDigitsLength(String value);
	
	boolean assertStatAdvertiserSpotNameDigitsLength(String value);
	
	boolean assertEventTypeDigitsLength(String value);
	
	boolean assertSchedulerName(String schName);
	
}
