package com.lib.base.app.view;

import android.os.Bundle;
import android.view.View;

import com.lib.base.app.BaseApp;

/**
 * Created by Chiely on 15/7/10.
 */
public class BusinessFragment extends DelegateFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (BaseApp.getInstance().bufData == null) {
//            return;
//        }
        onSubViewCreated(view, savedInstanceState);
    }

    protected void onSubViewCreated(View view, Bundle savedInstanceState){}
}
