package com.fqc.common.utils;

import android.text.TextUtils;

/**
 * 书籍购买转化率工具
 * Created by xiaruri on 2016/1/18.
 */
public class BuyBookStatisticsUtil {

    private String way;             // 一级来源
    private String searchWay;       // 搜索的临时来源
    private String showType;        // 倒数第二级来源
    private String showTypeId;      // 倒数第二级来源id
    private String tradeType;       // 交易来源
    private int rowNum = -1;

    private static BuyBookStatisticsUtil mBuyBookStatisticsUtil;

    private BuyBookStatisticsUtil(){
    }

    public static BuyBookStatisticsUtil getInstance(){
        if(mBuyBookStatisticsUtil == null){
            mBuyBookStatisticsUtil = new BuyBookStatisticsUtil();
        }
        return mBuyBookStatisticsUtil;
    }

    public String getStatisticsParam(){
        String way = TextUtils.isEmpty(searchWay) ? this.way : searchWay;

        if(TextUtils.isEmpty(way)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&way=" + way);
        sb.append("&showType=" + showType);
        sb.append("&showTypeId=" + showTypeId);
        sb.append("&tradeType=" + tradeType);
        if(rowNum != -1){
            sb.append("&rowNum=" + rowNum);
        }
        return sb.toString();
    }
    public String getStatisticsParamJson(){
        String way = TextUtils.isEmpty(searchWay) ? this.way : searchWay;

        if(TextUtils.isEmpty(way)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\"way\":" +"\""+ way+"\"");
        sb.append(",\"showType\":" +"\""+ showType+"\"");
        sb.append(",\"showTypeId\":" +"\""+ showTypeId+"\"");
        sb.append(",\"tradeType\":" +"\""+ tradeType+"\"");

        if(rowNum != -1){
            sb.append(",\"rowNum\":" +"\""+ rowNum+"\",");
        }else {
            sb.append(",");
        }
        return sb.toString();
    }

    public void setWay(String way) {
        this.way = TextUtils.isEmpty(way) ? "" : way;
    }

    public String getSearchWay() {
        return searchWay;
    }

    public void setSearchWay(String searchWay) {
        this.searchWay = searchWay;
    }

    public void setShowType(String showType) {
        this.showType = TextUtils.isEmpty(showType) ? "" : showType;
    }

    public void setShowTypeId(String showTypeId) {
        this.showTypeId = TextUtils.isEmpty(showTypeId) ? "" : showTypeId;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = TextUtils.isEmpty(tradeType) ? "" : tradeType;
    }

    public String getWay() {
        return way;
    }

    public String getShowType() {
        return showType;
    }

    public String getShowTypeId() {
        return showTypeId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * 一级来源
     */
    public class Way{
        public static final String WAY_PUSH = "push";                           // 百度推送
        public static final String WAY_RECOMMEND_PAGE = "recommendPage";        // 书城推荐页
        public static final String WAY_PUBLISH = "publish";                     // 书城出版业
        public static final String WAY_ORIGINAL = "original";                   // 书城原创页
        public static final String WAY_CHILDRENBOOK = "childrenBook";           // 书城童书页
        public static final String WAY_BOOKLIST = "bookList";                   // 榜单页
        public static final String WAY_CATEGORY = "category";                   // 分类页
        public static final String WAY_SEARCH = "search";                       // 搜索页
        public static final String WAY_DISCOVER = "discover";                   // 发现页
        public static final String WAY_USERCENTER = "userCenter";               // 个人中心
        public static final String WAY_SHARING = "sharing";                     // 分享
        public static final String WAY_HOMEPAGE = "homePage";                   // 首页
        public static final String WAY_BOOT = "boot";                           // 启动页
        public static final String WAY_RECOMMEND = "recommend";                 // 系统推荐
        //5.7新增
        public static final String WAY_PRODUCT = "product";                     // 单品页（仅用于单品的推荐模块时使用）
        public static final String WAY_TUIJIAN = "tuijian";                     //
        public static final String WAY_READPLAN = "readPlan";                   // 读书计划
        public static final String WAY_PLANACTIVITY = "planActivity";           // 读书活动
    }

    /**
     * 倒数第二级来源
     */
    public class ShowType{
        public static final String SHOW_TYPE_COLUMN = "column";                 // 栏目
        public static final String SHOW_TYPE_CATEGORY = "category";             // 分类
        public static final String SHOW_TYPE_BOOKLIST = "bookList";             // 榜单
        public static final String SHOW_TYPE_SPECIAL_TOPIC = "specialTopic";    // 专题
        public static final String SHOW_TYPE_BOOK_BAR = "bookbar";              // 书吧
        public static final String SHOW_TYPE_CHANNEL = "channel";               // 频道书单
        public static final String SHOW_TYPE_IM = "IM";                         // IM
        public static final String SHOW_TYPE_READ = "read";                     // 阅读器
        public static final String SHOW_TYPE_GIVE = "give";                     // 赠书
        public static final String SHOW_TYPE_CART = "cart";                     // 购物车
        public static final String SHOW_TYPE_KEEP = "keep";                     // 收藏
        public static final String SHOW_TYPE_ARTICLE = "article";               // 文章
        public static final String SHOW_TYPE_NOTE = "note";                     // 笔记
        public static final String SHOW_TYPE_ORDER = "order";                   // 订单
        public static final String SHOW_TYPE_HISTORY = "history";               // 阅历
        public static final String SHOW_TYPE_GUESSULIKE = "guessulike";         // 猜你喜欢
        //5.7新增
        public static final String SHOW_TYPE_ALSOBUY = "alsobuy";               // 买了又买
        public static final String SHOW_TYPE_ALSOVIEW = "alsoview";             // 看了又看
        public static final String SHOW_TYPE_ALSOBUYREAD = "alsobuyread";          // 阅读完成页5.7新增
        public static final String SHOW_TYPE_READPLAN = "readPlan";             // 读书计划
        public static final String SHOW_TYPE_PLANACTIVITY = "planActivity";     // 读书活动
        public static final String SHOW_TYPE_EBOOKSEARCH = "ebookSearch";       // 电子书搜索
        public static final String SHOW_TYPE_ALSOVIEW_CHANNEL = "alsoviewchannel";       //

    }

    /**
     * 交易类型
     */
    public class TradeType{
        public static final String TRADE_TYPE_BOOKSTORE = "bookStore";          // 书架
        public static final String TRADE_TYPE_ITEM = "item";                    // 单品页
        public static final String TRADE_TYPE_CART = "cart";                    // 购物车
        public static final String TRADE_TYPE_READ = "read";                    // 阅读器
        public static final String TRADE_TYPE_READPLAN = "readPlan";            // 读书计划
    }

}
