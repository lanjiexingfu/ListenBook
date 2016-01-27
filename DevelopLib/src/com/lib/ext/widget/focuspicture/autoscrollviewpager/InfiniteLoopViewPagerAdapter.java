
package com.lib.ext.widget.focuspicture.autoscrollviewpager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 无限循环viewpager adapter
 */
public class InfiniteLoopViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "InfiniteLoopViewPagerAdapter";

    private static final boolean DEBUG = false;

    private PagerAdapter adapter;

    public InfiniteLoopViewPagerAdapter(PagerAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    public int getCount() {
        if (getRealCount() <= 1) // 一项就不滑动
            return getRealCount();

        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return adapter.getCount();
    }

    public int getRealItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = getRealposition(position);
        adapter.destroyItem(container, realPosition, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealposition(position);
        return adapter.instantiateItem(container, realPosition);
    }

    // 当项数不足4循环滑动会出现空白页，则2倍扩展 不小于4-循环滑动不空白
    private int getRealposition(int position) {
        int realCount = getRealCount();
        // if (realCount < 4 && realCount > 1) {
        // realCount = 2 * getRealCount();
        // }

        int realPosition = position % realCount;
        return realPosition;
    }

    /*
     * start 传递给包装adapter
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        adapter.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

}
