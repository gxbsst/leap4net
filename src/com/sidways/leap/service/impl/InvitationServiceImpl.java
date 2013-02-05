package com.sidways.leap.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.sidways.leap.dao.InvitationDao;
import com.sidways.leap.entity.Invitation;
import com.sidways.leap.service.InvitationChecker;
import com.sidways.leap.service.InvitationService;
import com.sidways.leap.service.UserService;

/**
 * @author Kim 2012-7-9
 */
public class InvitationServiceImpl extends AbstractGeneralDBService<Invitation> implements InvitationService {

	private InvitationChecker momoryInvitationChecker;
	
	private InvitationDao invitationDao;

	private UserService userService;
	
	private Integer validity;
	
	private Integer maxCount;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public void setInvitationDao(InvitationDao invitationDao) {
		this.invitationDao = invitationDao;
	}
	
	public InvitationServiceImpl() throws Exception {
		super();
		this.momoryInvitationChecker = new MomoryInvitationChecker();
	}

	@Override
	@Transactional
	public InvitationResponse build(){
		try{
			this.checkCount(this.invitationDao.getInvitationBeforeDate(this.userService.getCurrentUser()));
		}catch(BuildInvitationException e){
			return new InvitationResponseImpl("邀请码每月最多不允许超过" + this.maxCount + "个", null);
		}
		Invitation invitation = new Invitation();
		invitation.setCode(this.buildCode());
		invitation.setDeadLine(this.buildValidity());
		invitation.setOwner(this.userService.getCurrentUser());
		super.baseDao.save(invitation);
		return new InvitationResponseImpl(null, invitation.getCode());
	}
	
	public boolean checkInvitation(String code){
		Invitation invitation = this.invitationDao.getByCode(code);
		boolean dbInvitation = invitation != null ? true : false;
		return dbInvitation || this.momoryInvitationChecker.checkInvitation(code);
	}
	
	public int countInvitation(){
		return this.invitationDao.getInvitationBeforeDate(this.userService.getCurrentUser()).size();
	}
	
	@Transactional
	@Scheduled(cron="0 * * 1 * ?")
	public void clearAll(){
		for(Invitation invitation : this.invitationDao.getAll()){
			this.invitationDao.delete(invitation.getId());
		}
	}
		
	protected void checkCount(List<Invitation> invitations) throws BuildInvitationException{
		if(invitations.size() >= maxCount){
			throw new BuildInvitationException("最大只允许创建" + this.maxCount + "个邀请码");
		}
	}
	
	protected Date buildValidity(){
		DateTime dt = new DateTime();
		dt.plusHours(validity);
		return dt.toDate();
	}
	
	protected String buildCode(){
		return UUID.randomUUID().toString();
	}

	@Override
	protected GeneralViewProxy generateGeneralViewProxy(Invitation entity) {
		return null;
	}
	
	private static class InvitationResponseImpl implements InvitationResponse{
	
		private String error;
		
		private String code;

		private InvitationResponseImpl(String error, String code) {
			super();
			this.error = error;
			this.code = code;
		}

		@Override
		public String getError() {
			return error;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
	
	private class MomoryInvitationChecker implements InvitationChecker{
		
		private Map<String, MemoryInvitation> invitations;
		
		public MomoryInvitationChecker() throws Exception{
			this.invitations = new HashMap<String, MemoryInvitation>();
			this.generateInvitaiton();
		}

		@Override
		public boolean checkInvitation(String code) {
			MemoryInvitation invitation = this.invitations.get(code.toLowerCase());
			if(invitation == null){
				return false;
			}
			return invitation.valid();
		}

		private void generateInvitaiton() throws Exception{
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("invitation.properties"));
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(Object code : properties.keySet()){
				String[] dates = properties.getProperty(code.toString()).split(",");
				MemoryInvitation invitation = new MemoryInvitation(format.parse(dates[0]), format.parse(dates[1]));
				this.invitations.put(code.toString().toLowerCase(), invitation);
			}
		}
		
		private class MemoryInvitation{
			
			private Date startDate;
			
			private Date endDate;

			public MemoryInvitation(Date startDate, Date endDate) {
				super();
				this.startDate = startDate;
				this.endDate = endDate;
			}
			
			public boolean valid(){
				Date currentDate = new Date();
				return currentDate.after(this.startDate) && currentDate.before(this.endDate);
			}
		}
	}
}
