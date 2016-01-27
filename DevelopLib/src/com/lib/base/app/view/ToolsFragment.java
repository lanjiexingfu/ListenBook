package com.lib.base.app.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;

import com.lib.R;
import com.lib.base.app.BaseApp;
import com.lib.base.utils.ToastUtil;

import org.androidannotations.annotations.ViewById;

/**
 * Created by chiely on 15/6/2.
 */
public class ToolsFragment extends HandlerFragment {

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

    public <T extends View> T findViewById(@IdRes int id) {
        return (T) getView().findViewById(id);
    }

}
