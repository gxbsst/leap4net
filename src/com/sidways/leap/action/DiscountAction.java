package com.sidways.leap.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sidways.leap.service.DiscountService;

@Controller
public class DiscountAction {

	private DiscountService discountService;

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	@RequestMapping(value = "/discount", method = RequestMethod.GET)
	@ResponseBody
	public SaleOff account(String type, Double price, String code) {
		if (type.equals("YEAR")) {
			return new SaleOff(discountService.discount(code, price));
		} else {
			return new SaleOff(price);
		}
	}

	public static class SaleOff {

		private double price;

		public SaleOff(double price) {
			super();
			this.price = price;
		}

		public double getPrice() {
			return price;
		}
	}
}
