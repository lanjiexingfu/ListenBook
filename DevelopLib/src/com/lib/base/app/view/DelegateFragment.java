package com.lib.base.app.view;

import android.os.Bundle;
import android.view.View;

import com.lib.base.app.delegate.IDelegate;
import com.lib.custom.delegate.EventDelegate;
import com.lib.custom.delegate.TitleDelegate;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by chiely on 15/6/2.
 */
public class DelegateFragment extends ToolsFragment {
    private DelegateManager delegateManager;

    private TitleDelegate titleDelegate;
    private EventDelegate eventDelegate;

    /**
     * 默认不使用TitleDelegate
     */
    protected boolean useTitleDelegate() {
        return false;
    }

    /**
     * 默认不使用EventDelegate
     */
    protected boolean useEventDelegate() {
        return false;
    }

    public TitleDelegate getTitleDelegate() {
        return titleDelegate;
    }

    public EventDelegate getEventDelegate() {
        return eventDelegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventDelegate()) {
            addDelegate(eventDelegate = new EventDelegate(this));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
//        Assert.assertTrue("必须在主线程添加代理！", isMainThread());
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
