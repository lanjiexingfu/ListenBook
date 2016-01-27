package com.lib.base.utils;

import java.util.List;

import android.widget.TextView;

/**
 * Created by chiely on 15/4/13.
 */
public class SafeValueUtil {

    public static String getString(String source) {
        return CheckUtils.isAvailable(source) ? source : "";
    }

    public static String getString(TextView textView) {
        return textView != null ? textView.getText().toString().trim() : "";
    }

    public static String getString(String[] array, int index) {
        return CheckUtils.isAvailable(array, index) ? array[index] : "";
    }

    public static String getString(List<String> list, int index) {
        return CheckUtils.isAvailable(list, index) ? list.get(index) : "";
    }

    public static int getSize(List list) {
        return CheckUtils.isAvailable(list) ? list.size() : 0;
    }

    public static <T> T getItem(List<T> list, int index) {
        return CheckUtils.isAvailable(list, index) ? list.get(index) : null;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    /**
     * 字符串转浮点型
     *
     * @param str
     * @param defValue
     * @return
     */
    public static float toFloat(String str, float defValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转浮点型
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static float toFloat(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toFloat(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }
}
