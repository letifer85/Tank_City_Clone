package com.thebyteguru.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.JFrame;

public abstract class Display {

	public static final DisplayParams DEFAULT_PARAMS = DisplayParams.build();

	private static boolean            created        = false;
	private static JFrame             window;
	private static Canvas             content;
	private static BufferedImage      screenBuffer;
	private static int[]              screenBufferData;
	private static BufferStrategy     bufferStrategy;
	private static Graphics2D         screenGraphics;
	private static int                clearColor;
	private static Dimension          size;

	public static void create(DisplayParams dp) {
		if (created)
			throw new IllegalStateException("Disply is already created. Please dispose of the current window prior to creating a new one.");

		window = new JFrame(dp.title);
		window.setUndecorated(dp.decorated);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					dp.onCloseRutine.call();
				} catch (Exception e1) {
					System.err.println("An error occured during 'on close rutine'.");
					e1.printStackTrace();
					System.exit(1);
				}
			}
		});
		window.setResizable(dp.resizable);
		size = new Dimension(dp.width, dp.height);
		content = new Canvas();
		content.setPreferredSize(size);
		window.getContentPane().add(content);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		screenBuffer = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_ARGB);
		screenBufferData = ((DataBufferInt) screenBuffer.getRaster().getDataBuffer()).getData();
		content.createBufferStrategy(dp.numBuffers);
		bufferStrategy = content.getBufferStrategy();
		screenGraphics = (Graphics2D) screenBuffer.getGraphics();
		clearColor = dp.clearColor;

		if (dp.withAntialiasing) {
			if ((dp.antialiasing & DisplayParams.ANTIALIAS) > 0) {
				screenGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			if ((dp.antialiasing & DisplayParams.TEXT_ANTIALIAS) > 0) {
				screenGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		}

		created = true;
	}

	public static void clear() {
		Arrays.fill(screenBufferData, clearColor);
	}

	public static void swapBuffers() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.drawImage(screenBuffer, 0, 0, null);
		bufferStrategy.show();
	}

	public static int[] getScreenBufferData() {
		return screenBufferData;
	}

	public static Graphics2D getScreenGraphics() {
		return screenGraphics;
	}

	public static void dispose() {
		window.dispose();
		created = false;
	}

}
