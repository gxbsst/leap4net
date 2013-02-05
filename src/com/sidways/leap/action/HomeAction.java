package com.sidways.leap.action;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kim 2012-7-9
 */
@Controller
public class HomeAction extends LocalBaseAction{

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session){
		return new ModelAndView("/home" + super.getSuffix(session) + "/index");
	}
}
