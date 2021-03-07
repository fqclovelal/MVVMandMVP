package com.fqc.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

public class NetUtils {

	/**
	 * 判断需要的网络是否有连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(Context context) {
		return isNetworkConnected(context);
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					return mNetworkInfo.isConnected(); // return
														// mNetworkInfo.isAvailable();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		}
		return false;
	}

	// private boolean checkWifi(Context context) {
	// boolean isWifiConnect = true;
	// ConnectivityManager cm = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
	// for (int i = 0; i < networkInfos.length; i++) {
	// if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
	// if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
	// isWifiConnect = false;
	// }
	// if (networkInfos[i].getType() == cm.TYPE_WIFI) {
	// isWifiConnect = true;
	// }
	// }
	// }
	// return isWifiConnect;
	// }

	/**
	 * 判断WIFI网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isConnected();
			}
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isConnected();
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	public static final Uri APN_URI = Uri.parse("content://telephony/carriers/preferapn");

	public static ApnWrapper getApn(Context context) {
		Context ctx = context;
		ApnWrapper wrapper = new ApnWrapper();
		Cursor cursor = null;
		try {
			cursor = ctx.getContentResolver().query(APN_URI, 
					new String[] { "name", "apn", "proxy", "port" }, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				if (cursor.isAfterLast()) {
					wrapper.name = "N/A";
					wrapper.apn = "N/A";
				} else {
					wrapper.name = cursor.getString(0) == null ? "" : cursor.getString(0).trim();
					wrapper.apn = cursor.getString(1) == null ? "" : cursor.getString(1).trim();
				}				
			} else {
				wrapper.name = "N/A";
				wrapper.apn = "N/A";
			}
			wrapper.proxy = android.net.Proxy.getDefaultHost();
			wrapper.proxy = TextUtils.isEmpty(wrapper.proxy) ? "" : wrapper.proxy;
			wrapper.port = android.net.Proxy.getDefaultPort();
			wrapper.port = wrapper.port > 0 ? wrapper.port : 80;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeCursor(cursor);
		}
		return wrapper;
	}

	private static void closeCursor(Cursor cursor){
		try{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
		}catch(Exception e){
			
		}
	}
	
	public static class ApnWrapper {
		public String name;
		public String apn;
		public String proxy;
		public int port;

	}
}
