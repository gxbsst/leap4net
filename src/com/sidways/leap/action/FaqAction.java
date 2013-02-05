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
public class FaqAction extends LocalBaseAction{

	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public ModelAndView faq(HttpSession session){
		return new ModelAndView("/faq" + super.getSuffix(session) + "/index");
	}
}
