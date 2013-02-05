package com.sidways.leap.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import com.sidways.leap.dao.UserDao;
import com.sidways.leap.entity.User;

/**
 * @author Kim
 * 2012-6-18
 */
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

	@Override
	protected Class<User> getClazz() {
		return User.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getByUsername(String username) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getClazz());
		detachedCriteria.add(Expression.eq("username", username));
		return super.head(super.template.findByCriteria(detachedCriteria));
	}
}
