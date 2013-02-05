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
public class ContactAction extends LocalBaseAction{

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact(HttpSession session){
		return new ModelAndView("/contact" + super.getSuffix(session) + "/index");
	}
}
