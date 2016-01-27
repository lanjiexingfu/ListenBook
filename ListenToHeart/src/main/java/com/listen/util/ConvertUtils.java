package com.listen.util;

import android.support.annotation.ArrayRes;

import com.lib.base.utils.Basic;
import com.lib.base.utils.SafeValueUtil;
import com.listen.activity.R;

import java.util.HashMap;

/**
 * Created by Chiely on 15/7/1.
 */
public class ConvertUtils extends Basic {

    private static HashMap<String, String> ReceiveErrorMap = new HashMap<>();
    private static HashMap<String, String> PushErrMap = new HashMap<>();

    public static void init() {
        initMap(R.array.receive_error_code, R.array.receive_error_msg, ReceiveErrorMap);
        initMap(R.array.push_error_code, R.array.push_error_msg, PushErrMap);
    }

    private static void initMap(@ArrayRes int arrayResOfCode, @ArrayRes int arrayResOfMsg, HashMap<String, String> errMap) {
        String[] codeArray = getAppContext().getResources().getStringArray(arrayResOfCode);
        String[] msgArray = getAppContext().getResources().getStringArray(arrayResOfMsg);
        for (int i = 0; i < codeArray.length; i++) {
            errMap.put(codeArray[i], msgArray[i]);
        }
    }

    public static String getSafeStringFromArray(@ArrayRes int arrayRes, int index) {
        return SafeValueUtil.getString(getAppContext().getResources().getStringArray(arrayRes), index);
    }


    public static String getReceiveErrorMessage(String errCode) {
        return SafeValueUtil.getString(ReceiveErrorMap.get(errCode));
    }


}
