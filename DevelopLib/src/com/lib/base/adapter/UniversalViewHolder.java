package com.lib.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lib.base.utils.CheckUtils;

public class UniversalViewHolder {

    private View mConvertView;

    private final SparseArray<View> mViewArrays;

    private UniversalViewHolder(Context context, ViewGroup parent, @NonNull int layoutId) {
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mViewArrays = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    public static UniversalViewHolder getHolder(Context context, View convertView, ViewGroup parent, @NonNull int layoutId) {
        if (convertView == null) {
            return new UniversalViewHolder(context, parent, layoutId);
        }
        return (UniversalViewHolder) convertView.getTag();

    }

    public <T extends View> T getView(int resId) {
        View view = mViewArrays.get(resId);
        if (view == null) {
            view = mConvertView.findViewById(resId);
            mViewArrays.put(resId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public void setVisible(boolean isVisible, int... viewIds) {
        for (int viewId : viewIds) {
            setVisible(viewId, isVisible);
        }
    }

    public void setVisible(int viewId, boolean isVisible) {
        setVisibility(viewId, isVisible ? View.VISIBLE : View.GONE);
    }

    public void setVisibility(int viewId, int visibilty) {
        getView(viewId).setVisibility(visibilty);
    }

    public void setText(int viewId, CharSequence strText) {
        strText = strText != null && strText.length() > 0 ? strText : "";
        ((TextView) getView(viewId)).setText(strText);
    }

    public void setTextColor(int viewId, int colorId) {
        ((TextView) getView(viewId)).setTextColor(colorId);
    }

    public void setImageResource(int viewId, int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bmp) {
        ((ImageView) getView(viewId)).setImageBitmap(bmp);
    }

    public void setRadioCheck(int viewId, boolean isCheck) {
        ((RadioButton) getView(viewId)).setChecked(isCheck);
    }

    public void setBackground(int viewId, int resId) {
        getView(viewId).setBackgroundResource(resId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

}
