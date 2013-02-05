package com.sidways.leap.dao;

import java.util.List;

import com.sidways.leap.entity.Invitation;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-7-9
 */
public interface InvitationDao extends BaseDao<Invitation>{

	public Invitation getByCode(String code);
	
	public List<Invitation> getInvitationBeforeDate(User user);
}
