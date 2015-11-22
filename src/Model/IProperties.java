package Model;

/**
 * IProperties.java Interface
 * Declare required functions for ValidateAndConvertUtils class,
 * Each schedulerInfoType implements this interface and overrides the functions with its certain implementation.
 * In ValidateAndConvertUtils class we use an IProperties object, and set its type only single time to the specific schedulerInfoType. 
 *
 * Example of functions:
 * get format of each date object.
 * assert digits length in scheduler for each field.
 * assert scheduler info name.
 * 
 * 
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

	boolean assertBrkDigitsLength(String value);

	boolean assertPosDigitsLength(String value);

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
