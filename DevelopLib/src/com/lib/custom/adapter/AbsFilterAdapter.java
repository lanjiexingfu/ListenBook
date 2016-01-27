
package com.lib.custom.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;

import com.lib.base.adapter.UniversalAdapter;
import com.lib.base.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地搜索自动过滤适配器
 */
public abstract class AbsFilterAdapter<T> extends UniversalAdapter<T> implements Filterable {

    private ArrayList<T> mUnfilteredData = new ArrayList<>();

    private SimpleFilter mFilter;

    protected AbsFilterAdapter(Context mContext, @LayoutRes int mLayoutId) {
        super(mContext, mLayoutId);
    }

    public AbsFilterAdapter(Context context, ArrayList<T> datas, @LayoutRes int layoutId) {
        super(context, datas, layoutId);
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SimpleFilter();
        }
        return mFilter;
    }

    private class SimpleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<T> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<T> newValues = new ArrayList<T>(count);

                for (int i = 0; i < count; i++) {
                    T h = unfilteredValues.get(i);
                    if (h != null) {

                        String str = getFilteredWord(h);

                        String[] words = str.split(" ");
                        int wordCount = words.length;

                        for (int k = 0; k < wordCount; k++) {
                            String word = words[k];

                            if (word.toLowerCase().contains(prefixString)) {
                                newValues.add(h);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mDatas = (ArrayList<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    public abstract String getFilteredWord(T t);

    public void cancelFilter() {
        mDatas = mUnfilteredData;
        if (CheckUtils.isAvailable(mDatas)) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    public int getUnfilteredDataCount() {
        return CheckUtils.isAvailable(mUnfilteredData) ? mUnfilteredData.size() : 0;
    }

    public T getUnfilteredDataItem(int position) {
        return CheckUtils.isAvailable(mUnfilteredData, position) ? mUnfilteredData.get(position) : null;
    }

    @Override
    public void updateData() {
        this.mUnfilteredData.clear();
        this.mUnfilteredData.addAll(mDatas);
    }
}
