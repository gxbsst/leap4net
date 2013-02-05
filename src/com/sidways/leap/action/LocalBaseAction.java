package com.sidways.leap.action;

import javax.servlet.http.HttpSession;

public class LocalBaseAction {

	public String getSuffix(HttpSession session){
		String suffix = (String)session.getAttribute("LAU");
		return suffix == null ? "" : suffix;
	}
}
