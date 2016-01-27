package com.lib.base.app.view;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;

import com.lib.R;
import com.lib.base.app.AppManager;
import com.lib.base.app.BaseApp;
import com.lib.custom.config.EventInfo;
import com.lib.custom.config.EventType;

import junit.framework.Assert;

/**
 * Created by Chiely on 15/7/10.
 */
public class BusinessActivity extends DelegateActivity {

    public void onEvent(EventInfo eventInfo) {
        if (EventType.New_Server_Message_Click == eventInfo.type) {
            Assert.assertNotNull("MainActivity must start before " + getClass().getSimpleName() + " !", MainBaseActivity.SubMainActivityClz);
            AppManager.getAppManager().returnToActivity(MainBaseActivity.SubMainActivityClz);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (BaseApp.getInstance().bufData == null) {
//            finish();
//            return;
//        }
        onSubCreated(savedInstanceState);
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    protected void onSubCreated(Bundle savedInstanceState) {
    }
}
