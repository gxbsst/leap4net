package com.sidways.leap.action;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WhyAction extends LocalBaseAction {

	@RequestMapping(value = "/why", method = RequestMethod.GET)
	public ModelAndView why(HttpSession session) {
		return new ModelAndView("/why" + super.getSuffix(session) + "/index");
	}
}
