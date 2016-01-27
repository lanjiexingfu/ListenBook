package com.lib.base.app.view;

import android.app.Activity;
import android.content.Intent;

import com.lib.base.app.BaseApp;
import com.lib.base.utils.ToastUtil;

/**
 * Created by chiely on 15/6/2.
 */
public class ToolsActivity extends HandlerActivity {

    public void showToast(String text) {
        ToastUtil.showShort(text);
    }

    public void showLongToast(String text) {
        ToastUtil.showLong(text);
    }

    protected boolean isMainThread() {
        return Thread.currentThread() == BaseApp.getUIThread();
    }

    public void startActivity(Class<? extends Activity> clz) {
        startActivity(new Intent(getActivity(), clz));
    }
}
