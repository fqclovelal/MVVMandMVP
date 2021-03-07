package com.fqc.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 首次引导管理类
 * @author xiaruri
 *
 */
public class FirstGuideManager {

	/**
	 * static
	 */
	private static final String FIRST_GUIDE_SP = "first_guide_sp";
	private Context mContext;
	private static FirstGuideManager instance;
	
	private FirstGuideManager(Context context) {
		mContext = context.getApplicationContext();
	}
	
	public static FirstGuideManager getInstance(Context context) {
		if (instance == null) {
			instance = new FirstGuideManager(context);
		}
		return instance;
	}
	
	public void release(){
		mContext = null;
		instance = null;
	}
	
	/**
	 * 定义引导标签，版本升级修改key值版本号即可，新增功能点引导，添加枚举即可
	 */
	public enum FirstGuideTag {
        /** 首次运行3张引导页 */
//        IS_FIRST_RUN("is_first_run_v540", false),
        /** 首次运行选择标签 */
//        IS_FIRST_SELECT("is_first_select_v500", false),
        /* 阅读相关的预置文件 */
        IS_FIRST_READFILE("is_first_readfile_v100", false),
		/*单行本预置文件*/
		IS_FIRST_OFFPRINT_FILE("is_first_offprint_v100", false),
		/**
		 * 是否重置书城html，不支持在设置界面中重置引导
		 */
//		IS_RESET_BOOKSTORE_HTML("is_reset_bookstore_html", false),
//		/**
//		 * 频道详情
//		 */
//		IS_FIRST_CHANNEL_GUIDE("is_first_channel_guide", true),
//		/**
//		 * 频道列表
//		 */
//		IS_FIRST_CHANNELLIST_GUIDE("is_first_channellist_guide", true),
        /** 首次提示自动打开 **/
//        IS_FIRST_AUTO_OPEN("is_first_auto_open", true),
//
//        /** 书架引导 **/
//        IS_FIRST_SHELF_GUIDE("is_first_shelf_guide", true),
//        /** 已购FIRST引导 **/
//        IS_FIRST_PERSONFIRSTBUY_GUIDE("is_first_personfirstbuy_guide", true),
//        /** 已购引导 **/
//        IS_FIRST_PERSONBUY_GUIDE("is_first_personbuy_guide", true),
//        /** 主页引导 **/
//        IS_FIRST_PERSONMAIN_GUIDE("is_first_personmain_guide", true),
//        /** 主页只看帖子引导 **/
//        IS_FIRST_PERSONFIRST_GUIDE("is_first_personfirst_guide", true),
//        /** 书友录引导 **/
//        IS_FIRST_PERSONFRIEND_GUIDE("is_first_personfriend_guide", true),
//		/**  是否批量订阅推荐频道**/
//        IS_BATCH_SUBSCRIBE("is_batch_subscribe", false),
//
//        /** 发现引导 **/
//        IS_FIRST_FIND_GUIDE("is_first_find_guide", true),
        /** TTS引导动画 */
        IS_FIRST_TTS_ANIM("is_first_tts_anim", true),
        /** 读书引导 */
        IS_FIRST_READ_BOOK("is_first_read_book_v400", true),
        /** 读书设置引导 */
        IS_FIRST_READ_SETTING("is_first_read_setting_v400", true),
        /** 触摸滑动调节亮度引导 */
        IS_FIRST_TOUCH_LIGHT("is_first_touch_light_v400", true),
        /** 触摸滑动显示目录引导 */
        IS_FIRST_TOUCH_CONTENT("is_first_touch_content_v400", true),
        /** 画线引导 */
        IS_FIRST_READ_LINE("is_first_read_line_v400", true),
        /** 首次剪切 */
        IS_FIRST_IN_CLIP("reader_pdf_firstread", true);

		/** 书吧引导 **/
//		IS_FIRST_BAR_SQUARE_GUIDE("is_first_bar_square_guide", true),
//		IS_FIRST_BAR_BOOK_GUIDE("is_first_bar_book_guide", true),

		/** 签到引导 **/
//        IS_FIRST_HOME_GUIDE("is_first_home_guide", true),
		/** 攻略引导 **/
//        IS_FIRST_HOME_GUIDE2("is_first_home_guide2", true),

		/** 5.4版本首页消息和bar位置调整引导 **/
//		IS_FIRST_HOME_GUIDE3("is_first_home_guide3", true),
		/** 5.4版本阅历入口提示 **/
//		IS_FIRST_PERSONAL_YUELI("is_first_personal_yueli", true),
		/** 5.7云书架入口提示 **/
//		IS_FIRST_SHELF_CLOUD("is_first_shelf_cloud", true),
		/** 5.7拉新分享入口提示 **/
//		IS_FIRST_PERSONAL_INVITE("is_first_personal_invite", true),
		/** 5.7版本读书计划详情设置预开始时间提示 **/
//		IS_FIRST_PLAN_DETAIL_SET_BEGIN_TIME("is_first_plan_detail_set_begin_time", true);
        /**
		 * 映射中的key
		 */
		private String mKey;
		
		/**
		 * 是否支持重置
		 */
		private boolean mReset;

		/**
		 * 定义管理标签
		 * 
		 * @param key
		 *            映射存储key
		 * @param reset
		 *            是否可重置
		 */
		private FirstGuideTag(String key, boolean reset) {
			this.mKey = key;
			this.mReset = reset;
		}

		/**
		 * 获取key值
		 * 
		 * @return mKey
		 */
		public String getKey() {
			return mKey;
		}

		/**
		 * 是否可重置
		 * 
		 * @return mReset
		 */
		public boolean isReset() {
			return mReset;
		}
	}
	
	/**
	 * 获取是否需要显示引导
	 * 
	 * @param tag
	 *            引导标签
	 * @return boolean,默认首次为true
	 */
	public boolean isFirstGuide(FirstGuideTag tag) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				FIRST_GUIDE_SP, Context.MODE_PRIVATE);
		return preferences.getBoolean(tag.getKey(), true);
	}

	/**
	 * 设置是否引导
	 * 
	 * @param tag
	 *            引导标签
	 * @param value
	 *            true为显示，false不显示
	 */
	public void setFirstGuide(FirstGuideTag tag, boolean value) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				FIRST_GUIDE_SP, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(tag.getKey(), value);
		edit.commit();
	}


	/**
	 * 重置所有引导（仅限可重置）
	 * 
	 */
	public void resetFirstGuideAll() {
		FirstGuideTag[] tags = FirstGuideTag.values();
		resetFirstGuide(tags);
	}

	/**
	 * 重置部分引导（仅限可重置）
	 * 
	 * @param tags
	 *            引导标签数组
	 * 
	 */
	public void resetFirstGuide(FirstGuideTag... tags) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				FIRST_GUIDE_SP, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		for (int i = 0; i < tags.length; i++) {
			FirstGuideTag tag = tags[i];
			if (tag.isReset()) {
				edit.putBoolean(tag.getKey(), true);
			}
		}
		edit.commit();
	}
	
}
