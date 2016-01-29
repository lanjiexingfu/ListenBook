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

public class DetailFragment extends BusinessFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void onSubViewCreated(View view, Bundle savedInstanceState) {
        super.onSubViewCreated(view, savedInstanceState);
    }

}
