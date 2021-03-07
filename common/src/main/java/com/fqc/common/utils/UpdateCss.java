package com.fqc.common.utils;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dangdang.reader.DDApplication;
import com.dangdang.reader.R;

public class UpdateCss {
	
	private ConfigManager mConfigManager;
	private float mEpubCssVersion;
	private Context mContext;
	private String mEpubCssPath;
	public static final String CODE = "AndroidV3_2_css";
	private final String CSS_VERSION = "1.0";
	
	private final static String KEY_LOCALCSS_VERSION = "key_localcss_version";
	
	/**
	 * 预置css升级替换版本号
	 */
	private final static int EPUBCSS_LOCAL_VERSION = 2;
	
	
	public UpdateCss(ConfigManager configmanager){
		mConfigManager = configmanager;
	}
	
	public void execute(Context context){
		mContext = context;
		checkCssIsExit();
	//4.0暂时不升级css motify by wyz	
	//	sendCommand(BlockActionOfPublic,"&returnType=json&code=" + CODE,BlockActionTag.TAG_CSS_UPDATE);
	}
		
	public void checkCssIsExit(){
		String css = DROSUtility.getEpubCssPath();
		mEpubCssPath = css + DDApplication.EPUB_CSS;
		
		File filePath = new File(mEpubCssPath);
//		if(true){
		if(!filePath.exists() || isUpgradeLocalCss()){
			try {
				filePath.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			DangdangFileManager.writeStringToFile(mContext.getResources().openRawResource(R.raw.style),filePath);
			updateEpubCss(CSS_VERSION);
			
			saveLocalCssVersion(EPUBCSS_LOCAL_VERSION);
		}
		((DDApplication)mContext).setEpubCss(mEpubCssPath);
	}
	
	private boolean isUpgradeLocalCss(){
		
		int localCssVersion = mConfigManager.getPreferences().getInt(KEY_LOCALCSS_VERSION, 0);
		
		return localCssVersion < EPUBCSS_LOCAL_VERSION;
	}
	
	private void saveLocalCssVersion(int version){
		Editor editor = mConfigManager.getPreferences().edit();
		editor.putInt(KEY_LOCALCSS_VERSION, version);
		editor.commit();
	}
	
	public float getEpubCssVersion(){
		float version = 0L;
		String versionStr = mConfigManager.getPreferences().getString(ConfigManager.KEY_CONTENT_CSS_VEVSION, null);
		if(versionStr == null)
			return 0l;
		try{
			version = Float.parseFloat(versionStr);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			version = 0l;
		}
		return version;
	}
	
	public void updateEpubCss(String cssVersion){
		SharedPreferences sp = mConfigManager.getPreferences();
		Editor editor = sp.edit();
		editor.putString(ConfigManager.KEY_CONTENT_CSS_VEVSION, cssVersion);
		editor.commit();
	}
	
}
