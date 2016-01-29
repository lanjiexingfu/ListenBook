package com.listen.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lib.ab.view.pullview.AbPullToRefreshView;
import com.lib.base.app.view.BusinessFragment;
import com.lib.base.utils.ToastUtil;
import com.listen.activity.R;
import com.listen.adapter.IndexListViewAdapter;
import com.listen.model.bean.IndexBookCategoryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecommendFragment extends BusinessFragment implements AbPullToRefreshView.OnHeaderRefreshListener,AbPullToRefreshView.OnFooterLoadListener{

    @InjectView(R.id.pull_refresh_view)
    AbPullToRefreshView abPullToRefreshView;
    @InjectView(R.id.listview)
    ListView listView;
    private IndexListViewAdapter listViewAdapter;
    private List<IndexBookCategoryBean> listBean;
    private String imgs1;
    private String imgs2;
    private String imgs3;
    private String imgs4;
    private String imgs5;
    private String imgs6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void onSubViewCreated(View view, Bundle savedInstanceState) {
        super.onSubViewCreated(view, savedInstanceState);
        initPullRefreshView();
        initData();
    }

    private void initPullRefreshView() {
        abPullToRefreshView.setLoadMoreEnable(true);
        abPullToRefreshView.setOnFooterLoadListener(this);
        abPullToRefreshView.getFooterView().setVisibility(View.GONE);
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
    }


    private void initData() {
        /*
		 * 添加模拟数据，正常情况下，这些数据是从服务端获取的
		 * 此次添加了七条数据，对应Gridview中图片数量
		 */
        //模拟用户发布的图片，路径用"#"隔开
        listBean=new ArrayList<IndexBookCategoryBean>();
        imgs1 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58";
        imgs2 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58#http://t10.baidu.com/it/u=374721516,1427740298&fm=58";
        imgs3 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58#http://t10.baidu.com/it/u=374721516,1427740298&fm=58#http://t11.baidu.com/it/u=3158457091,3429860559&fm=58";
        imgs4 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58#http://t10.baidu.com/it/u=374721516,1427740298&fm=58#http://t11.baidu.com/it/u=3158457091,3429860559&fm=58#http://t12.baidu.com/it/u=732128477,3149312025&fm=58";
        imgs5 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58#http://t10.baidu.com/it/u=374721516,1427740298&fm=58#http://t11.baidu.com/it/u=3158457091,3429860559&fm=58#http://t12.baidu.com/it/u=732128477,3149312025&fm=58#http://t11.baidu.com/it/u=2722915642,3232472693&fm=58";
        imgs6 = "http://t10.baidu.com/it/u=2565424359,3856609610&fm=58#http://t10.baidu.com/it/u=374721516,1427740298&fm=58#http://t11.baidu.com/it/u=3158457091,3429860559&fm=58#http://t12.baidu.com/it/u=732128477,3149312025&fm=58#http://t11.baidu.com/it/u=2722915642,3232472693&fm=58#http://t12.baidu.com/it/u=1313963321,225077119&fm=58";

        IndexBookCategoryBean bean = null;
        for (int i = 0; i < 7; i++) {
            bean = new IndexBookCategoryBean();
            switch (i) {
                case 0:
                    bean.setBookCategoryName("小编推荐");
                    bean.setBookCategoryImages(imgs1);
                    break;
                case 1:
                    bean.setBookCategoryName("新书推荐");
                    bean.setBookCategoryImages(imgs2);
                    break;
                case 2:
                    bean.setBookCategoryName("热门节目");
                    bean.setBookCategoryImages(imgs3);
                    break;
                case 3:
                    bean.setBookCategoryName("有声小说");
                    bean.setBookCategoryImages(imgs4);
                    break;
                case 4:
                    bean.setBookCategoryName("文学名著");
                    bean.setBookCategoryImages(imgs5);
                    break;
                case 5:
                    bean.setBookCategoryName("少儿天地");
                    bean.setBookCategoryImages(imgs6);
                    break;
                case 6:
                    bean.setBookCategoryName("娱乐天地");
                    bean.setBookCategoryImages(imgs6);
                    break;
            }
            listBean.add(bean);//添加进list
        }

        listViewAdapter = new IndexListViewAdapter(getActivity(), listBean);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    public void onUIHandleMessage(Message msg) {
        switch (msg.what){
            case 1 :
                onRefreshFinish();
                break;
        }
        super.onUIHandleMessage(msg);
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    sendHandler(getUIHandler(),1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        view.onFooterLoadFinish();
    }

    private void onRefreshFinish() {
        if (abPullToRefreshView != null) {
            abPullToRefreshView.onHeaderRefreshFinish();
        }
    }

}
