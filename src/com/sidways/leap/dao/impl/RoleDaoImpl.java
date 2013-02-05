package com.sidways.leap.dao.impl;

import com.sidways.leap.dao.RoleDao;
import com.sidways.leap.entity.Role;

/**
 * @author Kim
 * 2012-6-18
 */
public class RoleDaoImpl extends AbstractBaseDao<Role> implements RoleDao {

	@Override
	protected Class<Role> getClazz() {
		return Role.class;
	}
}
