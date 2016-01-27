package com.lib.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;

import com.lib.base.app.AppManager;
import com.lib.base.app.BaseApp;

/**
 * Created by chiely on 15/6/2.
 */
public class Basic {

    public static Application getAppContext() {
        return BaseApp.getInstance();
    }

    public static Activity getActivity() {
        return AppManager.getAppManager().currentActivity();
    }

    public static Resources getResources() {
        return getAppContext().getResources();
    }
}
