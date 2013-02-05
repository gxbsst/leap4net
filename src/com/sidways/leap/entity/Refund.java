package com.sidways.leap.entity;

/**
 * @author Kim 2012-7-16
 */
public class Refund extends Entity {

	private Order target;
	
	private String content;

	public Order getTarget() {
		return target;
	}

	public void setTarget(Order target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
