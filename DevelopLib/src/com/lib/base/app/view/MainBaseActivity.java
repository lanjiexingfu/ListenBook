package com.lib.base.app.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lib.base.app.BaseApp;
import com.lib.custom.config.EventInfo;
import com.lib.custom.config.EventType;
import com.lib.custom.config.INotifyType;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by Chiely on 15/7/16.
 */
public abstract class MainBaseActivity extends DelegateActivity {
    public static Class<? extends MainBaseActivity> SubMainActivityClz;
    
    protected boolean isResume = false;

    public MainBaseActivity() {
        SubMainActivityClz = this.getClass();
    }

    public void onEventMainThread(EventInfo eventInfo) {
        if (eventInfo.type == EventType.New_Server_Message) {
            if (isResume && eventInfo.arg1 == INotifyType.MESSAGE) {
                showNewMessageTip();
            }
        } else if (eventInfo.type == EventType.New_Server_Message_Click) {
            if (eventInfo.arg1 == INotifyType.MESSAGE) {
                openNotifyShow();
            } else {
                handleNotifyClick(eventInfo.arg1, (String) eventInfo.obj);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BaseApp.getInstance().bufData == null) {
            startActivity(new Intent(this, getSplashActivityCls()));
            finish();
            return;
        }
//      CrashReport.testJavaCrash();
        onSubCreated(savedInstanceState);

    }

    protected void onSubCreated(Bundle savedInstanceState) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 显示小红点
     */
    protected void showNewMessageTip() {
    }

    /**
     * 打开通知界面
     */
    protected void openNotifyShow() {
    }

    protected abstract void handleNotifyClick(int msgType, String data);

    protected abstract Class<? extends Activity> getSplashActivityCls();


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}

