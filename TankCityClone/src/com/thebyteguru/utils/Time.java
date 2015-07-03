package com.thebyteguru.utils;

public class Time {

	public static final float SECOND = 1000000000.0f;

	public static float get() {
		return System.nanoTime();
	}

}
