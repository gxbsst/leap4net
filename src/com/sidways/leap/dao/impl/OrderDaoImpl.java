package com.sidways.leap.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import com.sidways.leap.dao.OrderDao;
import com.sidways.leap.entity.Order;
import com.sidways.leap.entity.Order.State;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-7-9
 */
public class OrderDaoImpl extends AbstractBaseDao<Order> implements OrderDao {

	@Override
	protected Class<Order> getClazz() {
		return Order.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getByUser(User user){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getClazz());
		detachedCriteria.add(Expression.eq("owner", user));
		detachedCriteria.add(Expression.le("state", State.PAY));
		return super.template.findByCriteria(detachedCriteria);
	}
}
