package com.lib.base.app;

import com.lib.base.FinalBitmap;
import com.lib.base.FinalDb;
import com.lib.base.FinalHttp;
import com.lib.base.bean.BufData;
import com.lib.base.http.AjaxParams;
import com.lib.base.utils.DisplayUtils;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author daxiong
 * @ClassName: BaseAppcation
 * @Description: 修饰了Application，添加appManager，程序崩溃处理
 * @date 2013-7-24 下午12:16:35
 */
public class BaseApp<T extends BufData> extends Application implements FinalDb.DbUpdateListener {

    private static BaseApp mAppContext;

    // 主线程，也即UI线程
    private static Thread mMainThread;

    private static FinalDb     mFinalDb     = null;//数据库模块
    private static FinalBitmap mFinalBitmap = null;//图片加载模块
    private static FinalHttp   mFinalHttp   = null;//网络请求模块

    public AppManager   mAppManager;
    public CrashHandler crashHandler;

    public T bufData;

    @Override
    public void onCreate() {
        super.onCreate();
        initBaseHandle();

//        initBitmap(this);
//        initHttp();
//        initDB(this);
    }

    private void initBaseHandle() {
        mAppContext = this;
        mMainThread = Thread.currentThread();

        mAppManager = AppManager.getAppManager();// 获取appManager

        DisplayUtils.init();

        //		crashHandler = CrashHandler.getInstance();// 程序崩溃处理
        //		crashHandler.init(getApplicationContext());
    }

    public static <APP extends BaseApp> APP getInstance() {
        return (APP) mAppContext;
    }

    public static Thread getUIThread() {
        return mMainThread;
    }

    public void removeNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    /**
     * 获取FinalDb实例
     *
     * @return FinalDb
     */
    public static FinalDb getDbInstance() {
        return mFinalDb;
    }

    /**
     * 获取FinalBitmap实例
     *
     * @return FinalBitmap
     */
    public static FinalBitmap getBitmapInstance() {
        return mFinalBitmap;
    }

    /**
     * 获取FinalBitmap实例
     *
     * @return FinalHttp
     */
    public static FinalHttp getHttpInstance() {
        return mFinalHttp;
    }

    /**
     * 初始化HTTP请求
     */
    private void initHttp() {
        mFinalHttp = new FinalHttp();
        mFinalHttp.addHeader(Configure.header, Configure.charset);//配置http请求头
        mFinalHttp.configCharset(Configure.charset);
        mFinalHttp.configCookieStore(null);
        mFinalHttp.configRequestExecutionRetryCount(Configure.requestExecutionRetryCount);//请求错误重试次数
        mFinalHttp.configTimeout(Configure.timeOut);//超时时间
        mFinalHttp.configUserAgent(Configure.userAgent);//配置客户端信息
    }

    /**
     * 初始化bitmap
     *
     * @param context
     */
    private void initBitmap(Context context) {
        mFinalBitmap = FinalBitmap.create(context);
        mFinalBitmap.configBitmapLoadThreadSize(Configure.bitmapLoadThreadSize);
        mFinalBitmap.configDiskCachePath(Configure.IMAGE_PATH);
        mFinalBitmap.configDiskCacheSize(Configure.bitmapMemorySize);
    }

    /**
     * 初始化数据库
     *
     * @param context
     */
    private void initDB(Context context) {
        mFinalDb = FinalDb.create(context, Configure.DB_TARGET_DIR, Configure.DB_NAME, Configure.isDebug, Configure.DB_VERSION, this);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库升级要做的事
    }

    public void setBufData(T bufData) {
        this.bufData = bufData;
    }
}
