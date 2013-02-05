package com.sidways.leap.dao.impl;

import com.sidways.leap.dao.RefundDao;
import com.sidways.leap.entity.Refund;

/**
 * @author Kim 2012-7-16
 */
public class RefundDaoImpl extends AbstractBaseDao<Refund> implements RefundDao {

	@Override
	protected Class<Refund> getClazz() {
		return Refund.class;
	}
}
