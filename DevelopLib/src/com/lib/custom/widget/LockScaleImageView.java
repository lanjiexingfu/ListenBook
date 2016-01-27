package com.lib.custom.widget;

import junit.framework.Assert;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 等比例缩放
 */
public class LockScaleImageView extends ImageView {

    public enum Orientation {
        Horizontal, Vertical
    }

    private Orientation orientation = Orientation.Horizontal;
    private double scale = 1.0f;

    public LockScaleImageView(Context context) {
        super(context);
    }

    public LockScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLockOrientation(final Orientation orientation) {
        this.orientation = orientation;
    }

    public void setScale(final double scale) {
        Assert.assertTrue("缩放比例必须大于等于0", scale >= 0);
        this.scale = scale;
    }

    String tip;

    public void setTip(final String tip) {
        this.tip = tip;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 根据测量值对另一方向的值进行缩放并重新设置为测量值
        if (orientation == Orientation.Horizontal) {
            final int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            final int measuredHeight = (int) (width * scale);
            if (tip != null) {
//                LogUtil.e("mydebug", tip + "---->Horizontal:{width :" + width + ", height:" + measuredHeight + "}");
            }
            setMeasuredDimension(width, measuredHeight);
        } else {
            final int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            final int measuredWidth = (int) (height * scale);
            if (tip != null) {
//                LogUtil.e("mydebug", tip + "---->Vertical:{width :" + measuredWidth + ", height:" + height + "}");
            }
            setMeasuredDimension(measuredWidth, height);
        }
    }

}
