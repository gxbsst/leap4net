package com.sidways.leap.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sidways.leap.service.IPService;
import com.sidways.leap.service.IPService.IPResponse;

/**
 * @author Kim 2012-7-9
 */
@Controller
public class IPAction{

	private IPService ipService;
	
	public void setIpService(IPService ipService) {
		this.ipService = ipService;
	}

	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	@ResponseBody
	public IPResponse ip(HttpServletRequest request){
		return ipService.search(request.getRemoteHost());
	}
}
