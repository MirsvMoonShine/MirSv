package com.mirsv.util;

public class TimeUtil {

	public static String toString(long seconds) {
		long hour = seconds / 3600;
		seconds -= hour * 3600;
		long minute = seconds / 60;
		seconds -= minute * 60;
		return (hour != 0 ? hour + "시간 " : "") + (minute != 0 ? minute + "분 " : "") + (seconds >= 0 ? seconds + "초" : "");
	}

}
