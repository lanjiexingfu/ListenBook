/**
 *  文件名:BaseFragment.java
 *  创建人:Sven Fang
 *  创建时间:2015-3-5
 */
package com.lib.base.app.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import junit.framework.Assert;

public class BaseFragment extends Fragment {

    private BaseActivity activity;
    private boolean isAlive = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Assert.assertTrue(activity.toString() + " must be BaseFragment!", activity instanceof BaseActivity);

        this.activity = (BaseActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private synchronized void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * 当界面生命周期处于onViewCreated之后和onDestroyView之前时为true,否则为false
     *
     * @return
     */
    public synchronized boolean isAlive() {
        return isAlive;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAlive(true);
    }

    @Override
    public void onDestroyView() {
        setAlive(false);
        super.onDestroyView();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void finish() {

    }
}
