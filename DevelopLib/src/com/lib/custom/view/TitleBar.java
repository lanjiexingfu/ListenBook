package com.lib.custom.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.R;
import com.lib.base.utils.CheckUtils;

/**
 * Created by Chiely on 15/8/10.
 */
public class TitleBar extends LinearLayout {

    private TextView mTitleShow;
    private ImageView mLeftBtn;
    private ImageView mLeftPoint;
    private TextView mLeftText;
    private View mLeftLayout;
    private ImageView mRightBtn;
    private TextView mRightText;
    private ImageView mRightPoint;
    private View mRightLayout;

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initSelfParams();
        initChildViews();
    }

    private void initSelfParams() {
        setId(R.id.title_bar_layout);
        addView(LayoutInflater.from(getContext()).inflate(R.layout.layout_title_bar, this, false));
    }

    private void initChildViews() {
        mLeftBtn = findChildById(R.id.title_bar_left_btn);
        mLeftText = findChildById(R.id.title_bar_left_text);
        mLeftPoint = findChildById(R.id.title_bar_left_point);
        mLeftLayout = findChildById(R.id.title_bar_left_layout);
        mTitleShow = findChildById(R.id.title_bar_title_show);
        mRightBtn = findChildById(R.id.title_bar_right_btn);
        mRightText = findChildById(R.id.title_bar_right_text);
        mRightPoint = findChildById(R.id.title_bar_right_point);
        mRightLayout = findChildById(R.id.title_bar_right_layout);

        View.OnClickListener leftOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    Activity activity = ((Activity) getContext());
                    activity.finish();
                }
            }
        };
        mLeftLayout.setOnClickListener(leftOnClickListener);
        mRightLayout.setOnClickListener(leftOnClickListener);
    }

    public void setBackground(@ColorRes int colorId) {
        setBackgroundColor(getResources().getColor(colorId));
    }

    public void setBackgroundColor(int color) {
        setBackgroundColor(color);
    }

    public void setTitleTextColor(int color) {
        mTitleShow.setTextColor(color);
    }

    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    public void setTitle(String text) {
        if (CheckUtils.isAvailable(text)) {
            mTitleShow.setText(text);
        }
    }

    public void hideLeftView() {
        mLeftLayout.setVisibility(View.INVISIBLE);
    }

    public void showLeftTipsPoint() {
        mLeftPoint.setVisibility(View.VISIBLE);
    }

    public void hideLeftTipsPoint() {
        mLeftPoint.setVisibility(View.GONE);
    }

    public void setLeftOnClick(View.OnClickListener onClickListener) {
        mLeftLayout.setOnClickListener(onClickListener);
    }

    public void setLeftText(int titleId) {
        setLeftText(getString(titleId));
    }

    public void setLeftText(String text) {
        if (CheckUtils.isAvailable(text)) {
            mLeftLayout.setVisibility(View.VISIBLE);
            mLeftBtn.setVisibility(View.INVISIBLE);
            mLeftText.setVisibility(View.VISIBLE);
            mLeftText.setText(text);
        }
    }

    public void setLeftTextColor(int color) {
        mLeftText.setTextColor(color);
    }

    public void setLeftBackground(@DrawableRes int resId) {
        mLeftBtn.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.INVISIBLE);
        mLeftBtn.setImageResource(resId);
    }

    public void setRightOnClick(View.OnClickListener onClickListener) {
        mRightLayout.setOnClickListener(onClickListener);
    }

    public void setRightText(int titleId) {
        setRightText(getString(titleId));
    }

    public void setRightText(String text) {
        if (CheckUtils.isAvailable(text)) {
            mRightLayout.setVisibility(View.VISIBLE);
            mRightBtn.setVisibility(View.INVISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText(text);
        }
    }

    public void setRightTextBackground(int resDrawable) {
        mRightText.setBackgroundResource(resDrawable);
        mRightText.setGravity(Gravity.CENTER);
    }

    public void setRightTextColor(int color) {
        mRightText.setTextColor(color);
    }

    public void setRightDrawable(int rightIconId) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.INVISIBLE);
        mRightBtn.setImageResource(rightIconId);
    }

    public void setRightDrawable(Drawable rightIcon) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.INVISIBLE);
        mRightBtn.setImageDrawable(rightIcon);
    }

    public void hideRightView() {
        mRightLayout.setVisibility(View.INVISIBLE);
    }

    public void showRightTipsPoint() {
        mRightPoint.setVisibility(View.VISIBLE);
    }

    public void hideRightTipsPoint() {
        mRightPoint.setVisibility(View.GONE);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findChildById(@IdRes int id) {
        return (T) findViewById(id);
    }

    @NonNull
    private String getString(int titleId) {
        return getContext().getResources().getString(titleId);
    }
}
