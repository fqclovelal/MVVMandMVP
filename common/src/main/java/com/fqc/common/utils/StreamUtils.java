package com.fqc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StreamUtils {
	
	public static byte[] getBytesFromStream(InputStream is) {
		return getBytesFromStream(is, 1024 * 2);
	}
	
	public static byte[] getBytesFromStream(InputStream is, int length) {
		if(is == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[length];
		try {
			int ch = -1;
			while ((ch = is.read(data)) > -1) {
				baos.write(data, 0, ch);
			}
			data = null;
			data = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			data = null;
		} finally {
			closeStream(is);
			closeStream(baos);
		}
		return data;
	}
	
	public static void closeStream(Closeable c) {
		if(c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int stringToInt(String text) {
		if(text == null) {
			return 0;
		}
		text = text.trim();
		int value = 0;
		try {
			value = Integer.valueOf(text);
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			value = 0;
		}
		return value;
	}
	
	public static String[] convertStringToArray(String textArray, char delim) {
		if(textArray == null) {
			return new String[0];
		}
		ArrayList<String> list = new ArrayList<String>();
		final int length = textArray.length();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; ++i) {
			char ch = textArray.charAt(i);
			if(delim == ch) {
				String text = sb.toString();
				text = text.trim();
				if(text.length() > 0) {
					list.add(text);
				}
				sb.delete(0, sb.length());
				continue;
			}
			sb.append(ch);
		}
		String text = sb.toString();
		text = text.trim();
		if(text.length() > 0) {
			list.add(text);
		}
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}
}
