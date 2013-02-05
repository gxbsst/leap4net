package com.sidways.leap.service;

/**
 * @author Kim 2012-7-10
 */
public interface ShellService {

	public void createAt(String username, String password);
	
	public void removeAt(String username);
	
	public void changePw(String username, String currentPassword, String password);
	
	public void sync();
}
