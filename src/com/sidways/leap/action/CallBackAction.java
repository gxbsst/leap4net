package com.sidways.leap.action;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidways.leap.service.CallBackService;
import com.sidways.leap.service.OrderService;

/**
 * @author Kim 2012-7-16
 */
@Controller
public class CallBackAction extends LocalBaseAction{
	
	private CallBackService callBackService;
	
	private OrderService orderService;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setCallBackService(CallBackService callBackService) {
		this.callBackService = callBackService;
	}

	@RequestMapping(value = "/call/notifyCall", method = RequestMethod.POST)
	public void notifyCall(HttpServletRequest request, Writer writer) throws IOException{
		this.callBackService.callback(request.getParameterMap());
		writer.write("success");
	}
	
	@RequestMapping(value = "/call/returnCall", method = RequestMethod.GET)
	public ModelAndView returnCall(HttpServletRequest request, HttpSession session){
		return new ModelAndView("/home" + super.getSuffix(session) + "/callback", "user", orderService.get(request.getParameter("out_trade_no")));
	}
}
