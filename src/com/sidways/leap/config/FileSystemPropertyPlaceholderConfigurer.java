package com.sidways.leap.config;

import java.io.File;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * @author Kim 2012-7-9
 */
public class FileSystemPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	public FileSystemPropertyPlaceholderConfigurer(String configKey, String fileName) {
		super.setLocation(new FileSystemResource(System.getProperty(configKey) + File.separator + fileName));
	}
}
