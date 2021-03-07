package com.fqc.common.utils;

import android.content.Context;
import android.widget.Toast;

import com.dangdang.reader.Constants;
import com.dangdang.reader.R;
import com.dangdang.reader.db.service.DDStatisticsService;
import com.dangdang.reader.dread.StartRead;
import com.dangdang.reader.dread.jni.BaseJniWarp;
import com.dangdang.reader.dread.util.ParserEpubN;
import com.dangdang.reader.personal.domain.ShelfBook;
import com.dangdang.reader.personal.domain.ShelfBook.BookType;
import com.dangdang.reader.personal.domain.ShelfBook.TryOrFull;
import com.dangdang.zframework.utils.UiUtil;

import java.io.File;
import java.util.Date;

public class ReadBook {

	private Context mContext;
	private ShelfBook mInfo;

	private DDStatisticsService mDDService;

	public ReadBook(Context context, ShelfBook info) {
		mContext = context;
		mInfo = info;
		mDDService = DDStatisticsService.getDDStatisticsService(context);
	}

	public void readBook(String refer) {
		if (mInfo.getMediaId().startsWith(DangdangFileManager.TXT_BOOK_ID_PRE)) {
//			readTxtImportBook(refer);
			startRead(mInfo, BaseJniWarp.BOOKTYPE_DD_TXT, StartRead.ReadParams.FTYPE_TXT);
		} else if (mInfo.getMediaId().startsWith(
				DangdangFileManager.PDF_BOOK_ID_PRE)) {
//			readPdfImportBook(refer);
			startRead(mInfo, BaseJniWarp.BOOKTYPE_DD_PDF, StartRead.ReadParams.FTYPE_PDF);
		} else if (mInfo.getMediaId().startsWith(
				DangdangFileManager.EPUB_BOOK_THIRD_ID_PRE)) {
//			readThirdEpubImportBook(refer);
			startRead(mInfo, BaseJniWarp.BOOKTYPE_THIRD_EPUB, StartRead.ReadParams.FTYPE_EPUB);
		} else {
			if (mInfo.getTryOrFull() == TryOrFull.BORROW_FULL) {
				mDDService.addData(DDStatisticsService.BORROWBOOKCLICK,
						DDStatisticsService.ProductId, mInfo.getMediaId(),
						DDStatisticsService.OPerateTime,
						String.valueOf(new Date().getTime()));
			}
//			readDangDangEpubBook(refer);
			if(mInfo.getBookType() == BookType.BOOK_TYPE_NOT_NOVEL){
				String bookdir = mInfo.getBookDir().trim();
				// 判断文件是否存在
				if (DangdangFileManager.isFileExist(bookdir)
						&& DangdangFileManager.isFileHasChild(bookdir)) {
                    //TODO 内核已经处理。
//					if (fileIsNewData(bookdir + File.separator + mInfo.getMediaId())) {

						startRead(mInfo, BaseJniWarp.BOOKTYPE_DD_DRM_EPUB, StartRead.ReadParams.FTYPE_EPUB);
//					} else {
//						showReDownloadDialog();
//					}
				} else {
					SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
				}
			}else{
				startRead(mInfo, BaseJniWarp.BOOKTYPE_DD_DRM_HTML, StartRead.ReadParams.FTYPE_PART);
			}
		}
	}
	
	private void startRead(ShelfBook book, int type, int fileType){
		if (fileType != StartRead.ReadParams.FTYPE_PART && 
				!DangdangFileManager.isFileExist(book.getBookDir())){
			SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
			return;
		}	
		
		StartRead startRead = new StartRead();
        StartRead.PartReadParams params = new StartRead.PartReadParams();
        String bookfile = book.getBookDir();
        if (type ==BaseJniWarp.BOOKTYPE_DD_DRM_EPUB && !bookfile.endsWith(".epub"))
        	bookfile = DangdangFileManager.getBookDest(bookfile, book.getMediaId(), BookType.BOOK_TYPE_NOT_NOVEL).getAbsolutePath();
        params.setBookFile(bookfile);
        params.setBookDir(book.getBookDir());
        params.setBookId(book.getMediaId());
        params.setBookName(book.getTitle());
        params.setIsBought(TryOrFull.TRY != book.getTryOrFull());
        params.setBookType(type);
        params.setIsFull(book.getBookType()==BookType.BOOK_TYPE_IS_FULL_YES);
        params.setFileType(fileType);
		params.setSaleId(book.getSaleId());
		params.setBookAuthor(book.getAuthorPenname());
		params.setBookCover(book.getCoverPic());
		params.setBookDesc(book.getDescs());
		params.setBookCategories(book.getGroupType().getName());		
		params.setReadProgress(book.getReadProgress());
		params.setBookCertKey(book.getBookKey());
		params.setIsFollow(book.isFollow());
		params.setIndexOrder(book.getServerLastIndexOrder());
		params.setPreload(book.isPreload());
        
		startRead.startPartRead(mContext, params);
        
	}

//	public void readDangDangEpubBook(String refer) {
//
//		String bookdir = mInfo.getBookDir().trim();
//		// 判断文件是否存在
//		if (DangdangFileManager.isFileExist(bookdir)
//				&& DangdangFileManager.isFileHasChild(bookdir)) {
//			if (fileIsNewData(bookdir + File.separator + mInfo.getMediaId())) {
//				Intent startReadIntent = initReadMainActivityIntent(refer);
//				mContext.startActivity(startReadIntent);
//			} else {
//				showReDownloadDialog();
//			}
//		} else {
//			SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
//		}
//	}
//
//	public void readThirdEpubImportBook(String refer) {
//		String bookdir = mInfo.getBookDir().trim();
//		// 判断文件是否存在
//		if (DangdangFileManager.isFileExist(bookdir)) {
//			Intent startReadIntent = initReadMainActivityIntent(refer);
//			mContext.startActivity(startReadIntent);
//		} else {
//			SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
//		}
//	}
//
//	// 放置pdf的业务逻辑
//	public void readPdfImportBook(String refer) {
//
//		String bookdir = mInfo.getBookDir().trim();
//		Intent startReadIntent = new Intent(mContext, ReadMainActivity.class);
//		startReadIntent.putExtra(IntentK.ProductId, mInfo.getMediaId());
//		startReadIntent.putExtra(IntentK.BookName, mInfo.getTitle());
//		startReadIntent.putExtra(IntentK.BookDir, bookdir);
//		startReadIntent.putExtra(IntentK.BookEpub, bookdir);
//		startReadIntent.putExtra(IntentK.IsBought, true);
//		startReadIntent
//				.putExtra(IntentK.ReadProgress, mInfo.getReadProgress());
//		startReadIntent.putExtra(IntentK.ReferType, refer);
//		startReadIntent.putExtra(IntentK.BookType, BaseJniWarp.BOOKTYPE_DD_PDF);
//
//		// 判断文件是否存在
//		if (DangdangFileManager.isFileExist(bookdir)) {
//			mContext.startActivity(startReadIntent);
//		} else {
//			SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
//		}
//
//	}
//
//	public void readTxtImportBook(String refer) {
//
//		String bookdir = mInfo.getBookDir().trim();
//		// 判断文件是否存在
//		if (DangdangFileManager.isFileExist(bookdir)) {
//			Intent startReadIntent = initReadMainActivityIntent(refer);
//			mContext.startActivity(startReadIntent);
//		} else {
//			SystemLib.showTip(mContext, R.string.file_not_exist_with_error);
//		}
//	}

	private void showReDownloadDialog() {
		UiUtil.showToast(mContext, R.string.open_book_failed, Toast.LENGTH_LONG);
//		final CommonDialog dialogBuilder = new CommonDialog(mContext,
//				R.style.dialog_commonbg);
//		dialogBuilder.setTitleInfo(mContext
//				.getString(R.string.shelf_friendly_tips));
//		dialogBuilder.setInfo(mContext
//				.getString(R.string.old_data_redownload_tip));
//		dialogBuilder.setInfoLineNum(3);
//		dialogBuilder.setRightButtonText(mContext
//				.getString(R.string.string_dialog_redownload));
//		dialogBuilder.setLeftButtonText(mContext
//				.getString(R.string.shelf_cancel));
//		dialogBuilder.setOnRightClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (isBookBelongsToCurrentUser(mInfo)) {
//					mOldBookListener.reDownloadOldData(mInfo, mContext);
//				} else {
//					showReLoginDialog();
//				}
//				dialogBuilder.dismiss();
//			}
//		});
//
//		dialogBuilder.setOnLeftClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialogBuilder.dismiss();
//			}
//		});
//		dialogBuilder.show();
	}

	private boolean isBookBelongsToCurrentUser(ShelfBook info) {
//		if (info.getTryOrFull().ordinal() < 2) {// 试读
//			return true;
//		}
//
//		if (mAccountManager.checkTokenValid()) {
//			return info.getUserId().equals(mAccountManager.getUserId())
//					|| info.getUserName()
//							.equals(mAccountManager.getUsername());
//		}

		return true;
	}

//	private void showReLoginDialog() {
//		final CommonDialog dialogBuilder = new CommonDialog(mContext,
//				R.style.dialog_commonbg);
//		dialogBuilder.setTitleInfo(mContext
//				.getString(R.string.shelf_friendly_tips));
//		dialogBuilder.setInfo(mContext
//				.getString(R.string.relogin_for_download_newbook));
//		dialogBuilder.setRightButtonText(mContext
//				.getString(R.string.unlogin_and_relogin));
//		dialogBuilder.setLeftButtonText(mContext
//				.getString(R.string.shelf_cancel));
//		dialogBuilder.setOnRightClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (mContext instanceof MainActivity) {
//					((MainActivity) mContext).changeTab(3, false);
//				}
//				dialogBuilder.dismiss();
//			}
//		});
//
//		dialogBuilder.setOnLeftClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialogBuilder.dismiss();
//			}
//		});
//		dialogBuilder.show();
//	}

	private boolean fileIsNewData(String bookdir) {

		ParserEpubN parse = new ParserEpubN();
		File file = new File(bookdir);
		if (file.exists()) {
			return isNewData(parse.parseVersion(bookdir + File.separator));
		}
		return isNewData(parse.readInnerZipFile(bookdir + ".epub", "dangdang"));
	}

	private boolean isNewData(String content) {
		String[] arr = content.split("=");
		if (arr.length <= 1) {
			return false;
		}
		if (Float.parseFloat(arr[1]) < Constants.OLD_FILE_VERSION_CODE) {
			return false;
		}
		return true;
	}
	
//	private Intent initReadMainActivityIntent(String refer) {
//		String bookdir = mInfo.getBookDir().trim();
//		Intent startReadIntent = new Intent(mContext, ReadMainActivity.class);
//		startReadIntent.putExtra(IntentK.ProductId, mInfo.getMediaId());
//		startReadIntent.putExtra(IntentK.BookName, mInfo.getTitle());
//		startReadIntent.putExtra(IntentK.BookDir, bookdir);
//		startReadIntent.putExtra(IntentK.ReferType, refer);
//
//		if (mInfo.getMediaId().startsWith(DangdangFileManager.TXT_BOOK_ID_PRE)) {
//			startReadIntent.putExtra(IntentK.BookEpub, bookdir);
//			startReadIntent.putExtra(IntentK.BookType,
//					BaseJniWarp.BOOKTYPE_DD_TXT);
//		} else if (mInfo.getMediaId().startsWith(
//				DangdangFileManager.EPUB_BOOK_THIRD_ID_PRE)) {
//			startReadIntent.putExtra(IntentK.BookEpub, bookdir);
//			startReadIntent.putExtra(IntentK.BookType,
//					BaseJniWarp.BOOKTYPE_THIRD_EPUB);
//		} else {
//			startReadIntent.putExtra(IntentK.BookEpub, bookdir + File.separator
//					+ mInfo.getMediaId() + ".epub");
//			startReadIntent.putExtra(IntentK.BookType,
//					BaseJniWarp.BOOKTYPE_DD_DRM_EPUB);
//		}
//		startReadIntent.putExtra(IntentK.IsBought, mInfo.getTryOrFull().ordinal() >= 2);
//		startReadIntent
//				.putExtra(IntentK.ReadProgress, mInfo.getReadProgress());
//
//		return startReadIntent;
//	}

	private OldBookListener mOldBookListener;

	public void setmOldBookListener(OldBookListener mOldBookListener) {
		this.mOldBookListener = mOldBookListener;
	}

	public interface OldBookListener {
		public void reDownloadOldData(ShelfBook info, Context context);
	}
}
