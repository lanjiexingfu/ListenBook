package com.lib.custom.delegate;

import com.lib.base.app.delegate.IDelegate;

import de.greenrobot.event.EventBus;

/**
 * Created by chiely on 15/3/30.
 */
public class EventDelegate implements IDelegate {

    private Object subscriber;

    public EventDelegate(Object subscriber) {
        this.subscriber = subscriber;
        init();
    }

    private void registerEventBus(Object subscriber) {
        try {
            EventBus.getDefault().register(subscriber);
        } catch (Exception e) {
        }
    }

    private void unregisterEventBus(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    @Override
    public void init() {
        registerEventBus(subscriber);
    }

    @Override
    public void destroy() {
        unregisterEventBus(subscriber);
    }
}
