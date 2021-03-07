package com.fqc.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 特殊键事件监听
 * 电源，home
 * @author luxu
 */
public class SpecialKeyObserver {

	private Context mContext;
	private IntentFilter mIntentFilter;
	private OnPowerKeyListener mOnPowerKeyListener;
	private PowerKeyBroadcastReceiver mPowerKeyReceiver;

	private OnHomeKeyListener mOnHomeKeyListener;
	private HomeKeyBroadcastReceiver mHomeKeyReceiver;

	public SpecialKeyObserver(Context context) {
		mContext = context;
	}

	public interface OnHomeKeyListener {

		public void onHomeKeyPressed();

		public void onHomeKeyLongPressed();

	}

	public interface OnPowerKeyListener {
		/**
		 * @param onOrOff true:on, false:off
		 */
		public void onPowerKeyPressed(boolean onOrOff);
	}

	public void startPowerListener() {
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
		mPowerKeyReceiver = new PowerKeyBroadcastReceiver();
		mContext.registerReceiver(mPowerKeyReceiver, mIntentFilter);
	}

	public void stopPowerListener() {
		if (mPowerKeyReceiver != null) {
			mContext.unregisterReceiver(mPowerKeyReceiver);
			mPowerKeyReceiver = null;
		}
	}

	public void setPowerKeyListener(OnPowerKeyListener powerKeyListener) {
		mOnPowerKeyListener = powerKeyListener;
	}

	public void startHomeListener() {
		mIntentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		mHomeKeyReceiver = new HomeKeyBroadcastReceiver();
		mContext.registerReceiver(mHomeKeyReceiver, mIntentFilter);
	}

	public void stopHomeListener() {
		if (mHomeKeyReceiver != null) {
			mContext.unregisterReceiver(mHomeKeyReceiver);
			mHomeKeyReceiver = null;
		}
	}

	public void setHomeKeyListener(OnHomeKeyListener homeKeyListener) {
		mOnHomeKeyListener = homeKeyListener;
	}

	class PowerKeyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(mOnPowerKeyListener != null){
				String action = intent.getAction();
				if (action.equals(Intent.ACTION_SCREEN_OFF)) {
					mOnPowerKeyListener.onPowerKeyPressed(false);
				} else if(action.equals(Intent.ACTION_SCREEN_ON)){
					mOnPowerKeyListener.onPowerKeyPressed(true);
				}
			}
		}
	}

	class HomeKeyBroadcastReceiver extends BroadcastReceiver {
		
		final String SYSTEM_DIALOG_REASON_KEY = "reason";
		// 按下Home键
		final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
		// 长按Home键
		final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
				if (reason != null && mOnHomeKeyListener != null) {
					if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
						mOnHomeKeyListener.onHomeKeyPressed();
					} else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
						mOnHomeKeyListener.onHomeKeyLongPressed();
					}
				}
			}
		}
	}

}
