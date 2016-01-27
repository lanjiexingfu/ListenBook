package com.lib.base.app.delegate;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;

/**
 * 
 * Created by chiely on 15/3/27.
 *
 */
public abstract class BasicDelegate implements IDelegate {

    private BaseActivity activity;
    private BaseFragment fragment;

    public BasicDelegate(BaseActivity activity) {
        this.activity = activity;
        this.fragment = null;
        init();
    }

    public BasicDelegate(BaseFragment fragment) {
        this.activity = null;
        this.fragment = fragment;
        init();
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public BaseActivity getActivity() {
        return isActivity() ? activity : (BaseActivity) fragment.getActivity();
    }

    public boolean isActivity() {
        return activity != null;
    }

    public Context getContext() {
        return getActivity();
    }

    public boolean isAlive() {
        return isActivity() ? activity.isAlive() : fragment.isAlive();
    }

    public Intent getIntent() {
        return getActivity().getIntent();
    }

    public Bundle getArguments() {
        return isActivity() ? null : fragment.getArguments();
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) (isActivity() ? activity.findViewById(id) : fragment.getView().findViewById(id));
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    public String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        getActivity().unregisterReceiver(receiver);
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        getActivity().registerReceiver(receiver, filter);
    }

    public void sendBroadcast(Intent intent) {
        getActivity().sendBroadcast(intent);
    }
}
