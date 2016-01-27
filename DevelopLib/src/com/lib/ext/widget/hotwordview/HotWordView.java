package com.lib.ext.widget.hotwordview;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lib.R;
import com.lib.base.app.Configure;
import com.lib.base.utils.UnitUtil;

import java.util.List;

public class HotWordView extends LinearLayout {
    private Context mContext;
    private int width;
    private float density;
    private OnClickHotWordListener mOnClickHotWordListener;

    public HotWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        width = Configure.screenWidth;
        density = Configure.screenDensity;
    }

    public void setHotWord(List<String> listStr) {
        removeAllViews();
        int llIndex = 0;
        int btnMaxW = width - UnitUtil.dp2px(mContext, 20);
        int dp4 = UnitUtil.dp2px(mContext, 4);
        int dp2 = UnitUtil.dp2px(mContext, 2);
        int dp10 = UnitUtil.dp2px(mContext, 5);
        int dp5 = UnitUtil.dp2px(mContext, 5);
        int sp12 = density > 1.5 ? UnitUtil.dp2px(mContext, 5) : UnitUtil.dp2px(mContext, 10);

        int dp35 = UnitUtil.dp2px(mContext, 35);

        for (int index = 0; index < listStr.size(); index++) {
            Button btn = new Button(mContext);
            LinearLayout.LayoutParams btnlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            btnlp.height = dp35;
            btnlp.setMargins(dp2, dp2, dp2, dp2);
            btn.setLayoutParams(btnlp);
            btn.setBackgroundResource(R.drawable.ic_hotword);
            btn.setText(listStr.get(index));
            btn.setTextSize(sp12);
            btn.setTextColor(getResources().getColor(R.color.black));
            btn.setPadding(dp10, dp2, dp10, dp2);
            btn.setSingleLine(true);
            btn.setEllipsize(TruncateAt.END);
            btn.setMaxWidth(btnMaxW);
            btn.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickHotWordListener != null) {
                        mOnClickHotWordListener.onClickHotWord(((Button) v).getText().toString());
                    }
                }
            });

            int btnW = btn.getMeasuredWidth();

            if (getChildCount() == 0) {
                LinearLayout ll = new LinearLayout(mContext);
                LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                llLp.setMargins(dp5, 0, dp5, dp10);
                ll.setLayoutParams(llLp);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                addView(ll);
            }
            //获取某一行的child个数
            int childCount = ((LinearLayout) getChildAt(llIndex)).getChildCount();
            int totalW = 0;

            for (int i = 0; i < childCount; i++) {
                View childAt = ((LinearLayout) getChildAt(llIndex)).getChildAt(i);
                totalW += childAt.getMeasuredWidth() + dp4;
            }

            if (width - totalW - dp10 > btnW + dp4) {
                ((LinearLayout) getChildAt(llIndex)).addView(btn);
            } else {
                llIndex++;
                LinearLayout ll = new LinearLayout(mContext);
                LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                llLp.setMargins(dp5, 0, dp5, dp10);
                ll.setLayoutParams(llLp);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                addView(ll);
                ll.addView(btn);
            }

        }

    }

    /**
     * 设置点击热词时候的回调监听
     *
     * @param l
     */
    public void setOnClickHotWordListener(OnClickHotWordListener l) {
        mOnClickHotWordListener = l;
    }
}
