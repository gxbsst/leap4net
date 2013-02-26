package com.sidways.leap.config;

import java.io.File;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.apache.commons.logging.Log;
/**
 * @author Kim 2012-7-9
 */
public class FileSystemPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private Log log = LogFactory.getLog(FileSystemPropertyPlaceholderConfigurer.class);

	public FileSystemPropertyPlaceholderConfigurer(String configKey, String fileName) {
		log.info("Property value of System.getProperty(configKey): " + System.getProperty(configKey));
		
		super.setLocation(new FileSystemResource(System.getProperty(configKey) + File.separator + fileName));
	}
}
