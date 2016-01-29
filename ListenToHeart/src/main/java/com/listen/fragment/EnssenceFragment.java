package com.listen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lib.ab.view.pullview.AbPullToRefreshView;
import com.lib.base.adapter.UniversalAdapter;
import com.lib.base.adapter.UniversalViewHolder;
import com.lib.base.app.view.BusinessFragment;
import com.listen.activity.BookDetailActivity;
import com.listen.activity.R;
import com.listen.model.bean.BookBean;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EnssenceFragment extends BusinessFragment implements AbPullToRefreshView.OnHeaderRefreshListener,AbPullToRefreshView.OnFooterLoadListener{

    @InjectView(R.id.pull_refresh_view)
    AbPullToRefreshView abPullToRefreshView;
    @InjectView(R.id.listview)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enssence,null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void onSubViewCreated(View view, Bundle savedInstanceState) {
        super.onSubViewCreated(view, savedInstanceState);
        initPullRefreshView();
        setupView();
    }

    private void initPullRefreshView() {
        abPullToRefreshView.setLoadMoreEnable(true);
        abPullToRefreshView.setOnFooterLoadListener(this);
        abPullToRefreshView.getFooterView().setVisibility(View.GONE);
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
    }

    private void setupView(){
        ArrayList<BookBean> hotBooks = new ArrayList<>();
        for(int i = 0;i<20;i++){
            hotBooks.add(new BookBean());
        }
        listView.setAdapter(new UniversalAdapter<BookBean>(getActivity(), hotBooks, R.layout.hot_fragment_item) {
            @Override
            public void setupViews(UniversalViewHolder viewHolder, BookBean item, int position) {
                viewHolder.setImageResource(R.id.book_img,R.drawable.hot_book);
                viewHolder.setText(R.id.book_name, "盗墓笔记(全)");
                viewHolder.setText(R.id.book_author, "陈立乔");
                viewHolder.setText(R.id.book_intro,"五十年前，一群长沙土夫子（盗墓贼）挖到一部战国帛书，残篇中记载了一座奇特的战国古墓的位置，但那群土夫子在地下碰上了诡异事件，几乎全部身亡。");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), BookDetailActivity.class));
            }
        });
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
