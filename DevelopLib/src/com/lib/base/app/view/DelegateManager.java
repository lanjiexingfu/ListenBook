package com.lib.base.app.view;

import com.lib.base.app.delegate.IDelegate;

import java.util.ArrayList;

/**
 * 代理类管理者
 * Created by chiely on 15/6/2.
 */
public class DelegateManager {

    private ArrayList<IDelegate> iDelegateContainer = new ArrayList<IDelegate>();

    public void add(IDelegate delegate) {
        iDelegateContainer.add(delegate);
    }

    public void add(IDelegate... delegates) {
        for (IDelegate delegate : delegates) {
            add(delegate);
        }
    }

    public void destroy() {
        for (IDelegate iDelegate : iDelegateContainer) {
            iDelegate.destroy();
        }
        iDelegateContainer.clear();
    }

}
