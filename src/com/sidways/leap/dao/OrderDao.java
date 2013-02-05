package com.sidways.leap.dao;

import java.util.List;

import com.sidways.leap.entity.Order;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-7-9
 */
public interface OrderDao extends BaseDao<Order> {

	public List<Order> getByUser(User user);
}
