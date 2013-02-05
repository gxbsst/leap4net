package com.sidways.leap.dao;

import com.sidways.leap.entity.Discount;

public interface DiscountDao {

	public Discount getByCode(String code);
}
