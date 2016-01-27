package com.lib.base.app.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.lib.base.app.AppManager;
import com.lib.base.app.Configure;
import com.lib.base.broadcast.NetWorkStateBroadcast;

public class BaseActivity extends FragmentActivity {

    /**
     * 使用Log来调试时使用此变量，定位包名
     */
    public String TAG = this.getClass().getName();

    private boolean isAlive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 将activity推入管理栈
        AppManager.getAppManager().addActivity(this);
        Configure.init(this);
        super.onCreate(savedInstanceState);
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    protected void onDestroy() {
        isAlive = false;
        if (netWorkStateBroadcast != null) {
            unregisterReceiver(netWorkStateBroadcast);
        }
        //销毁时activity出栈
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    /**
     * 设置默认内容
     *
     * @param layoutResID
     */
    public void setContentViewDefault(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        this.setContentView(view);
    }

    /**
     * 跳转Activity
     *
     * @param activity
     * @param bundle
     */
    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 获取当前activity实例
     *
     * @return
     */
    protected BaseActivity getActivity() {
        return this;
    }

    /**
     * 网络状态广播实例，加入被注册则在destroy的时候必须反注册 *
     */
    protected NetWorkStateBroadcast netWorkStateBroadcast;

    /**
     * 注册网络状态变化监听
     */
    protected void registerNetWorkChangeReceiver() {
        if (netWorkStateBroadcast == null) {
            netWorkStateBroadcast = new NetWorkStateBroadcast();
            netWorkStateBroadcast.setOnNetWorkChangeListener(onChangeListener);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateBroadcast, filter);
        }
    }

    /**
     * 当网络状态改变
     */
    protected void onNetWorkChange() {
    }

    /**
     * 设置网络状态的监听
     */
    private NetWorkStateBroadcast.onNetWorkChangeListener onChangeListener = new NetWorkStateBroadcast.onNetWorkChangeListener() {

        @Override
        public void onNetWorkStateChange() {
            onNetWorkChange();
        }
    };
}
