package com.sidways.leap.service;


public interface DiscountService{

	public double discount(String code, double price);
	
	public interface DiscountPolicy {

		public double discount(double price, double plus);
	}
}
