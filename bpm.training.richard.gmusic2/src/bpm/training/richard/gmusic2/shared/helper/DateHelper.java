package bpm.training.richard.gmusic2.shared.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public DateHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		return sdf.format(date);
	}
	
	public static Date formatDate(String dateString , String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format); 
		Date date = (Date)formatter.parse(dateString);
		return date;
	}
	
}
