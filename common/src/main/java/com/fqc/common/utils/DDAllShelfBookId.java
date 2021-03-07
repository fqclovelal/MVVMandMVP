package com.fqc.common.utils;

import java.util.ArrayList;
import java.util.List;

public class DDAllShelfBookId {

	//private static DDAllShelfBookId mInstance = new DDAllShelfBookId() ;
	
	//存放 所有 书籍的id（包括txt和pdf书籍）
	private static List<String> mAllBookId = new ArrayList<String>();
	
	/*private DDAllShelfBookId() {
		mAllBookId = new ArrayList<String>();
	}*/
	
	/*public static DDAllShelfBookId getInstance() {
		return mInstance;
	}*/
	
	/*public synchronized static DDAllShelfBookId getInstance() {
		if (mInstance == null) {
			return new DDAllShelfBookId();
		}
		return mInstance;
	}*/
	
	public static void addBookId(String str) {
		mAllBookId.add(str);
	}
	
	public static void clearAllBookId() {
		mAllBookId.clear();
	}
	
	public static List<String> getAllBookIdList() {
		return mAllBookId;
	}
	
}
