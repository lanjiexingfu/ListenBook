package com.lib.custom.adapter;

import android.widget.BaseAdapter;

import com.lib.base.bean.LetterBean;
import com.lib.base.utils.CheckUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by chiely on 15/5/12.
 */
public abstract class LetterAdapter<Bean extends LetterBean> extends BaseAdapter {

    private ArrayList<Bean> datas = new ArrayList<Bean>();

    private HashMap<String, Integer> letterListIndexs = new HashMap<String, Integer>();

    public boolean isLetterItem(int position) {
        if (letterListIndexs.containsValue(position)) {
            return true;
        } else {
            return false;
        }
    }

    public int getLetterPosition(String letter) {
        if (letterListIndexs != null && letterListIndexs.containsKey(letter.toLowerCase())) {
            return letterListIndexs.get(letter.toLowerCase());
        } else {
            return -1;
        }
    }

    public void setData(ArrayList<Bean> datas) {
        if (!CheckUtils.isAvailable(datas)) {
            return;
        }

        synchronized (this) {
            clearAllData();

            // 设置拼音
            for (Bean data : datas) {
                data.init();
            }

            // 排序
            Collections.sort(datas, new Comparator<Bean>() {
                @Override
                public int compare(Bean lhs, Bean rhs) {
                    return lhs.pinYing.compareTo(rhs.pinYing);
                }
            });

            this.datas.addAll(datas);

            int i = 0;
            for (Bean data : this.datas) {
                if (letterListIndexs.get(data.firstLetter) == null) {
                    letterListIndexs.put(data.firstLetter, i);
                }
                i++;
            }
        }
    }

    /**
     * 清除数据
     */
    public void clearAllData() {
        synchronized (this) {
            datas.clear();
            letterListIndexs.clear();
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Bean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
