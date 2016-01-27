package com.lib.base.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 监听网络状态的广播
 */
public class NetWorkStateBroadcast extends BroadcastReceiver {

    private boolean flag = false;
    private onNetWorkChangeListener mNetWorkChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        //用户实时监听网络状态的回调
        if (mNetWorkChangeListener != null) {
            mNetWorkChangeListener.onNetWorkStateChange();
        }
    }

    /**
     * 设置网络状态的监听
     *
     * @param l
     */
    public void setOnNetWorkChangeListener(onNetWorkChangeListener l) {
        mNetWorkChangeListener = l;
    }

    public interface onNetWorkChangeListener {
        void onNetWorkStateChange();
    }
}
