package com.sidways.leap.dao;

import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-6-18
 */
public interface UserDao extends BaseDao<User>{

	public User getByUsername(String username);
}
