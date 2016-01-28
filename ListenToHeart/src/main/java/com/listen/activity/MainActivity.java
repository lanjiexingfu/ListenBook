package com.listen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lib.base.app.view.BusinessActivity;
import com.lib.base.app.view.DelegateFragment;
import com.lib.base.utils.AppUtil;
import com.lib.base.utils.ToastUtil;
import com.listen.fragment.EnssenceFragment;
import com.listen.fragment.HotFragment;
import com.listen.fragment.ListenBarFragment;
import com.listen.fragment.RecommendFragment;

/**
 * Created by Chiely on 15/6/18.
 */

public class MainActivity extends BusinessActivity{

    private static int[] tabNameStrId = new int[]{
            R.string.main_tab_name_listen_bar,
            R.string.main_tab_name_mime,
            R.string.main_tab_name_discover,
            R.string.main_tab_name_account,
    };

    private static int[] titleStrId = new int[]{
            R.string.main_tab_name_listen_bar,
            R.string.main_tab_name_mime,
            R.string.main_tab_name_discover,
            R.string.main_tab_name_account,
    };

    private static Class<DelegateFragment>[] fClzs = new Class[]{
            ListenBarFragment.class,
            RecommendFragment.class,
            HotFragment.class,
            EnssenceFragment.class
    };

    private DelegateFragment[] fragments = new DelegateFragment[fClzs.length];

    private RadioGroup bottomTab;

    private FragmentManager fm;

    private int curId = -1;



    @Override
    protected void onSubCreated(Bundle savedInstanceState) {
        super.onSubCreated(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化组件
        setupView();
        fm = getSupportFragmentManager();
        curId = -1;
        bottomTab.check(0);

    }

    //初始化组件
    private void initView() {
        bottomTab = (RadioGroup) findViewById(R.id.bottom_tab);
        int childCount = bottomTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RadioButton childView = (RadioButton) bottomTab.getChildAt(i);
            childView.setId(i);
            childView.setText(getString(tabNameStrId[i]));
        }

    }

    private void setupView() {
        bottomTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchTab(checkedId);
            }
        });
    }


    private void switchTab(int tabId) {
        if (curId == tabId) {
            return;
        }
        //更换标题
        getTitleDelegate().hideLeftView();
        getTitleDelegate().setTitle(titleStrId[tabId]);
        getTitleDelegate().setRightDrawable(R.drawable.ic_search);
        getTitleDelegate().setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort("搜索");
            }
        });
        switchFragment(getFragment(tabId));
        curId = tabId;
    }

    private DelegateFragment getFragment(int index) {
        if (fragments[index] == null) {
            try {
                fragments[index] = fClzs[index].newInstance();
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }
        return fragments[index];
    }

    private void switchFragment(DelegateFragment newF) {
        FragmentTransaction ft = fm.beginTransaction();
        String fTag = newF.getClass().getSimpleName();
        ft.replace(R.id.main_center_fragment, newF, fTag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        AppUtil.doHomeHandle(this);
    }

}
