package com.sidways.leap.service;

/**
 * @author Kim 2012-7-10
 */
public interface IPService {

	public IPResponse search(String ip);
	
	public interface IPResponse extends JSONResponse{
		
		public String getAddress();
	}
}
