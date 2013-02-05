package com.sidways.leap.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.sidways.leap.service.ResourceService;

/**
 * @author Kim 2012-7-16
 */
public class HowToResourceServiceImpl implements ResourceService {

	private File dir;

	public void setDir(File dir) {
		this.dir = dir;
	}

	@Override
	public void find(String resource, OutputStream stream) {
		InputStream in = null;
		try {
			in = new FileInputStream(new File(dir, resource));
			IOUtils.copy(in, stream);
		} catch (Exception e) {
			IOUtils.closeQuietly(in);
		}
	}
}
