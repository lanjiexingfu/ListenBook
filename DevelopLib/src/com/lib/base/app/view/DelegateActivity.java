package com.lib.base.app.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.lib.base.app.delegate.IDelegate;
import com.lib.custom.delegate.EventDelegate;
import com.lib.custom.delegate.TitleDelegate;

import junit.framework.Assert;

/**
 * Created by chiely on 15/6/2.
 */
public class DelegateActivity extends ToolsActivity {

    private DelegateManager delegateManager;

    private TitleDelegate titleDelegate;
    private EventDelegate eventDelegate;

    /**
     * 默认使用TitleDelegate
     */
    protected boolean useTitleDelegate() {
        return true;
    }

    /**
     * 默认不使用EventDelegate
     */
    protected boolean useEventDelegate() {
        return true;
    }

    public TitleDelegate getTitleDelegate() {
        return titleDelegate;
    }

    public EventDelegate getEventDelegate() {
        return eventDelegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventDelegate()) {
            addDelegate(eventDelegate = new EventDelegate(this));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (useTitleDelegate()) {
            addDelegate(titleDelegate = new TitleDelegate(this));
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        if (useTitleDelegate()) {
            addDelegate(titleDelegate = new TitleDelegate(this));
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        if (useTitleDelegate()) {
            addDelegate(titleDelegate = new TitleDelegate(this));
        }
    }

    /**
     * 添加代理，必须在主线程
     *
     * @param delegate
     */
    protected void addDelegate(IDelegate delegate) {
        Assert.assertTrue("必须在主线程添加代理！", isMainThread());
        if (delegateManager == null) {
            delegateManager = new DelegateManager();
        }
        delegateManager.add(delegate);
    }

    @Override
    public void onDestroy() {
        if (delegateManager != null) {
            delegateManager.destroy();
        }
        super.onDestroy();
    }
}
