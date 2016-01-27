package com.lib.base.app.handler;

import android.os.Message;

/**
 * Created by chiely on 15/3/20.
 */
public interface IHandler {

    void onUIHandleMessage(Message msg);

    void onBackProcessHandleMessage(Message msg);

    void onCustomUIHandleMessage(Message msg);

    void onCustomUIHandleMessage(Message msg, int handlerType);

    void onCustomBackgroundHandleMessage(Message msg);

    void onCustomBackgroundHandleMessage(Message msg, int handlerType);

}
