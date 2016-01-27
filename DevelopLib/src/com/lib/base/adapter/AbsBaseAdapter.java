
package com.lib.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.BaseAdapter;

import com.lib.base.utils.CheckUtils;

/**
 * 抽象适配器.提供修改适配器数据的方法，这些方法必须运行在UI线程中，否则会抛出异常
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mDatas = new ArrayList<T>();

    public AbsBaseAdapter() {
    }

    public AbsBaseAdapter(List<T> datas) {
        setData(datas);
    }

    public AbsBaseAdapter(T[] datas) {
        setData(datas);
    }

    public void setData(List<T> datas) {
        clearData();
        addData(datas);
    }

    public void setData(T[] datas) {
        clearData();
        addData(datas);
    }

    public void addData(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
        }
        updateData();
    }

    public void addData(T[] datas) {
        if (datas != null && datas.length > 0) {
            for (T data : datas) {
                addData(data);
            }
        }
    }

    public void addData(T data) {
        if (data != null) {
            this.mDatas.add(data);
        }
        updateData();
    }

    public void addData(int index, T data) {
        if (index >= 0 && index <= mDatas.size()) {
            if (data != null) {
                this.mDatas.add(index, data);
            }
        }
        updateData();
    }

    public void addData(int index, List<T> datas) {
        if (index >= 0 && index <= mDatas.size()) {
            if (datas != null && datas.size() > 0) {
                this.mDatas.addAll(index, datas);
            }
        }
        updateData();
    }

    public void removeData(T data) {
        if (data != null) {
            this.mDatas.remove(data);
        }
        updateData();
    }

    public void removeData(int index) {
        if (index >= 0 && index < mDatas.size()) {
            this.mDatas.remove(index);
        }
        updateData();
    }

    public void clearData() {
        this.mDatas.clear();
        updateData();
    }

    protected void updateData() {

    }

    public ArrayList<T> getDatas() {
        return this.mDatas;
    }

    @SuppressWarnings("unchecked")
    public T[] getDatasOfArray() {
        return CheckUtils.isAvailable(getDatas()) ? (T[]) getDatas().toArray() : null;
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return this.mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < this.mDatas.size()) {
            return this.mDatas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        // 简单返回数据索引号，可以重写本方法，返回真实数据对应的ID
        return position;
    }

}
