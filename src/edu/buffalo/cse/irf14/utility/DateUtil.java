package edu.buffalo.cse.irf14.utility;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public static Calendar getCalendar()
	{
		Calendar calendar = new GregorianCalendar();
		
		calendar.set(1900,Calendar.JANUARY, 01,  00, 00, 00);
		
		return calendar;
	}
	
	
}
