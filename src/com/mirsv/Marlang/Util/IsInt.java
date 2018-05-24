package com.mirsv.Marlang.Util;

public class IsInt {
	
	public static boolean isInt(String s) {
		boolean isint = true;
		try{
			Integer.parseInt(s);
		}catch(Exception e){
			isint = false;
		}
		return isint;
	}
	
}
