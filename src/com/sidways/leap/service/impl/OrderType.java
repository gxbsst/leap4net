package com.sidways.leap.service.impl;

/**
 * @author Kim 2012-7-9
 */
public enum OrderType {

	DAY, MONTH, YEAR;

	public Double getPrice() {
		switch (this) {
		case DAY:
			return 1.49;
		case MONTH:
			return 7.99;
		case YEAR:
			return 69.99;
		}
		throw new RuntimeException();
	}
}
