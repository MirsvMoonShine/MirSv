package com.mirsv.util;

public class TimeUtil {

	public static String toString(long seconds) {
		long hour = seconds / 3600;
		seconds -= hour * 3600;
		long minute = seconds / 60;
		seconds -= minute * 60;
		return (hour != 0 ? hour + "시간 " : "") + (minute != 0 ? minute + "분 " : "") + (seconds >= 0 ? seconds + "초" : "");
	}

	public static String parseTicks(int ticks) {
		ticks /= 20;
		int seconds = ticks % 60;
		int minutes = (ticks / 60) % 60;
		int hours = (ticks / 3600) % 24;
		int days = ticks / 86400;
		return days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";
	}

}
