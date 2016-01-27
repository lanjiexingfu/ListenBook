package com.lib.custom.delegate;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.R;
import com.lib.base.app.delegate.BasicDelegate;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.base.utils.CheckUtils;
import com.lib.custom.view.TitleBar;

/**
 * Created by chiely on 15/3/27.
 */
public class TitleDelegate extends BasicDelegate {

    private TitleBar titleBar;

    public TitleDelegate(BaseActivity activity) {
        super(activity);
    }

    public TitleDelegate(BaseFragment fragment) {
        super(fragment);
    }

    public void init() {
        titleBar = findViewById(R.id.title_bar_layout);
    }

    @Override
    public void destroy() {
    }

    public void setBackground(@ColorRes int colorId) {
        titleBar.setBackground(colorId);
    }

    public void setBackgroundColor(int color) {
        titleBar.setBackgroundColor(color);
    }

    public void setTitleTextColor(int color) {
        titleBar.setTitleTextColor(color);
    }

    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    public void setTitle(String text) {
        titleBar.setTitle(text);
    }

    public void hideLeftView() {
        titleBar.hideLeftView();
    }

    public void showLeftTipsPoint() {
        titleBar.showLeftTipsPoint();
    }

    public void hideLeftTipsPoint() {
        titleBar.hideLeftTipsPoint();
    }

    public void setLeftOnClick(View.OnClickListener onClickListener) {
        titleBar.setLeftOnClick(onClickListener);
    }

    public void setLeftText(int titleId) {
        setLeftText(getString(titleId));
    }

    public void setLeftText(String text) {
        titleBar.setLeftText(text);
    }

    public void setLeftTextColor(int color) {
        titleBar.setLeftTextColor(color);
    }

    public void setLeftBackground(@DrawableRes int resId) {
        titleBar.setLeftBackground(resId);
    }

    public void setRightOnClick(View.OnClickListener onClickListener) {
        titleBar.setRightOnClick(onClickListener);
    }

    public void setRightText(int titleId) {
        setRightText(getString(titleId));
    }

    public void setRightText(String text) {
        titleBar.setRightText(text);
    }

    public void setRightTextBackground(int resDrawable) {
        titleBar.setRightTextBackground(resDrawable);
    }

    public void setRightTextColor(int color) {
        titleBar.setRightTextColor(color);
    }

    public void setRightDrawable(int rightIconId) {
        titleBar.setRightDrawable(rightIconId);
    }

    public void setRightDrawable(Drawable rightIcon) {
        titleBar.setRightDrawable(rightIcon);
    }

    public void hideRightView() {
        titleBar.hideRightView();
    }

    public void showRightTipsPoint() {
        titleBar.showRightTipsPoint();
    }

    public void hideRightTipsPoint() {
        titleBar.hideRightTipsPoint();
    }
}
