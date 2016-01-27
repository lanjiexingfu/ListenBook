package com.lib.custom.http;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.lib.base.FinalHttp;
import com.lib.base.app.DLog;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.base.http.AjaxCallBack;
import com.lib.base.http.AjaxParams;
import com.lib.base.utils.CheckUtils;
import com.lib.base.utils.ToastUtil;
import com.lib.custom.http.listener.OnRequestListener;
import com.lib.custom.view.LoadingDialog;

public class BaseRequest {

    private static final int RETRY_COUNT = 1; // 重试次数
    private static final int TIMEOUT = 10 * 1000; // 30秒的超时时间

    private String TAG = "BaseRequest";

    private Context mContext; // 上下文

    protected BaseActivity activity;
    private BaseFragment fragment;

    private FinalHttp request; // 请求
    private Dialog loadingDialog; // 请求时候的加载框

    private boolean isShowLoadDialog = false;
    private boolean isShowError = true;

    public String url = ""; // 接口地址
    protected AjaxParams params; // 请求的参数
    private OnRequestListener mOnRequestListener; // 请求事件的回调

    public BaseRequest(BaseFragment fragment) {
        this((BaseActivity) fragment.getActivity());
        this.fragment = fragment;
    }

    public BaseRequest(BaseActivity activity) {
        this.activity = activity;

        TAG = getClass().getName();

        this.mContext = activity;
        request = new FinalHttp();
        request.configRequestExecutionRetryCount(RETRY_COUNT);
        request.configTimeout(TIMEOUT);

        params = new AjaxParams();
    }

    public boolean isAlive() {
        return fragment != null ? fragment.isAlive() : activity.isAlive();
    }

    public void setParams(AjaxParams params) {
        this.params = params;
    }

    public boolean isShowError() {
        return isShowError;
    }

    public void setShowError(boolean isShowToast) {
        this.isShowError = isShowToast;
    }

    public void setLoadingDialog(boolean isShow) {
        this.isShowLoadDialog = isShow;
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.create(mContext);
        }
    }

    /**
     * 设置请求事件监听
     */
    public void setOnRequestListener(OnRequestListener l) {
        mOnRequestListener = l;
    }

    /**
     * 发起请求
     */
    public void post() {
        showDialog();
        if (params != null) {
            postWithParams();
        } else {
            postNoParams();
        }
    }

    /**
     * 带参数的请求,请求方式:post
     */
    private void postWithParams() {
        request.post(getUrl(), params, callBack);
        Log.e(TAG, getUrl() + "?" + params.toString());
    }

    /**
     * 不带参数的请求,请求方式:post
     */
    private void postNoParams() {
        request.post(getUrl(), callBack);
        Log.e(TAG, getUrl());
    }

    /**
     * 显示加载框
     */
    private void showDialog() {
        if (loadingDialog == null || !isShowLoadDialog) {
            return;
        }

        try {
            loadingDialog.show();
        } catch (Exception e) {
            loadingDialog = null;
            e.printStackTrace();
        }
    }

    /**
     * 关闭加载框
     */
    private void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * getInstance set
     */
    private String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * 显示错误提示
     *
     * @param errorNo
     * @param strMsg
     */
    private void showErrorMsg(int errorNo, String strMsg) {
        Log.e(TAG, "errorNo:" + errorNo);
        if (!isShowError) {
            return;
        }

        if (CheckUtils.isAvailable(strMsg) && strMsg.contains("ConnectTimeoutException")) {
            ToastUtil.showShort("服务器连接超时，请检查服务器地址是否设置错误，或者联系客服人员！");
        } else {

            switch (errorNo) {
                case 0:
                    ToastUtil.showShort("由于网络原因无法链接到服务器...");
                    break;
                case 400:
                    ToastUtil.showShort("服务器地址请求无效，请检查服务器地址是否设置错误！");
                    break;
                case 403:
                    ToastUtil.showShort("服务器地址禁止访问，请联系客服人员！");
                    break;
                case 404:
                    ToastUtil.showShort("服务器无法找到，请检查服务器地址是否设置错误，或者联系客服人员！");
                    break;
                case 405:
                    ToastUtil.showShort("资源被禁止，无法访问，请联系客服人员！");
                    break;
                case 410:
                    ToastUtil.showShort("服务器地址永远不可用，请检查服务器地址是否设置错误！");
                    break;
                case 414:
                    ToastUtil.showShort("服务器地址请求无效，请求URI太长！");
                    break;
                case 500:
                    ToastUtil.showShort("内部服务器错误，请联系客服人员！");
                    break;
                case 502:
                    ToastUtil.showShort("网关错误，请检查网络相关配置！");
                    break;
                case 503:
                    ToastUtil.showShort("因暂时超载或临时维护，您的Web 服务器目前无法处理HTTP 请求!");
                    break;
            }
        }
    }

    /**
     * 请求的回调
     */
    private AjaxCallBack<String> callBack = new AjaxCallBack<String>() {

        @Override
        public void onStart() {
            if (!isAlive()) return;

            if (mOnRequestListener != null) {
                mOnRequestListener.onStart();
            }
            showDialog();
        }

        @Override
        public void onLoading(long count, long current) {
            if (!isAlive()) return;

            if (mOnRequestListener != null) {
                mOnRequestListener.onLoading(count, current);
            }
        }

        @Override
        public void onSuccess(String t) {
            dismissDialog();
            if (!isAlive()) return;
            DLog.d(TAG, t);
            if (mOnRequestListener != null) {
                mOnRequestListener.onSuccess(t);
            }
        }

        @Override
        public void onFailure(Throwable t, int errorNo, String strMsg) {
            dismissDialog();
            if (!isAlive()) return;
            t.printStackTrace();// 打印异常
            showErrorMsg(errorNo, strMsg);
            mOnRequestListener.onError(errorNo, strMsg);
        }
    };
}
