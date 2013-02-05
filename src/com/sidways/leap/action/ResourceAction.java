package com.sidways.leap.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sidways.leap.service.ResourceService;

/**
 * @author Kim 2012-7-16
 */
@Controller
public class ResourceAction{

	private ResourceService resourceService;

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@RequestMapping(value = "/resource/pdf", method = RequestMethod.GET)
	public void findPDF(String resource, HttpServletResponse response) throws IOException {
		//response.setHeader("Content-Disposition", "attachment;filename=howTo.pdf");
		this.resourceService.find(resource, response.getOutputStream());
	}
}
