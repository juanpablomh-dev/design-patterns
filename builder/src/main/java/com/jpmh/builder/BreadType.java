package com.jpmh.builder;

public enum BreadType {

	WHOLEMEAL("wholemeal"), WHITE("White"), ITALIAN("italian");

	private final String title;

	private BreadType(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
