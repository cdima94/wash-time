package com.android.washtime.utils;

public class DateParsing {

	public static String formatDate(String date) {
		String[] splitDate = date.split("-");
		return splitDate[0] + "-" +  splitDate[1] + "-" + Integer.valueOf(splitDate[2]);
	}
}
