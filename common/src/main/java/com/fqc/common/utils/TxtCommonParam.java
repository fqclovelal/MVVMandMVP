package com.fqc.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dangdang.reader.Constants;
import com.dangdang.reader.DDApplication;
import com.dangdang.reader.db.ShelfBookDBColumn;
import com.dangdang.reader.db.service.ShelfBookService;
import com.dangdang.reader.personal.domain.GroupType;
import com.dangdang.reader.personal.domain.ScanItem;
import com.dangdang.reader.personal.domain.ShelfBook;
import com.dangdang.reader.personal.domain.ShelfBook.BookType;
import com.dangdang.reader.personal.domain.ShelfBook.TryOrFull;
import com.dangdang.zframework.utils.MD5Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class TxtCommonParam {

    private ConfigManager mConfigManager;
    private ShelfBookService mShelfService;
    private Context mContext;

    public TxtCommonParam(Context context, ShelfBookService service) {
        this.mContext = context;
        this.mShelfService = service;
        mConfigManager = new ConfigManager(mContext);
    }

    public void addBookToShelf(String path) {
        List<ScanItem> list = new ArrayList<ScanItem>();
        ScanItem item = new ScanItem(new File(path), true);
        list.add(item);
        addBookListToShelfBook(list);
    }

    public void deleteBookOnShelf(String path) {
        if (mShelfService.deleteBookByDir(path)) {
            Intent tent = new Intent(Constants.BROADCAST_DELETE_BOOK);
            tent.putExtra(ShelfBookDBColumn.BOOK_DIR, path);
            mContext.sendBroadcast(tent);
        }
    }

    public boolean checkImportBook(String path) {
        ShelfBook info = getShelfBookFromIntent(path);
        if (!isImportBook(info.getBookDir())) {

            return false;
        }
        // 如果导入的书是加密的话，则不打开
        if (info.getMediaId().startsWith(DangdangFileManager.EPUB_BOOK_THIRD_ID_PRE) && DangdangFileManager.isDangdangInnerEpubBook(info.getBookDir())) {

            return false;
        }
        return true;
    }
    private boolean isImportBook(String bookId){
        return bookId.toLowerCase(Locale.CHINA).endsWith(DangdangFileManager.PRE_IMPORT_BOOKS[0]) ||
                bookId.toLowerCase(Locale.CHINA).endsWith(DangdangFileManager.PRE_IMPORT_BOOKS[1]) ||
                bookId.toLowerCase(Locale.CHINA).endsWith(DangdangFileManager.PRE_IMPORT_BOOKS[2]) ;
    }
    public void addBookListToShelfBook(List<ScanItem> list) {

        HashSet<Long> mSet = new HashSet<Long>();

        if (list != null && list.size() > 0) {
            List<ShelfBook> shelflist = new ArrayList<ShelfBook>();
            for (int i = 0; i < list.size(); i++) {
                ScanItem scan = list.get(i);
                if (scan.select) {
                    ShelfBook info = getShelfBookFromIntent(scan.file.getAbsolutePath());
                    long time = info.getLastTime();
                    while (mSet.contains(time)) {
                        ++time;
                    }
                    mSet.add(time);
                    info.setLastTime(time);
                    //生成缩略图
                    shelflist.add(info);
                }
            }

            mShelfService.saveInputShelfBookList(shelflist);
            if (shelflist.isEmpty())
                return;
            DDApplication ddApplication = (DDApplication) (((Activity) mContext).getApplication());
            ddApplication.setmImportBookList(shelflist);
            Intent tent = new Intent(Constants.BROADCAST_REFRESH_BOOKLIST);
            mContext.sendBroadcast(tent);
        }
    }

    private ShelfBook getShelfBookFromIntent(String path) {

        ShelfBook info = new ShelfBook();

        String prefix = DangdangFileManager.getImportBooksPreIndex(path);
        String mBookId = prefix + MD5Util.getMD5Str(path);
        info.setImport(true);
        info.setMediaId(mBookId);
        info.setTitle(DangdangFileManager.getBookNameFromPath(path));
        info.setBookType(BookType.BOOK_TYPE_NOT_NOVEL);
        info.setBookDir(path);
        info.setBookFinish(1);
        info.setUserId(Constants.DANGDANG_DEFAULT_USER);
        info.setUserName(Constants.DANGDANG_DEFAULT_USER);
        info.setLastTime(System.currentTimeMillis());
        info.setGroupId(Constants.UNKNOW_TYPE);
        info.setTryOrFull(TryOrFull.FULL);
        info.setIsOthers(false);

        GroupType type = new GroupType();
        type.setId(0);
        info.setGroupType(type);

        return info;
    }

    private String getPdfResPath() {
        return mConfigManager.getPdfResourceUrl();
    }
}
