package com.listen.util;

import com.lib.base.utils.Basic;
import com.listen.activity.R;

/**
 * Created by Chiely on 15/7/1.
 */
public class UrlUtil extends Basic {

    public static String getServerUrl(String urlPath) {
        return getAppContext().getString(R.string.url_server, urlPath);
    }

    public static String getResUrl(String urlPath) {
        return getAppContext().getString(R.string.url_server_res, urlPath);
    }

    public static String getServerIP(String serverIP) {
        return getAppContext().getString(R.string.server_ip, serverIP);
    }
}
