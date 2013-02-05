package com.sidways.leap.action;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sidways.leap.service.InvitationService;
import com.sidways.leap.service.InvitationService.BuildInvitationException;
import com.sidways.leap.service.InvitationService.InvitationResponse;
import com.sidways.leap.service.OrderService;
import com.sidways.leap.service.UserService;
import com.sidways.leap.service.UserService.ChangePwResponse;

/**
 * @author Kim 2012-7-9
 */
@Controller
public class AccountAction extends LocalBaseAction {

	private InvitationService invitationService;

	private OrderService orderService;

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setInvitationService(InvitationService invitationService) {
		this.invitationService = invitationService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView account(HttpSession session) {
		return new ModelAndView("/account" + super.getSuffix(session) + "/index", "history", this.orderService.history());
	}

	@RequestMapping(value = "/pw", method = RequestMethod.GET)
	public ModelAndView pw(HttpSession session) {
		return new ModelAndView("/account" + super.getSuffix(session) + "/pw");
	}

	@RequestMapping(value = "/changePw", method = RequestMethod.POST)
	@ResponseBody
	public ChangePwResponse changePw(String currentPassword, String password) {
		return this.userService.changePw(currentPassword, password);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Boolean error, HttpSession session) {
		return new ModelAndView("/account" + super.getSuffix(session) + "/login", "error", error);
	}

	@RequestMapping(value = "/changeLocal", method = RequestMethod.GET)
	public String local(String local, HttpSession session) {
		session.setAttribute("LAU", local);
		return "redirect:/login";
	}

	@RequestMapping(value = "/invitation", method = RequestMethod.GET)
	@ResponseBody
	public InvitationResponse invitation() throws BuildInvitationException {
		return this.invitationService.build();
	}

	@RequestMapping(value = "/countInvitation", method = RequestMethod.GET)
	@ResponseBody
	public int countInvitation() throws BuildInvitationException {
		return this.invitationService.countInvitation();
	}

	@RequestMapping(value = "/guest", method = RequestMethod.POST)
	public ModelAndView invitationApply(String code, HttpSession session) {
		if (this.invitationService.checkInvitation(code)) {
			return new ModelAndView("/account" + super.getSuffix(session) + "/guest");
		} else {
			return new ModelAndView("redirect:/login?error=true");
		}
	}

	@RequestMapping(value = "/contain", method = RequestMethod.GET)
	@ResponseBody
	public boolean contain(String username) throws BuildInvitationException {
		return this.userService.containUser(username);
	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public ModelAndView forget(HttpSession session) {
		return new ModelAndView("/account" + super.getSuffix(session) + "/forget");
	}

	@RequestMapping(value = "/recovered", method = RequestMethod.POST)
	public ModelAndView recovered(String username) throws BuildInvitationException {
		this.userService.recovered(username);
		return new ModelAndView("redirect:/account/login");
	}
}
