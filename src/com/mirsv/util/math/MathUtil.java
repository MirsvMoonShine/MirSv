package com.mirsv.util.math;

import com.google.common.base.Preconditions;

import java.util.Random;

public class MathUtil {

	private static final Random random = new Random();

	public static int randomBetween(int min, int max) {
		Preconditions.checkArgument(min < max);
		return random.nextInt(max - min) + min;
	}

}
