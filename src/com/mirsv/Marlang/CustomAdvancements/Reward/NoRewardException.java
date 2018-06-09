package com.mirsv.Marlang.CustomAdvancements.Reward;

import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;

public class NoRewardException extends Exception{
	private static final long serialVersionUID = 1L;

	public NoRewardException() {}
	
	public NoRewardException(String s) {
		super(s);
	}
	
}
