package com.lib.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.lib.base.app.DLog;

/**
 * Created by chiely on 15/4/5.
 */
public class DisplayUtils extends Basic {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DENSITY = -1;

    public static void init() {
        DisplayMetrics displayMetrics = getDisplayMetrics(getAppContext());
        DENSITY = displayMetrics.density;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;

        DLog.i("zcl", "DENSITY: " + DENSITY);
        DLog.i("zcl", "SCREEN_WIDTH: " + SCREEN_WIDTH);
        DLog.i("zcl", "SCREEN_HEIGHT: " + SCREEN_HEIGHT);
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        //DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5, xdpi=160.421, ydpi=159.497}
        //DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    public static int getScreenWidth() {
        if (SCREEN_WIDTH == -1) {
            init();
        }
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == -1) {
            init();
        }
        return SCREEN_HEIGHT;
    }

    /**
     * 描述：dip转换为px.
     */
    public static int dip2px(int dip) {
        return (int) (DENSITY * dip + .5f);
    }

    /**
     * 描述：px转换为dip.
     */
    public static float px2dip(float pxValue) {
        return pxValue / DENSITY;
    }

    /**
     * 描述：sp转换为px.
     */
    public static float sp2px(float spValue) {
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics(getAppContext()));
    }

    /**
     * 描述：px转换为sp.
     */
    public static float px2sp(float pxValue) {
        return pxValue / DENSITY;
    }

    /**
     * TypedValue官方源码中的算法，任意单位转换为PX单位
     *
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   对应单位的值
     * @param metrics 密度
     * @return px值
     */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
