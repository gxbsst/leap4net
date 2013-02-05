package com.sidways.leap.dao.impl;

import java.io.IOException;
import java.util.Properties;

import com.sidways.leap.dao.DiscountDao;
import com.sidways.leap.entity.Discount;

public class DiscountDaoImpl implements DiscountDao {

	private Properties properties;

	public DiscountDaoImpl() throws IOException {
		super();
		properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("discount.properties"));
	}

	@Override
	public Discount getByCode(String code) {
		if(code == null){
			return null;
		}
		String discount = properties.getProperty(code);
		try {
			Discount di = new Discount();
			di.setType(discount.split(",")[0]);
			di.setNumber(Double.valueOf(discount.split(",")[1]));
			return di;
		} catch (Exception e) {
			return null;
		}
	}
}
