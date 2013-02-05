package com.sidways.leap.service.impl;

import com.sidways.leap.dao.DiscountDao;
import com.sidways.leap.entity.Discount;
import com.sidways.leap.service.DiscountService;

public class DiscountServiceImpl implements DiscountService {

	private DiscountDao discountDao;

	public void setDiscountDao(DiscountDao discountDao) {
		this.discountDao = discountDao;
	}

	public double discount(String code, double price) {
		Discount discount = discountDao.getByCode(code);
		if (discount == null) {
			return price;
		} else {
			if (discount.getType().equals(MinusDiscountPolicy.TYPE)) {
				return new MinusDiscountPolicy().discount(price, discount.getNumber());
			} else if (discount.getType().equals(PercentDiscountPolicy.TYPE)) {
				return new PercentDiscountPolicy().discount(price, discount.getNumber());
			} else {
				return price;
			}
		}
	}
}
