package com.sidways.leap.service;

import com.sidways.leap.entity.Order;

/**
 * @author Kim 2012-7-16
 */
public interface PayService {

	public String buildPayURL(Order order);
}
