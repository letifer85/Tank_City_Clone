package com.thebyteguru.game;

import java.awt.Graphics2D;
import java.util.concurrent.Callable;
import com.thebyteguru.display.Display;
import com.thebyteguru.display.DisplayParams;
import com.thebyteguru.utils.Time;

public class Game implements Runnable, Callable<Void> {

	public static final int    UPDATE_RATE     = 60;
	public static final float  UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
	public static final long   IDLE_TIME       = 1;
	public static final String TITLE           = "Game";

	private Thread             gameThread;
	private boolean            running;
	private Callable<Void>     onCloseRutine;
	private Graphics2D         displayGraphics;

	public Game() {
		DisplayParams dp = DisplayParams.build()
		        .withTitle(TITLE)
		        .withCustomOnCloseOperation(this);
		Display.create(dp);

		displayGraphics = Display.getScreenGraphics();
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cleanUp();
	}

	@Override
	public Void call() throws Exception {
		stop();
		return null;
	}

	public void update(float delta) {

	}

	public void render() {
		Display.clear();

		Display.swapBuffers();
	}

	public void run() {
		int fps = 0;
		int upd = 0;
		int loopedUpd = 0;
		float count = 0;

		boolean render = false;

		float unprocessedTime = 0;
		float previousTime = Time.get();
		while (running) {
			float currentTime = Time.get();
			float elapsedTime = currentTime - previousTime;
			previousTime = currentTime;

			unprocessedTime += elapsedTime;
			count += elapsedTime;

			while (unprocessedTime > UPDATE_INTERVAL) {
				update(1);
				upd++;
				unprocessedTime -= UPDATE_INTERVAL;

				if (render)
					loopedUpd++;

				render = true;
			}
			if (render) {
				render();
				fps++;
				render = false;
			} else {
				try {
					Thread.sleep(IDLE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (count >= Time.SECOND) {
				Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | lupd: " + loopedUpd);
				fps = 0;
				upd = 0;
				loopedUpd = 0;
				count = 0;
			}

		}

	}

	private void cleanUp() {
		Display.dispose();
	}

}
