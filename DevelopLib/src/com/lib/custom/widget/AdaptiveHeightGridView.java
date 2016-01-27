package com.lib.custom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 根据ChildView自适应调整控件自身高度，主要应用于被嵌套在ScrollView或者其他滑动布局中
 */
public class AdaptiveHeightGridView extends GridView {

    private int numColumns;
    private int verticalSpacing;

    public AdaptiveHeightGridView(Context context) {
        super(context);
    }

    public AdaptiveHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int count = getCount();
        if (count != 0 && getMeasuredHeight() != 0) {
            int remain = count % numColumns;
            if (remain > 0) {
                remain = 1;
            }
            final int line = count / numColumns + remain;
            final int paddingTB = getPaddingTop() + getPaddingBottom();

            //                // 根据子控件测量值计算总高，在横竖屏切换的时候会出现子控件测量值还是切换之前的值的情况，故计算值不精准
            //            final View childV = getChildAt(0);
            //            System.out.println("childV:" +childV);
            //            // 必须判空，否则可能导致height为0
            //            if (childV != null) {
            //                // int totalHeight += line * childV.getMeasuredHeight() + ((line - 1) * verticalSpacing) + paddingTB;
            //            }
            
            // 根据当前测量值计算总高，缺点是在非嵌套于ScrollView类的布局中时计算不准确，所以谨慎使用
            int totalHeight = line * (getMeasuredHeight() - paddingTB) + ((line - 1) * verticalSpacing) + paddingTB;
            setMeasuredDimension(widthMeasureSpec, totalHeight);
        }
    }

    @Override
    public void setNumColumns(final int numColumns) {
        super.setNumColumns(numColumns);
        this.numColumns = numColumns;
    }

    @Override
    public void setVerticalSpacing(final int verticalSpacing) {
        super.setVerticalSpacing(verticalSpacing);
        this.verticalSpacing = verticalSpacing;
    }
}
