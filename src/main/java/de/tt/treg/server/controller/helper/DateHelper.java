package de.tt.treg.server.controller.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static int getYearFromDate(Date date) {
		return Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
	}

	public static String getYearFromDateAsString(Date birthDate) {
		return String.valueOf(getYearFromDate(birthDate));
	}

	public static String getYearMonthAndDayFromDateAsString(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date).toString();
	}

	public static Date getDateFromYear(int year) {
		try {
			return new SimpleDateFormat("yyyy").parse(String.valueOf(year));
		} catch (ParseException e) {

		}
		return null;
	}

	public static String getDayAndDate(Date date) {
		return new SimpleDateFormat("EEE dd.MM").format(date);
	}

	public static String getDateAndTime(Date date) {
		return new SimpleDateFormat("EEE dd.MM.yyyy HH:mm").format(date) + " Uhr";
	}
}
