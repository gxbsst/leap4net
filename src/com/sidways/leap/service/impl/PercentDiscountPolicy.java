package com.sidways.leap.service.impl;

import com.sidways.leap.service.DiscountService.DiscountPolicy;

public class PercentDiscountPolicy implements DiscountPolicy {

	public static final String TYPE = "PERCENT";
	
	@Override
	public double discount(double price, double plus) {
		return price * plus;
	}
}
