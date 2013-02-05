package com.sidways.leap.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.sidways.leap.dao.UserDao;
import com.sidways.leap.entity.User;
import com.sidways.leap.service.MailService;
import com.sidways.leap.service.MailService.Type;
import com.sidways.leap.service.ShellService;
import com.sidways.leap.service.UserService;

/**
 * @author Kim 2012-6-20
 */
public class UserServiceImpl extends AbstractGeneralDBService<User> implements UserService {
	
	private Log log = LogFactory.getLog(UserServiceImpl.class);

	private UserDao userDao;
	
	private ShellService shellService;
	
	private MailService mailService;
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setShellService(ShellService shellService) {
		this.shellService = shellService;
	}

	@Override
	public String getCurrentUserName() throws UserNotFoundException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null){
			throw new UserNotFoundException();
		}
		return authentication.getName();
	}
	
	@Override
	public User getCurrentUser() throws UserNotFoundException{
		return this.userDao.getByUsername(this.getCurrentUserName());
	}
	
	@Transactional
	public ChangePwResponse changePw(String currentPassword, String password){
		User user = this.getCurrentUser();
		if(user != null && this.checkPassword(currentPassword, user.getPassword())){
			user.setPassword(password);
			super.baseDao.update(user);
			this.shellService.changePw(user.getUsername(), currentPassword, password);
			return new ChangePwResponseImpl(null, "修改成功");
		}else{
			return new ChangePwResponseImpl("原始密码错误,修改失败", null);
		}
	}
	
	public boolean containUser(String username){
		User user = this.userDao.getByUsername(username);
		return user == null || user.getActivate() == false ? false : true;
	}
	
	public void recovered(String username){
		User user = this.userDao.getByUsername(username);
		if(user != null){
			try {
				this.mailService.changePw(user, Type.PASSWORD);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	@Override
	protected GeneralViewProxy generateGeneralViewProxy(User entity) {
		return null;
	}
	
	private boolean checkPassword(String currentPassword, String password){
		return currentPassword.equals(password);
	}
	
	private static class ChangePwResponseImpl implements ChangePwResponse{

		private String message;
		
		private String error;
		
		public ChangePwResponseImpl(String error, String message) {
			super();
			this.message = message;
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public String getError() {
			return error;
		}
	}
}
