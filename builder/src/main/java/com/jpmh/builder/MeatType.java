package com.jpmh.builder;

public enum MeatType {

	BEEF("beef"), CHICKEN("chicken"), FISH("fesh");

	private final String title;

	private MeatType(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
