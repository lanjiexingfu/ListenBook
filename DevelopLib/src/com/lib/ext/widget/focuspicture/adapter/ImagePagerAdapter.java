/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.lib.ext.widget.focuspicture.adapter;

import java.util.List;

import com.lib.R;
import com.lib.base.app.BaseApp;
import com.lib.ext.widget.focuspicture.libs.RecyclingPagerAdapter;
import com.lib.ext.widget.focuspicture.listener.OnClickPicListener;
import com.lib.ext.widget.focuspicture.utils.ListUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter implements OnClickListener {

    private Context            mContext;
    private List<String>       imageUrlList;
    private LayoutInflater     mInflater;
    private int                size;
    private boolean            isInfiniteLoop;
    private OnClickPicListener mOnClickPicListener;

    public ImagePagerAdapter(Context context, List<String> imageUrlList) {
        this.mContext = context;
        this.imageUrlList = imageUrlList;
        this.size = ListUtils.getSize(imageUrlList);
        isInfiniteLoop = false;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置点击图片监听
     *
     * @param onClickPicListener
     */
    public void setOnClickPicListener(OnClickPicListener onClickPicListener) {
        this.mOnClickPicListener = onClickPicListener;
    }


    @Override
    public int getCount() {
        if (ListUtils.getSize(imageUrlList) == 0) {
            return 0;
        }
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(imageUrlList);
    }

    /**
     * getInstance really position
     *
     * @param position
     * @return
     */
    public int getPosition(int position) {
        if (size == 0) {
            return 0;
        }
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.template_focuspic_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //        holder.imageView.setScaleType(ScaleType.FIT_XY);
        BaseApp.getBitmapInstance().display(holder.imageView, imageUrlList.get(getPosition(position)));
        //        holder.imageView.setImageResource(imageUrlList.getInstance(getPosition(position)));
        holder.imageView.setTag(position);
        holder.imageView.setOnClickListener(this);
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickPicListener != null) {
            int index = (Integer) v.getTag();
            mOnClickPicListener.onClickPic(index);
        }
    }
}
