package com.lib.base.app;

import android.util.Log;

/**
 * 日志信息输出类
 * <p>该类可自动或手动配置不同等级日志在发布模式下是否允许输出，
 * 并使用android.util.Log输出日志内容</p>
 */
public final class DLog {
    private static boolean mbDebugMode     = true;
    private static boolean mbLogDInRelease = true;
    private static boolean mbLogVInRelease = false;
    private static boolean mbLogIInRelease = false;
    private static boolean mbLogWInRelease = false;
    private static boolean mbLogEInRelease = true;

    public static void setInDebugMode(boolean bDebug) {
        mbDebugMode = bDebug;
    }

    public static void setLogDInRelease(boolean bLog) {
        mbLogDInRelease = bLog;
    }

    public static void setLogVInRelease(boolean bLog) {
        mbLogVInRelease = bLog;
    }

    public static void setLogIInRelease(boolean bLog) {
        mbLogIInRelease = bLog;
    }

    public static void setLogWInRelease(boolean bLog) {
        mbLogWInRelease = bLog;
    }

    public static void setLogEInRelease(boolean bLog) {
        mbLogEInRelease = bLog;
    }

    public static boolean isInDebugMode() {
        return mbDebugMode;
    }

    public static void d(String tag, String msg) {
        if (mbDebugMode && mbLogDInRelease) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (mbDebugMode && mbLogDInRelease) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (mbDebugMode && mbLogIInRelease) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (mbDebugMode && mbLogIInRelease) {
            Log.i(tag, msg, tr);
        }
    }

    public static void v(String tag, String msg) {
        if (mbDebugMode && mbLogVInRelease) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (mbDebugMode && mbLogVInRelease) {
            Log.v(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (mbDebugMode && mbLogWInRelease) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (mbDebugMode && mbLogWInRelease) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (mbDebugMode && mbLogEInRelease) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (mbDebugMode && mbLogEInRelease) {
            Log.e(tag, msg, tr);
        }
    }
}
