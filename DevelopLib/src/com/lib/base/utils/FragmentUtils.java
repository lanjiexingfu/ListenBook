package com.lib.base.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Chiely on 15/7/16.
 */
public class FragmentUtils {

    public static void replace(FragmentActivity activity,int id, Fragment childF) {
        activity.getSupportFragmentManager().beginTransaction().replace(id, childF).commitAllowingStateLoss();
    }

    public static void replace(Fragment groupF,int id, Fragment childF) {
        groupF.getChildFragmentManager().beginTransaction().replace(id, childF).commitAllowingStateLoss();
    }
}
