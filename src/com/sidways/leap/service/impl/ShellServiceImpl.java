package com.sidways.leap.service.impl;

import java.io.IOException;
import java.util.Formatter;

import org.apache.commons.io.IOUtils;

import com.sidways.leap.service.ShellService;

/**
 * @author Kim 2012-7-10
 */
public class ShellServiceImpl implements ShellService {
	
	private String createAt;
	
	private String removeAt;
	
	private String changePw;
	
	private String sync;
	
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public void setRemoveAt(String removeAt) {
		this.removeAt = removeAt;
	}

	public void setChangePw(String changePw) {
		this.changePw = changePw;
	}
	
	public void setSync(String sync) {
		this.sync = sync;
	}

	@Override
	synchronized public void createAt(String username, String password) {
		Formatter formatter = new Formatter();
		formatter.format(this.createAt, username, password);
		try {
			this.run(formatter.out().toString());
			this.sync();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	synchronized public void removeAt(String username) {
		Formatter formatter = new Formatter();
		formatter.format(this.removeAt, username);
		try {
			this.run(formatter.out().toString());
			this.sync();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	synchronized public void changePw(String username, String currentPassword, String password) {
		Formatter formatter = new Formatter();
		formatter.format(this.changePw, username, currentPassword, password);
		try {
			this.run(formatter.out().toString());
			this.sync();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sync(){
		Formatter formatter = new Formatter();
		formatter.format(this.sync);
		try {
			this.run(formatter.out().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void run(String shell) throws IOException{
		String[] env = new String[] { System.getenv("PATH") };
		Process process = Runtime.getRuntime().exec(shell, env);
		IOUtils.toString(process.getInputStream());
		IOUtils.closeQuietly(process.getInputStream());
		process.destroy();
	}
}
