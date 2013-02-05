package com.sidways.leap.service;


/**
 * @author Kim 2012-7-9
 */
public interface InvitationService extends InvitationChecker{

	public InvitationResponse build();
	
	public int countInvitation();
	
	public interface InvitationResponse extends JSONResponse{
		
		public String getCode();
	}
	
	public class BuildInvitationException extends Exception{

		private static final long serialVersionUID = 1L;

		public BuildInvitationException(String message) {
			super(message);
		}
	}
}
