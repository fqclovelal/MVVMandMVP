package com.fqc.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.dangdang.zframework.log.LogM;
import com.dangdang.zframework.utils.DRUiUtility;
import com.dangdang.zframework.view.DDEditText;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public final class DROSUtility {
	private static DROSUtility mOSUtility = null;
	private static int mTestId = 1900000000;
	public static final String TAG = "DROSUtility";
	public static final String ACTION_DEL_ALL_IMAGE_CACHE = DangdangConfig.ParamsType.mPagekageName+"_delImageCache";

	private DROSUtility(){
	}
	
	public static synchronized DROSUtility getOSUtilityInstance(){
		if(null == mOSUtility){
			mOSUtility = new DROSUtility();
		}
		return mOSUtility;
	}

	public static String getCachePath(){
		String path = DangdangFileManager.APP_ROOT_PATH + "/ImageCache/";
		if(DangdangFileManager.checkMounted()){		
			 path = DangdangFileManager.getRootPathOnSdcard() + "ImageCache/";
		}
		return path;
	}
	
	public static String getApkDir(){
		String path = DangdangFileManager.APP_ROOT_PATH + "/apk/";
		if(DangdangFileManager.checkMounted()){		
			 path = DangdangFileManager.getRootPathOnSdcard() + "apk/";
		}
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path;
	}
	
	public static File getApkFile(String filename){
		
		String path = getApkDir();
		File file = new File(path, filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	public static String getPermanentId(Context context) {
		String permanentId = "";
		ConfigManager configManager = new ConfigManager(context.getApplicationContext());
		SharedPreferences sp = configManager.getPreferences();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = dateFormat.format(new java.util.Date());
		LogM.i("getPermanentId", "getPermanentId.year="+year);  
		if( year.equals(sp.getString(ConfigManager.KEY_INIT_PERMANENTID, year))==true){
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			String date = sDateFormat.format(new java.util.Date());
			permanentId = date+getRandomInt(100000,900000)+getRandomInt(100000,900000)+getRandomInt(100000,900000);
			LogM.d(TAG, "permanentId=:" + permanentId);
			
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(ConfigManager.KEY_INIT_PERMANENTID, permanentId);
			editor.commit();
		}else{
			permanentId = sp.getString(ConfigManager.KEY_INIT_PERMANENTID, year);
		}
		return permanentId;
	}
	
	/*
	 * ��ȡimg����·��
	 */
	public static String getImageCache(){
		String path = getCachePath();
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path;
	}
	
	
	public static String getEpubCssPath(){
		String path = getCssPath();
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path;
	}
	
	public static String getCssPath(){
		String path = DangdangFileManager.APP_ROOT_PATH + "/EpubCss/";
		return path;
	}
	
	
	private static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
                hexString.append(Integer.toHexString(0xFF & b)).append(separator);
        }
        return hexString.toString();
	}
	
	public static String getMd5(byte[] bytes) {
        try {
                MessageDigest algorithm = MessageDigest.getInstance("MD5");
                algorithm.reset();
                algorithm.update(bytes);
                return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
                LogM.e("DROSUtility", "getMd5"+ e);
                throw new RuntimeException(e);
                // 5d5c87e61211ab7a4847f7408f48ac
        }
	}
	
	public static boolean checkImageCache(String image_url){
		if(image_url == null)
			return false;
		String path = DROSUtility.getImageCache()+ DROSUtility.getMd5(image_url.getBytes());
		File image = new File(path);
		if(!image.exists()){
			return false;
		}
		return true;
	}	
	
	public static String getHtmlPath(String image_url){
		if(image_url == null)
			return null;
		return DROSUtility.getImageCache()+ DROSUtility.getMd5(image_url.getBytes());
	}	
	
	public static boolean checkCachePath(){
		String path = getCachePath();
		File file = new File(path);
		if(!file.exists()){
			return false;
		}
		return true;
	}
	
	public static String getEpubCachePath(){
		String path = DangdangFileManager.APP_ROOT_PATH + "/DelCache/";
		if(DangdangFileManager.checkMounted()){		
			 path = DangdangFileManager.getRootPathOnSdcard() + "DelCache/";
		}
		return path;
	}
	
	public static void deleteEpubCache(Context context){
		if(!checkCachePath())
			return;
		String path = DangdangFileManager.APP_ROOT_PATH + "/ImageCache/";
		if(DangdangFileManager.checkMounted()){		
			 path = DangdangFileManager.getRootPathOnSdcard() + "DelCache";
		}
		String rename = path + "temp";
		
		File file = new File(path);
		File renamefile = new File(rename);
		
		file.renameTo(renamefile);
		RemoveFile remove = new RemoveFile(context,rename,false);
		remove.start();
	}
//	/**
//	 * ɾ���
//	 */
//	public static void deleteImageCache(Context context, boolean isNeedShowMsg){
//		if(!checkCachePath() && isNeedShowMsg){
//			String text = context.getString(R.string.clean_memory_end);
//            UiUtil.showToast(context.getApplicationContext(),text);
//			return;
//		}
//
//		String path = DangdangFileManager.APP_ROOT_PATH + "/ImageCache";
//		if(DangdangFileManager.checkMounted()){
//			 path = DangdangFileManager.getRootPathOnSdcard() + "ImageCache";
//		}
//		String rename = path + "temp";
//
//		File file = new File(path);
//		File renamefile = new File(rename);
//
//		file.renameTo(renamefile);
//		RemoveFile remove;
//		if(isNeedShowMsg){
//			remove = new RemoveFile(context,rename,true);
//		} else{
//			remove = new RemoveFile(context,rename,false);
//		}
//		remove.start();
//	}
	
	public static class RemoveFile implements Runnable {
		private String mFileName;
		private boolean mSend;
		private Context mContext;
		
		public RemoveFile(Context context,String name,boolean send){
			mContext = context;
			mFileName = name;
			mSend = send;
		}
		
		public void run() {
			boolean suc = removeFile(mFileName);
			if(suc && mContext != null && mSend){				
				Intent intent = new Intent();
				intent.setAction(ACTION_DEL_ALL_IMAGE_CACHE);
				mContext.sendBroadcast(intent);
			}
			LogM.w(TAG , "removeFile" + suc);
		}
		
		public void start(){
			new Thread(this).start();
		}
	}
	
	public static boolean removeFile(String delpath) {
		File file = new File(delpath);
		boolean sus = false;
		if(!file.isDirectory()) {
			sus = file.delete();
			if (!sus) {
				LogM.w(TAG, "removeFile" + file.getName());
			}
		}else {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				removeFile(delpath + "/" + filelist[i]);
			}
			sus = file.delete();
		}
		if(!sus) {
			LogM.w(TAG, "removeFile" + file.getName());
		}
		return true;
	}
	
	public static boolean checkEbook(String id){
		try{
			if(Integer.parseInt(id) > mTestId)
				return true;
			return false;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 获取服务器返回图片地址
	 * @param productId
	 * @param cover
	 * @return
	 */
	public static String getPicUrl(String productId,String cover) {
		if(cover == null){
			return getPicUrl(productId);
		}
		if(DangdangConfig.isDevelopEnv() && checkEbook(productId))
			return cover;
		return getPicUrl(productId);
	}
	
	/**
	 * 根据服务器返回的图片地址以及所需图片尺寸，获取对应尺寸的图片地址
	 * @param productId			productId，不能为空
	 * @param cover				服务器返回的图片地址，不能为空
	 * @param coverSize			所需要的图片尺寸，为空或者和ResourceManager中定义的尺寸不匹配时返回最大尺寸图片地址
	 * @return
	 */
	public static String getPicUrl(String productId, String cover, String coverSize) {

		String newPicUrl = "";
		if(!TextUtils.isEmpty(cover)){
			String newUrlHeader = "";
			String newUrlMid = "";
			String newUrlEnd = "";
			if(cover.contains("_epub")){
				newUrlHeader = cover.substring(0, cover.indexOf("_epub")-1);
				newUrlEnd = cover.substring(cover.indexOf("_epub"), cover.length());
				
				if(ImageConfig.IMAGE_SIZE_LL.equals(coverSize)){
					newUrlMid = "d";
				}else if(ImageConfig.IMAGE_SIZE_BB.equals(coverSize)){
					newUrlMid = "b";
				} 
				
				newPicUrl = newUrlHeader + newUrlMid + newUrlEnd;
			} else{
				newPicUrl = cover;
			}
		} 
		
		return newPicUrl;
	}
	
	public static String getPicUrlAsOriginal(String productId,String cover) {
		if(cover == null){
			return getPicUrlAsOriginal(productId);
		}
		if(DangdangConfig.isDevelopEnv() && checkEbook(productId))
			return cover;
		return getPicUrlAsOriginal(productId);
	}
	
	protected static String getPicUrlAsOriginal(String productId) {
		try{
			StringBuffer sb = new StringBuffer("http://img3"); 
			int productid=Integer.parseInt(productId);
			sb.append(""+(productid%10)+".ddimg.cn/");
			sb.append(""+(productid%99)+"/");
			sb.append(""+(productid%37)+"/");
			sb.append(""+productid);
			sb.append("-"+1);
			sb.append("_"+"o.jpg");
			return sb.toString();
		}catch(Exception e){
			LogM.e(e.toString());
		}
		return "";
	}
	
	protected static String getPicUrl(String productId) {
		try{
			StringBuffer sb = new StringBuffer("http://img3"); 
			int productid=Integer.parseInt(productId);
			sb.append(""+(productid%10)+".ddimg.cn/");
			sb.append(""+(productid%99)+"/");
			sb.append(""+(productid%37)+"/");
			sb.append(""+productid);
			sb.append("-"+1);
			if(DRUiUtility.getDensity() >= 2){
				sb.append("_"+"b.jpg");
			}
			else{
				sb.append("_"+"l.jpg");
			}
			return sb.toString();
		}catch(Exception e){
			LogM.e(e.toString());
		}
		return "";
	}
	
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		return false;
	}
	
	//隐藏输入法
	public static void hideSoftKeyBoard(DDEditText text,Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		boolean isopen=imm.isActive(); 
		if(isopen){
			imm.hideSoftInputFromWindow(text.getWindowToken(), 0/*InputMethodManager.HIDE_NOT_ALWAYS*/);
		}
	}
	
//	public static String getTencentUserNickName(OAuthV2 oauth){
//		String rlt = "";
//		String name = "";
//		try{
//			rlt = getTencentInfo(oauth);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//    	if(rlt != null && !"".equals(rlt)){
//    		try{
//    			JSONObject obj = new JSONObject(rlt);
//    			JSONObject obj2 = obj.getJSONObject("data");
//    			name = obj2.getString("nick");
//    		}catch(JSONException e){
//    			e.printStackTrace();
//    		}
//    	}
//    	return name;
//    }
	
//	public static String getTencentInfo(OAuthV2 oauth)throws Exception{
//    	UserAPI api = new UserAPI(OAuthConstants.OAUTH_VERSION_2_A);
//    	String rlt = api.info(oauth, "json");
//    	api.shutdownConnection();
//    	return rlt;
//    }
	
	public static void share(Context context,String shareTitle,String content){
		Intent intent = new Intent(Intent.ACTION_SEND);  
		intent.setType("text/plain");  
		intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);  
		intent.putExtra(Intent.EXTRA_TEXT, content);  
		context.startActivity(Intent.createChooser(intent, "")); 
	}
	
	/**
	 * 
	 * @param context
	 * @return true 模拟器 false 真机
	 */
	public static boolean phoneOrEmulator(Context context){
		TelephonyManager telmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		String deviceID = telmgr.getDeviceId(); 
		return "000000000000000".equalsIgnoreCase(deviceID); 
	}
	
	protected static boolean checkHeapSize(int width,int height) {
		long heapsize = Debug.getNativeHeapSize();
		if(heapsize <= height*width*4){
			LogM.e("wyz Heap size too small");
			return false;
		}
		return true;
	}
	
	public static Bitmap clipBitmap(Bitmap bitmap){
		Bitmap result = null;
		int bitmapHeight = bitmap.getHeight();
		int bitmapWidth = bitmap.getWidth();
		try {
			result = Bitmap.createBitmap(bitmap,bitmapWidth*3 / 20, 0, bitmapWidth * 14 / 20,bitmapHeight);
		} 
		catch (OutOfMemoryError err) {
			err.printStackTrace();
			result = null;
			return null;
		}
		finally{
			bitmap.recycle();
			bitmap = null;
		}
		return result;
	}
	
	//获取指定区间的随机数
	public static int getRandomInt(int start, int end) {
        return  (int) (Math.random()*(end-start+1))+start;
    }
	
	public static String getPrice(String price) {
		if(price == null) {
			price = "";
		}
		if ("null".equals(price)){
			price="";
		}
		price = price.trim();
		if(price.length() == 0) {
			return "0";
		} 
		if(price.length() == 1) {
			return "0.0" + price;
		} else if(price.length() == 2){
			return "0." + price;
		} else {
			int len = price.length();
			return price.substring(0,len-2)+ "." + price.substring(len-2, len);
		}
	}
	
	public static String getDeviceModel(){
		return	Build.MODEL;
	}
}
