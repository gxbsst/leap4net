package com.sidways.leap.action;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidways.leap.entity.Order;
import com.sidways.leap.service.OrderService;
import com.sidways.leap.service.OrderService.Payment;
import com.sidways.leap.service.OrderService.Type;
import com.sidways.leap.service.PayService;

/**
 * @author Kim 2012-7-9
 */
@Controller
public class OrderAction extends LocalBaseAction {

	private OrderService orderService;

	private PayService payService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}
	
	@RequestMapping(value = "/switch", method = RequestMethod.GET)
	public String switchLau(HttpSession session) {
		if (super.getSuffix(session).equals("")) {
			session.setAttribute("LAU", "_zh");
		}else{
			session.setAttribute("LAU", null);
		}
		return "redirect:/order";
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView order(HttpSession session) {
		return new ModelAndView("/order" + super.getSuffix(session) + "/index");
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public ModelAndView pay(HttpSession session, Type type, Payment payment, String email, String sale) throws Exception {
		final Order order = this.orderService.order(type, payment, email, sale);
		if (payment.equals(Payment.ALIPAY)) {
			return new ModelAndView("redirect:" + this.payService.buildPayURL(order));
		} else {
			return new ModelAndView("/order" + super.getSuffix(session) + "/index", "response", new HashMap<String, Object>() {
				{
					put("order", order);
					put("paypal", true);
				}
			});
		}
	}

	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	public ModelAndView refund(HttpSession session) {
		return new ModelAndView("/order" + super.getSuffix(session) + "/refund");
	}

	@RequestMapping(value = "/applyRefund", method = RequestMethod.POST)
	public ModelAndView applyRefund(String content) {
		this.orderService.refund(content);
		return new ModelAndView("redirect:/account");
	}
}
