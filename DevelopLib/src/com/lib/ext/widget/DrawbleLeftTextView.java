package com.lib.ext.widget;

/**
 * Created by Edward on 2015/11/4.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Edward on 2015/11/4.
 */
public class DrawbleLeftTextView extends TextView {

    public DrawbleLeftTextView(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawbleLeftTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawbleLeftTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            if (drawableLeft != null || drawableRight != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                if (drawableLeft != null)
                    drawableWidth = drawableLeft.getIntrinsicWidth();
                else if (drawableRight != null) {
                    drawableWidth = drawableRight.getIntrinsicWidth();
                }
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
