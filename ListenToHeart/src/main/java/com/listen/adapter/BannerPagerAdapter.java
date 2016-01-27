package com.listen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lib.base.utils.CheckUtils;
import com.listen.activity.R;

import java.util.ArrayList;

public class BannerPagerAdapter extends PagerAdapter {

    private ArrayList<ImageView> viewList; // 图片地址list
    private LayoutInflater inflater;
    private OnBannerItemClickListener onBannerItemClickListener;

//    public BannerPagerAdapter(Context context, ArrayList<String> imageUrls) {
//        this(context, imageUrls, null);
//    }

//    public BannerPagerAdapter(Context context, ArrayList<String> imageUrls, OnBannerItemClickListener onBannerItemClickListener) {
//        this.inflater = LayoutInflater.from(context);
//        this.onBannerItemClickListener = onBannerItemClickListener;
//        this.viewList = getViewList(imageUrls);
//    }

    public BannerPagerAdapter(Context context, ArrayList<Integer> imageIds) {
        this(context, imageIds, null);
    }

    public BannerPagerAdapter(Context context, ArrayList<Integer> imageIds, OnBannerItemClickListener onBannerItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.onBannerItemClickListener = onBannerItemClickListener;
        this.viewList = getViewList(imageIds);
    }

//    private ArrayList<ImageView> getViewList(ArrayList<String> imageUrls) {
//        ArrayList<ImageView> viewList = new ArrayList<>();
//        if (CheckUtils.isAvailable(imageUrls)) {
//            int count = imageUrls.size();
//            viewList.ensureCapacity(count);
//            for (int i = 0; i < count; i++) {
//                String url = imageUrls.get(i);
//                final ImageView imageView = (ImageView) inflater.inflate(R.layout.banner_item, null);
////                ImageLoaderUtils.displayImage(url,imageView);
//                if (onBannerItemClickListener != null) {
//                    final int finalI = i;
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onBannerItemClickListener.onItemClick(imageView, finalI);
//                        }
//                    });
//                }
//                viewList.add(imageView);
//            }
//        }
//        return viewList;
//    }

    private ArrayList<ImageView> getViewList(ArrayList<Integer> imgIds) {
        ArrayList<ImageView> viewList = new ArrayList<>();
        if (CheckUtils.isAvailable(imgIds)) {
            int count = imgIds.size();
            viewList.ensureCapacity(count);
            for (int i = 0; i < count; i++) {
                int imageId = imgIds.get(i);
                final ImageView imageView = (ImageView) inflater.inflate(R.layout.banner_item, null);
                imageView.setImageResource(imageId);
                if (onBannerItemClickListener != null) {
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBannerItemClickListener.onItemClick(imageView, finalI);
                        }
                    });
                }
                viewList.add(imageView);
            }
        }
        return viewList;
    }

    public int getCount() {
        return viewList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        if (container.indexOfChild(viewList.get(position)) != -1) {
            container.removeView(viewList.get(position));
        }
        container.addView(viewList.get(position), 0);
        return viewList.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }


    public interface OnBannerItemClickListener {
        void onItemClick(View view, int resPos);
    }
}
