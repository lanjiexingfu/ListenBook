package com.lib.base.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public abstract class UniversalAdapter<T> extends AbsBaseAdapter<T> {

    protected Context mContext;

    private int mLayoutId;

    protected UniversalAdapter(Context mContext, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
    }

    public UniversalAdapter(Context context, ArrayList<T> datas, @NonNull int layoutId) {
        super(datas);
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public T[] getDatasOfArray() {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UniversalViewHolder viewHolder = UniversalViewHolder.getHolder(mContext, convertView, parent, mLayoutId);
        setupViews(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void setupViews(UniversalViewHolder viewHolder, T item, int position);

}
