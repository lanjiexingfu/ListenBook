package com.lib.ext.widget.focuspicture;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lib.R;
import com.lib.base.utils.UnitUtil;
import com.lib.ext.widget.focuspicture.adapter.ImagePagerAdapter;
import com.lib.ext.widget.focuspicture.autoscrollviewpager.AutoScrollViewPager;
import com.lib.ext.widget.focuspicture.listener.OnClickPicListener;

@SuppressLint("NewApi")
public class FocusPictureLayout extends LinearLayout implements OnPageChangeListener {

    private View           contentView;
    private Context        mContext;
    private LayoutInflater mInflater;

    private AutoScrollViewPager vp_focusPic;
    private ImagePagerAdapter   mAdapter;
    private LinearLayout        ll_point;
    private RelativeLayout      rl_focusPic;

    private List<String> imageUrl = new ArrayList<String>();
    //	private List<View> picList = new ArrayList<View>();

    //	private OnClickPicListener mOnClickPicListener;

    private long picStopTimeMillis = 3000;
    private int  pointSize         = 20;

    //焦点图下标图片的切换状态 0:是当前显示的，2：是还没显示
    private int[] focusPicPoint = {R.drawable.home_bright_point, R.drawable.home_dark_point};

    public FocusPictureLayout(Context context) {
        super(context);
        mContext = context;
        initView();

        //		String s;
    }

    public FocusPictureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public FocusPictureLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }

    /**
     * 设置下标点是否显示
     *
     * @param visibility
     */
    public void setPointVisibility(int visibility) {
        ll_point.setVisibility(visibility);
    }

    /**
     * @description:设置每张图片的停留时间
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    public void setPicStopTimeMillis(long time) {
        picStopTimeMillis = time;
    }

    /**
     * @description:设置焦点图显示时和未显示时的下标图片
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    public void setPointState(int res1, int res2) {
        focusPicPoint[0] = res1;
        focusPicPoint[1] = res2;
    }

    /**
     * @description:设置焦点图的高度
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    public void setFocusPicHeight(int height) {
        LayoutParams lp = (LayoutParams) rl_focusPic.getLayoutParams();
        lp.height = height;
        rl_focusPic.setLayoutParams(lp);
    }

    /**
     * @description:设置点击图片的事件回调
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    public void setOnClickPicListener(OnClickPicListener l) {
        mAdapter.setOnClickPicListener(l);
    }

    public void setPointSize(int size) {
        pointSize = size;
        for (int i = 0; i < ll_point.getChildCount(); i++) {
            LayoutParams params = new LayoutParams(pointSize, pointSize);
            ll_point.getChildAt(i).setLayoutParams(params);
        }
    }

    /**
     * @description:设置图片数据源
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    public void setPic(List<String> url) {
        imageUrl = url;
        //		for(int i = 0;i<imageUrl.size();i++){
        //			ImageView pic = new ImageView(mContext);
        //			pic.setImageResource(R.drawable.ic_loading_04);
        //			pic.setScaleType(ScaleType.FIT_XY);
        //			picList.add(pic);
        //		}

        //跟新数据源
        mAdapter = new ImagePagerAdapter(mContext, imageUrl);
        mAdapter.setInfiniteLoop(true);
        vp_focusPic.setAdapter(mAdapter);

        //		mAdapter.notifyDataSetChanged();

        addPoint(imageUrl.size());//增加下标图片
    }

    /**
     * @param url
     * @param isLoop 是否循环
     */
    public void setPic(List<String> url, boolean isLoop) {
        imageUrl = url;
        //跟新数据源
        mAdapter = new ImagePagerAdapter(mContext, imageUrl);
        mAdapter.setInfiniteLoop(isLoop);
        vp_focusPic.setAdapter(mAdapter);

        addPoint(imageUrl.size());//增加下标图片
    }

    /**
     * 开始进行焦点图的切换
     */
    public void play() {
        vp_focusPic.startAutoScroll();
    }

    /**
     * @description:初始化控件
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    private void initView() {
        mInflater = LayoutInflater.from(mContext);
        contentView = mInflater.inflate(R.layout.layout_focuspic, this, true);

        vp_focusPic = (AutoScrollViewPager) contentView.findViewById(R.id.vp_focusPic);
        vp_focusPic.setInterval(picStopTimeMillis);

        vp_focusPic.setOnPageChangeListener(this);

        ll_point = (LinearLayout) contentView.findViewById(R.id.ll_point);
        rl_focusPic = (RelativeLayout) contentView.findViewById(R.id.rl_focusPic);
    }

    /**
     * 设置存放point的layout的背景
     *
     * @param resId
     */
    public void setPointLayoutBg(int resId) {
        ll_point.setBackgroundResource(resId);
    }

    /**
     * 设置存放point的layout的背景
     *
     * @param color
     */
    public void setPointLayoutBgByColor(int color) {
        ll_point.setBackgroundColor(color);
    }

    /**
     * @description:增加下标图片
     * @param:
     * @return:
     * @author: Lee
     * @date: 2014-5-12
     */
    private void addPoint(int count) {
        for (int i = 0; i < count; i++) {
            ImageView point = new ImageView(mContext);
            LayoutParams params = new LayoutParams(pointSize, pointSize);
            params.leftMargin = UnitUtil.dp2px(mContext, 3);
            params.topMargin = UnitUtil.dp2px(mContext, 5);
            params.bottomMargin = UnitUtil.dp2px(mContext, 5);
            point.setLayoutParams(params);

            if (i == 0) {
                point.setImageResource(focusPicPoint[0]);
            } else {
                point.setImageResource(focusPicPoint[1]);
            }

            ll_point.addView(point);
        }

    }

    //	@Override
    //	public void onClick(View v) {
    //		if(mOnClickPicListener != null){
    //			int index = (Integer) v.getTag();
    //			mOnClickPicListener.onClickPic(index);
    //		}
    //	}

    @Override
    public void onPageScrollStateChanged(int index) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int index) {
        for (int i = 0; i < ll_point.getChildCount(); i++) {
            if (i != mAdapter.getPosition(index)) {
                ((ImageView) ll_point.getChildAt(i)).setImageResource(focusPicPoint[1]);
            } else {
                ((ImageView) ll_point.getChildAt(i)).setImageResource(focusPicPoint[0]);
            }
        }
    }


}
