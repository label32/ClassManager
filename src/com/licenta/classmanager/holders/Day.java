package com.licenta.classmanager.holders;

public enum Day {
	Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6), Sunday(7);

	private int code;

	private Day(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}

}
