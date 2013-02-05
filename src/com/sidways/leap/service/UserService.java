package com.sidways.leap.service;

import com.sidways.leap.entity.User;



/**
 * @author Kim 2012-6-20
 */
public interface UserService {

	public String getCurrentUserName() throws UserNotFoundException;
	
	public User getCurrentUser() throws UserNotFoundException;
	
	public ChangePwResponse changePw(String currentPassword, String password);
	
	public boolean containUser(String username);
	
	public void recovered(String username);
	
	public final static class UserNotFoundException extends RuntimeException{

		private static final long serialVersionUID = 1L;
	}
	
	public interface ChangePwResponse extends JSONResponse{
		
		public String getMessage();
	}
}
