package com.listen.request;


import com.lib.base.app.BaseApp;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.base.bean.BufData;
import com.lib.base.http.AjaxParams;
import com.lib.base.utils.CheckUtils;
import com.lib.base.utils.JsonUtil;
import com.lib.base.utils.ToastUtil;
import com.lib.custom.config.EventInfo;
import com.lib.custom.config.EventType;
import com.lib.custom.http.BaseRequest;
import com.lib.custom.http.listener.OnRequestErrorListener;
import com.lib.custom.http.listener.OnRequestListener;
import com.listen.model.respond.BaseRespond;
import com.listen.util.ConvertUtils;

import java.lang.reflect.ParameterizedType;

import de.greenrobot.event.EventBus;

/**
 * Created by Chiely on 15/6/18.
 */
public class SimpleRequest<T extends BaseRespond> extends BaseRequest implements OnRequestListener {

    private Class<T> clz;

    private OnReceiveSuccessListener<T> onReceiveSuccessListener;
    private OnReceiveErrorListener onReceiveErrorListener;
    private OnRequestErrorListener mOnRequestErrorListener; // 请求错误的事件回调

    public SimpleRequest(BaseFragment fragment) {
        super(fragment);
        init();
    }

    public SimpleRequest(BaseActivity activity) {
        super(activity);
        init();
    }

    private void init() {
        setOnRequestListener(this);

        this.onReceiveErrorListener = new SimpleReceiveErrorListener();

        try {
            clz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception ignored) {
        }

        // accessToken	是	Api请求token
        BufData bufData = BaseApp.getInstance().bufData;
        if (bufData != null) {
            params.put("accessToken", bufData.accessToken);
        }

        subInit();
    }

    protected void subInit() {

    }

    public SimpleRequest<T> setClz(Class<T> clz) {
        this.clz = clz;
        return this;
    }

    public SimpleRequest<T> setOnRequestErrorListener(OnRequestErrorListener mOnRequestErrorListener) {
        this.mOnRequestErrorListener = mOnRequestErrorListener;
        return this;
    }

    public SimpleRequest<T> setOnReceiveSuccessListener(OnReceiveSuccessListener<T> onReceiveSuccessListener) {
        this.onReceiveSuccessListener = onReceiveSuccessListener;
        return this;
    }

    public SimpleRequest<T> setOnReceiveErrorListener(OnReceiveErrorListener onReceiveErrorListener) {
        this.onReceiveErrorListener = onReceiveErrorListener;
        return this;
    }

    @Deprecated
    @Override
    public void setParams(AjaxParams params) {
        super.setParams(params);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onLoading(long count, long current) {
    }

    @Override
    public void onSuccess(String resultJson) {

        T object = JsonUtil.getInstance().fromJson(resultJson, clz);

        if ("ok".equals(object.getResult()) || object.getResult()==null) {
            if (onReceiveSuccessListener != null) {
                onReceiveSuccessListener.onReceiveSuccess(object);
            }
        } else {
            if (onReceiveErrorListener != null) {
                onReceiveErrorListener.onReceiveError(object.getCode(), object.getResult());
            }
        }
    }

    @Override
    public void onError(int errorNo, String strMsg) {
        if (mOnRequestErrorListener != null) {
            mOnRequestErrorListener.onError(errorNo, strMsg);
        }
    }

    public static class SimpleReceiveErrorListener implements OnReceiveErrorListener {
        @Override
        public void onReceiveError(String code, String errMsg) {
            if ("10001".equals(code)) {
                EventBus.getDefault().post(new EventInfo(EventType.Logout));
            } else {
                String oldErrMsg = ConvertUtils.getReceiveErrorMessage(code);
                if (CheckUtils.isAvailable(oldErrMsg)) {
                    ToastUtil.showShort(oldErrMsg);
                } else {
                    ToastUtil.showShort(errMsg);
                }
            }
        }
    }
}
