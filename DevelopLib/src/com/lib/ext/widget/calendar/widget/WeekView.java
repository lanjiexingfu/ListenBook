package com.lib.ext.widget.calendar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lib.R;

/**
 * Created by Blaz Solar on 24/02/14.
 */
public class WeekView extends ViewGroup {

    private static final String TAG = "WeekView";
    private Paint paint;
    private Context context;
    private boolean isDrawLine;
    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setWillNotDraw(false);
        init();
    }

    private void init(){
        paint = new Paint();
        // 设置颜色
        paint.setColor(context.getResources().getColor(R.color.h_divider));
        paint.setStrokeWidth((float)5);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int maxSize = widthSize / 7;
        int baseSize = 0;

        int cnt = getChildCount();
        for(int i = 0; i < cnt; i++) {

            View child = getChildAt(i);

            if(child.getVisibility() == View.GONE) {
                continue;
            }

            child.measure(
                    MeasureSpec.makeMeasureSpec(maxSize, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(maxSize, MeasureSpec.AT_MOST)
            );

            baseSize = Math.max(baseSize, child.getMeasuredHeight());

        }

        for (int i = 0; i < cnt; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            child.measure(
                    MeasureSpec.makeMeasureSpec(baseSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(baseSize, MeasureSpec.EXACTLY)
            );

        }

        setMeasuredDimension(widthSize, getLayoutParams().height >= 0 ? getLayoutParams().height : baseSize + getPaddingBottom() + getPaddingTop());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int cnt = getChildCount();

        int width = getMeasuredWidth();
        int part = width / cnt;

        for(int i = 0; i < cnt; i++) {

            View child = getChildAt(i);
            if(child.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = child.getMeasuredWidth();

            int x = i * part + ((part - childWidth) / 2);
            child.layout(x, 0, x + childWidth, child.getMeasuredHeight());

        }

    }

    public void setIsDrawLine(boolean isDrawLine) {
        this.isDrawLine = isDrawLine;
        refreshDrawableState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制一个矩形
        if(isDrawLine){
            canvas.drawLine(0,getHeight(), getWidth(), getHeight(), paint);
        }

    }
}
