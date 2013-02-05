package com.sidways.leap.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class AlipayMd5Encrypt {

	    public static String md5(String text, String encoding) {
	        return DigestUtils.md5Hex(getContentBytes(text, encoding));
	    }

	    private static byte[] getContentBytes(String content, String charset) {
	        if (charset == null || "".equals(charset)) {
	            return content.getBytes();
	        }
	        try {
	            return content.getBytes(charset);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
	        }
	    }
	}