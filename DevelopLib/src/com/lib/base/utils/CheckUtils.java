package com.lib.base.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;

/**
 * Created by chiely on 15/3/26.
 */
public class CheckUtils {

    public static boolean isAvailable(List list) {
        return list != null && !list.isEmpty();
    }

    public static <T> boolean isAvailable(T[] menus) {
        return menus != null && menus.length > 0;
    }

    public static boolean isAvailable(JSONArray jsonArray) {
        return jsonArray != null && jsonArray.length() > 0;
    }

    public static boolean isAvailable(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isAvailable(String str) {
        return !StrUtil.isEmpty(str);
    }

    public static boolean isAvailable(List list, int index) {
        return isAvailable(list) && list.size() > index;
    }

    public static <T> boolean isAvailable(T[] list, int index) {
        return isAvailable(list) && index >= 0 && index < list.length;
    }

    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) return false;
        return emailer.matcher(email).matches();
    }
}
