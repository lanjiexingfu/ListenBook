package com.listen.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lib.ab.view.pullview.AbPullToRefreshView;
import com.lib.base.adapter.AbsBaseAdapter;
import com.lib.base.app.BaseApp;
import com.lib.base.app.delegate.BasicDelegate;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.base.bean.BufData;
import com.lib.base.http.AjaxParams;
import com.lib.base.utils.JsonUtil;
import com.lib.base.utils.ToastUtil;
import com.lib.base.utils.ViewUtil;
import com.lib.custom.http.BaseRequest;
import com.lib.custom.http.listener.OnRequestListener;
import com.listen.activity.R;
import com.listen.model.respond.BaseRespond;
import com.listen.request.SimpleRequest;

import java.util.ArrayList;

/**
 * Created by Chiely on 15/7/9.
 */
public class PullRefreshListDelegate<Respond extends BaseRespond, DataVo, Adapter extends AbsBaseAdapter<DataVo>> extends BasicDelegate implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    /**
     * 正式模式：请求网络数据
     * Debug模式：请求本地伪数据
     */
    private static final boolean DEBUG = false;

    private final IPullRefreshList<Respond, DataVo, Adapter> iPullRefreshList;

    private final Class<Respond> clz;

    private AbPullToRefreshView mAbPullToRefreshView;
    private ListView mListView;
    private Adapter mAdapter;

    private OnItemClickListener<DataVo> onItemClickListener;
    private OnChangeDataFinishListener onChangeDataFinishListener;

    /**
     * 当前页码：注意-必须请求成功后才进行更改
     */
    private int curPage;

    private int maxPageSize = 20;

    private boolean isEnd;

    public PullRefreshListDelegate(BaseActivity activity, @NonNull IPullRefreshList<Respond, DataVo, Adapter> iPullRefreshList, @NonNull Class<Respond> clz) {
        super(activity);
        this.iPullRefreshList = iPullRefreshList;
        this.clz = clz;
        setupView();
    }

    public PullRefreshListDelegate(BaseFragment fragment, @NonNull IPullRefreshList<Respond, DataVo, Adapter> iPullRefreshList, @NonNull Class<Respond> clz) {
        super(fragment);
        this.iPullRefreshList = iPullRefreshList;
        this.clz = clz;
        setupView();
    }

    @Override
    public void init() {
        if (getFragment() == null) {
            mAbPullToRefreshView = ViewUtil.getView(getActivity(), R.id.pull_refresh_view);
            mListView = ViewUtil.getView(getActivity(), android.R.id.list);
        } else {
            mAbPullToRefreshView = ViewUtil.getView(getFragment(), R.id.pull_refresh_view);
            mListView = ViewUtil.getView(getFragment(), android.R.id.list);
        }

        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);

        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

    }

    private void setupView() {
        //使用自定义的Adapter
        mListView.setAdapter(mAdapter = iPullRefreshList.newAdapterInstance());

        //item被点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, mAdapter.getItem(position), position);
                }
            }
        });
    }

    @Override
    public void destroy() {

    }

    public AbPullToRefreshView getPullToRefreshView() {
        return mAbPullToRefreshView;
    }

    public ListView getListView() {
        return mListView;
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public void setOnItemClickListener(OnItemClickListener<DataVo> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnChangeDataFinishListener(OnChangeDataFinishListener onChangeDataFinishListener) {
        this.onChangeDataFinishListener = onChangeDataFinishListener;
    }

    private void addDataList(ArrayList<DataVo> newData, String toastText) {
        if (newData != null && !newData.isEmpty()) {
            mAdapter.addData(newData);

            if (newData.size() < maxPageSize) {
                isEnd = true;
            } else {
                isEnd = false;
            }
        } else {
            isEnd = true;
//            ToastUtil.showShort(toastText);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void clearData() {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();
    }

    private void refreshDataSuccess(ArrayList<DataVo> newData) {
        mAdapter.clearData();
        addDataList(newData, "暂时无相关信息！");

        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(0);
            }
        }, 50);

        curPage = 1;

        if (onChangeDataFinishListener != null) {
            onChangeDataFinishListener.onRefreshFinish();
        }
    }

    private void loadMoreDataSuccess(ArrayList<DataVo> newData) {
        addDataList(newData, "已经没有更多信息了！");
        curPage++;

        if (onChangeDataFinishListener != null) {
            onChangeDataFinishListener.onLoadMoreFinish();
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        requestRefreshData();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        requestLoadMoreData();
    }

    public void headerRefreshing() {
        mAbPullToRefreshView.headerRefreshing();
    }

    public void requestRefreshData() {
        BaseRequest request = new BaseRequest(getActivity());
        request.setUrl(iPullRefreshList.getRequestDataUrl());
        request.setParams(comboParams(iPullRefreshList.getRequestParams(1)));
        request.setOnRequestListener(new OnRequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoading(long count, long current) {

            }

            @Override
            public void onSuccess(String resultJson) {
                Respond respond = JsonUtil.getInstance().fromJson(resultJson, clz);

                if ("ok".equals(respond.getResult())) {
                    refreshDataSuccess(iPullRefreshList.transform(respond));
                } else {
                    new SimpleRequest.SimpleReceiveErrorListener().onReceiveError(respond.getCode(), respond.getResult());
//                    clearData();
                }
                mAbPullToRefreshView.onHeaderRefreshFinish();
            }

            @Override
            public void onError(int errorNo, String strMsg) {
                clearData();
                mAbPullToRefreshView.onHeaderRefreshFinish();
            }
        });

        request.post();
    }

    private void requestLoadMoreData() {
        if (isEnd) {
            ToastUtil.showShort("已经没有更多信息了！");
            mAbPullToRefreshView.onFooterLoadFinish();
            return;
        }

        BaseRequest request = new BaseRequest(getActivity());
        request.setUrl(iPullRefreshList.getRequestDataUrl());
        request.setParams(comboParams(iPullRefreshList.getRequestParams(curPage + 1)));
        request.setOnRequestListener(new OnRequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoading(long count, long current) {

            }

            @Override
            public void onSuccess(String resultJson) {
                Respond respond = JsonUtil.getInstance().fromJson(resultJson, clz);

                if ("ok".equals(respond.getResult())) {
                    loadMoreDataSuccess(iPullRefreshList.transform(respond));
                } else {
                    new SimpleRequest.SimpleReceiveErrorListener().onReceiveError(respond.getCode(), respond.getResult());
                }
                mAbPullToRefreshView.onFooterLoadFinish();
            }

            @Override
            public void onError(int errorNo, String strMsg) {
                mAbPullToRefreshView.onFooterLoadFinish();
            }
        });

        request.post();
    }

    @NonNull
    private AjaxParams comboParams(AjaxParams params) {
        BufData bufData = BaseApp.getInstance().bufData;
        if (bufData != null) {
            params.put("accessToken", bufData.accessToken);
        }
        return params;
    }

    public interface OnChangeDataFinishListener {
        void onRefreshFinish();
        void onLoadMoreFinish();
    }

    public interface OnItemClickListener<DataVo> {
        void onItemClick(AdapterView<?> parent, View v, DataVo dataVo, int position);
    }
}
