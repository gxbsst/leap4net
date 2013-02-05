package com.sidways.leap.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import com.sidways.leap.dao.InvitationDao;
import com.sidways.leap.entity.Invitation;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-7-9
 */
public class InvitationDaoImpl extends AbstractBaseDao<Invitation> implements InvitationDao {

	@Override
	protected Class<Invitation> getClazz() {
		return Invitation.class;
	}
	
	@SuppressWarnings("unchecked")
	public Invitation getByCode(String code){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getClazz());
		detachedCriteria.add(Expression.eq("code", code));
		detachedCriteria.add(Expression.le("deadLine", new Date()));
		return super.head(super.template.findByCriteria(detachedCriteria));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invitation> getInvitationBeforeDate(User user) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getClazz());
		detachedCriteria.add(Expression.eq("owner", user));
		return super.template.findByCriteria(detachedCriteria);
	}
}
