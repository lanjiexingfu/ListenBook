package com.listen.view;

import android.support.annotation.NonNull;

import com.lib.base.adapter.AbsBaseAdapter;
import com.lib.base.http.AjaxParams;
import com.listen.model.respond.BaseRespond;


import java.util.ArrayList;

/**
 * Created by Chiely on 15/7/9.
 */
public interface IPullRefreshList<Respond extends BaseRespond, DataVo, Adapter extends AbsBaseAdapter<DataVo>> {

    /**
     * 新建一个列表适配器实例，用于当前下拉刷新列表
     *
     * @return
     */
    @NonNull
    Adapter newAdapterInstance();

    /**
     * 获取请求列表数据的接口URL
     *
     * @return
     */
    @NonNull
    String getRequestDataUrl();

    /**
     * 获取需要的请求参数
     *
     * @param lastPage 下一页
     * @return
     */
    @NonNull
    AjaxParams getRequestParams(int lastPage);

    ArrayList<DataVo> transform(Respond response);
}
