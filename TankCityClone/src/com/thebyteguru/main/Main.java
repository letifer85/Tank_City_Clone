package com.thebyteguru.main;

import com.thebyteguru.display.Display;

public class Main {

	public static void main(String[] args) {

		Display.create(Display.DEFAULT_PARAMS);

		while (true) {
			Display.clear();
			Display.swapBuffers();
		}
	}

}
