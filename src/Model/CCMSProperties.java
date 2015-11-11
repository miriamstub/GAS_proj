package Model;

/**
 * @author bfeldman 
 * Nov 10, 2015
 */
public class CCMSProperties implements IProperties {

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
		return value.length() == 3;
	}

	@Override
	public boolean assertPosDigitsLength(String value) {
		return value.length() == 3;
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

}