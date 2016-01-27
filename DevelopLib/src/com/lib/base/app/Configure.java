package com.lib.base.app;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.lib.R;
import com.lib.base.utils.AppUtil;
import com.lib.base.utils.CommonUtils;
import com.lib.base.utils.FileUtils;

import java.io.File;

/**
 * 项目配置相关
 */
public class Configure {

    public static boolean isDebug = true;                            //是否调试，是的话将打印数据库语句操作过程

    public static String APP_NAME;

    //数据库
    public static String DB_NAME;                    //数据名
    public static int DB_VERSION;                               //数据库版本

    //图片
    public static int bitmapLoadThreadSize = 5;                                 //读取图片的最大线程数
    public static int bitmapMemorySize = 1024 * 1024 * 10;                  //加载图片的最大内存
//    public static int loadingImage         = R.drawable.ic_launcher;            //设置加载时的图片

    //屏幕参数
    public static int screenWidth = 0;                                      //屏幕的宽度
    public static int screenHeight = 0;                                      //屏幕的高度
    public static float screenDensity = 0.0f;                                   //密度

    //HTTP请求
    public static String header = "Accept-Charset";         //Http头部
    public static String charset = "UTF-8";                  //字符格式
    public static int requestExecutionRetryCount = 3;                        //请求错误重试次数
    public static int timeOut = 10 * 1000;                //超时时间
    public static String userAgent = "Mozilla/5.0";            //配置客户端信息

    //程序内部文件夹
    public static String APP_PATH = "";
    //程序额外文件夹
    public static String APP_EXT_PATH = "";
    //缓存路径
    public static String CACHE = "";
    //图片文件夹
    public static String IMAGE_PATH = "";
    //数据库存放目录
    public static String DB_TARGET_DIR = "";
    //数据库文件绝对路径
    public static String DB_PATH = "";

    /**
     * 初始化参数
     *
     * @param context
     */
    public static void init(Activity context) {
        if (screenDensity == 0 || screenWidth == 0 || screenHeight == 0) {
            initDisplay(context);
            initPath(context);
        }
    }

    private static void initDisplay(Activity activity) {
        //屏幕参数初始化
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Configure.screenDensity = dm.density;
        Configure.screenHeight = dm.heightPixels;
        Configure.screenWidth = dm.widthPixels;
    }

    private static void initPath(Activity context) {

        APP_NAME = context.getString(R.string.main_folder_name);
        DB_NAME = context.getString(R.string.db_name);
        DB_VERSION = Integer.parseInt(context.getString(R.string.db_version));

        //程序安装路径
        APP_PATH = "/data/data/" + context.getPackageName() + File.separator;

        //程序额外需要的文件夹路径
        APP_EXT_PATH = AppUtil.hasSDCard() ? FileUtils.getSDRoot() + APP_NAME + File.separator : APP_PATH;

        //图片目录
        IMAGE_PATH = APP_EXT_PATH + "images" + File.separator;

        //缓存目录
        CACHE = APP_EXT_PATH + "data" + File.separator;

        // 数据库目录
        DB_TARGET_DIR = APP_PATH + "databases" + File.separator;

        // 数据库绝对路径
        DB_PATH = DB_TARGET_DIR  + Configure.DB_NAME;

        //创建文件夹
        CommonUtils.createFolder(APP_EXT_PATH);
        CommonUtils.createFolder(IMAGE_PATH);
        CommonUtils.createFolder(CACHE);
        CommonUtils.createFolder(DB_TARGET_DIR);

        DLog.d("APP_PATH：", APP_PATH);
        DLog.d("APP_EXT_PATH：", APP_EXT_PATH);
        DLog.d("IMAGE_PATH：", IMAGE_PATH);
        DLog.d("CACHE：", CACHE);
        DLog.d("DB_TARGET_DIR：", DB_TARGET_DIR);
    }
}
