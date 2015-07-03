package com.thebyteguru.display;

import java.util.concurrent.Callable;

public class DisplayParams {

	public static final byte           ANTIALIAS                  = 0b00000001;
	public static final byte           TEXT_ANTIALIAS             = 0b00000010;

	public static final String         DEFAULT_TITLE              = "title";
	public static final int            DEFAULT_WIDTH              = 800;
	public static final int            DEFAULT_HEIGHT             = 600;
	public static final boolean        RESIZABLE                  = false;
	public static final int            NUM_BUFFERS                = 1;
	public static final boolean        DEFAULT_ANTIALIASING       = false;
	public static final byte           DEFAULT_ANTIALIASING_VALUE = 0;
	public static final int            DEFAULT_CLEAR_COLOR        = 0xff000000;
	public static final boolean        DECORATED                  = false;
	public static final Callable<Void> DEFAULT_ON_CLOSE_RUTINE    = new Callable<Void>() {

		                                                              @Override
		                                                              public Void call() throws Exception {
			                                                              return null;
		                                                              }

	                                                              };

	protected String                   title;
	protected int                      width;
	protected int                      height;
	protected boolean                  resizable;
	protected Callable<Void>           onCloseRutine;
	protected int                      numBuffers;
	protected boolean                  withAntialiasing;
	protected byte                     antialiasing;
	protected int                      clearColor;
	protected boolean                  decorated;

	private DisplayParams() {
		this.title = DEFAULT_TITLE;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.resizable = RESIZABLE;
		this.onCloseRutine = DEFAULT_ON_CLOSE_RUTINE;
		this.numBuffers = NUM_BUFFERS;
		this.withAntialiasing = DEFAULT_ANTIALIASING;
		this.antialiasing = DEFAULT_ANTIALIASING_VALUE;
		this.clearColor = DEFAULT_CLEAR_COLOR;
		this.decorated = DECORATED;
	}

	public static DisplayParams build() {
		return new DisplayParams();
	}

	public DisplayParams withTitle(String title) {
		this.title = title;
		return this;
	}

	public DisplayParams withWidth(int width) {
		this.width = width;
		return this;
	}

	public DisplayParams withHeight(int height) {
		this.height = height;
		return this;
	}

	public DisplayParams makeResizable() {
		this.resizable = true;
		return this;
	}

	public DisplayParams withCustomOnCloseOperation(Callable<Void> rutine) {
		this.onCloseRutine = rutine;
		return this;
	}

	public DisplayParams withMultiBuffering(int numBuffers) {
		this.numBuffers = numBuffers;
		return this;
	}

	public DisplayParams withAntialiasing(byte types) {
		this.antialiasing = types;
		return this;
	}

	public DisplayParams withCustomClearColor(int color) {
		this.clearColor = color;
		return this;
	}

	public DisplayParams makeUndecorated() {
		this.decorated = true;
		return this;
	}
}
