package com.sidways.leap.service.impl;

import com.sidways.leap.service.DiscountService.DiscountPolicy;

public class MinusDiscountPolicy implements DiscountPolicy {

	public static final String TYPE = "MINUS";
	
	@Override
	public double discount(double price, double plus) {
		return price - plus;
	}
}
