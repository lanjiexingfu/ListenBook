package com.listen.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lib.base.FinalBitmap;
import com.lib.base.app.Configure;
import com.lib.base.utils.CheckUtils;
import com.lib.base.utils.DisplayUtils;
import com.lib.ext.widget.focuspicture.autoscrollviewpager.AutoScrollViewPager;
import com.lib.ext.widget.focuspicture.autoscrollviewpager.InfiniteLoopViewPagerAdapter;
import com.listen.activity.BookDetailActivity;
import com.listen.activity.MoreActivity;
import com.listen.activity.R;
import com.listen.model.bean.IndexBookCategoryBean;
import com.listen.widget.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

public class IndexListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<IndexBookCategoryBean> list;
    private FinalBitmap finalImageLoader;
    private AutoScrollViewPager autoScrollViewPager;
    private LinearLayout galleryPointLinear;
    private InfiniteLoopViewPagerAdapter autoScrollViewPagerAdapter;
    private int curPos = 0;
    private int wh;
    private View mTopView;

    public IndexListViewAdapter(Context context, List<IndexBookCategoryBean> list) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.wh = (DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(26)) / 4;
        this.list = list;
        this.finalImageLoader = FinalBitmap.create(context);
        this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);
    }

    @Override
    public int getCount() {
        return list == null ? 1 : list.size() + 1;
    }

    @Override
    public Object getItem(int arg0) {
        return list == null ? null : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return list == null ? null : arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return getTopView(convertView);
        } else {
            convertView = mInflater.inflate(R.layout.item_listview, null);
            TextView bookCategoryNameTV = (TextView) convertView.findViewById(R.id.book_category_name);//分类名称
            TextView bookCategoryMoreIV = (TextView) convertView.findViewById(R.id.book_category_more);//更多内容
            RelativeLayout horizonListviewRL = (RelativeLayout) convertView.findViewById(R.id.horizon_listview_rl);//更多内容
            bookCategoryMoreIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, MoreActivity.class));
                }
            });
            HorizontalListView horizontalListView = (HorizontalListView) convertView.findViewById(R.id.horizon_listview);//图片
            final IndexBookCategoryBean bean = list.get(position - 1);
            String bookCategoryName = null, bookCategoryImages = null;
            if (bean != null) {
                bookCategoryName = bean.getBookCategoryName();
                bookCategoryImages = bean.getBookCategoryImages();
            }
            //分类名称
            if (CheckUtils.isAvailable(bookCategoryName)) {
                bookCategoryNameTV.setText(bookCategoryName);
            }
            //是否含有图片
            if (CheckUtils.isAvailable(bookCategoryImages)) {
                horizonListviewRL.setVisibility(View.VISIBLE);
                initInfoImages(horizontalListView, bookCategoryImages);
            } else {
                horizonListviewRL.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    /**
     * 加载信息中包含的图片内容
     *
     * @param imgspath
     */
    public void initInfoImages(HorizontalListView horizontalListView, final String imgspath) {
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                list.add(imgs[i]);
            }
            String[] titles = {"怀师", "南怀瑾军校南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相", "南公庄严照", "怀师法相"};
            final int[] ids = {R.drawable.nanhuaijin_miss, R.drawable.nanhuaijin_school,
                    R.drawable.nanhuaijin_biguan, R.drawable.nanhuaijin,
                    R.drawable.nanhuaijin_zhuangyan, R.drawable.nanhuaijin_faxiang, R.drawable.nanhuaijin_zhuangyan, R.drawable.nanhuaijin_faxiang};
            HorizontalListViewAdapter hListViewAdapter = new HorizontalListViewAdapter(context, titles, ids);
            horizontalListView.setAdapter(hListViewAdapter);
            horizontalListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    context.startActivity(new Intent(context, BookDetailActivity.class));
                }
            });
        }
    }

    private View getTopView(View convertView) {
        if (mTopView == null) {
            mTopView = LayoutInflater.from(context).inflate(R.layout.layout_banner, null);
            autoScrollViewPager = (AutoScrollViewPager) mTopView.findViewById(R.id.auto_scroll_view_pager);
            galleryPointLinear = (LinearLayout) mTopView.findViewById(R.id.gallery_point_linear);
            initBanner();
        }
        return mTopView;
    }

    //初始化轮播图
    private void initBanner() {
        // 9:4
        int height = DisplayUtils.getScreenWidth() * 4 / 9;
        autoScrollViewPager.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        autoScrollViewPager.setInterval(2 * 1000); // 滑动间隔时间
//        autoScrollViewPager.setAutoScrollDurationFactor(500);
//        autoScrollViewPager.setSwipeScrollDurationFactor(500); // 滑动持续时间
        autoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE); // 循环模式

        autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (autoScrollViewPagerAdapter != null) {
                    changePointView(i % autoScrollViewPagerAdapter.getRealCount());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(context, getImageUrlList(), new BannerPagerAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(View view, int resPos) {
//                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("flag", 1);
//
//                String mediaUrl = "";
//
//                switch (resPos) {
//                    case 0:
//                        mediaUrl = StudentAPP.getStudent().getMediaUrl1();
//                        break;
//                    case 1:
//                        mediaUrl = StudentAPP.getStudent().getMediaUrl2();
//                        break;
//                    case 2:
//                        mediaUrl = StudentAPP.getStudent().getMediaUrl3();
//                        break;
//                    case 3:
//                        mediaUrl = StudentAPP.getStudent().getMediaUrl4();
//                        break;
//                    case 4:
//                        mediaUrl = StudentAPP.getStudent().getMediaUrl5();
//                        break;
//                }
//
//                if (ObjValid.isValid(mediaUrl)) {
//                    bundle.putString("bannerImageUrl", mediaUrl);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
            }
        });

        // 必须在setAdapter之前，否则#changePointView方法会空指针
        for (int i = 0; i < bannerPagerAdapter.getCount(); i++) {
            ImageView pointView = new ImageView(context);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setMargins(0, 0, 8, 0);
            pointView.setLayoutParams(ll);
            if (i == 0) {
                pointView.setBackgroundResource(R.drawable.qiehuan);
            } else {
                pointView.setBackgroundResource(R.drawable.qiehuanbai);
            }
            galleryPointLinear.addView(pointView);
        }
        autoScrollViewPager.setAdapter(bannerPagerAdapter);
        autoScrollViewPagerAdapter = (InfiniteLoopViewPagerAdapter) autoScrollViewPager.getAdapter();
        autoScrollViewPager.startAutoScroll();
    }

    /**
     * 获取轮播图片集合
     *
     * @return
     */
    @NonNull
    private ArrayList<Integer> getImageUrlList() {
        ArrayList<Integer> imageIds = new ArrayList<Integer>();
        imageIds.add(R.drawable.banner_1);
        imageIds.add(R.drawable.banner_2);
        imageIds.add(R.drawable.banner_3);
        imageIds.add(R.drawable.banner_4);
        return imageIds;
    }

    protected void changePointView(int newPos) {
        ImageView pointView = (ImageView) galleryPointLinear.getChildAt(curPos);
        ImageView curPointView = (ImageView) galleryPointLinear.getChildAt(newPos);
        pointView.setBackgroundResource(R.drawable.qiehuanbai);
        curPointView.setBackgroundResource(R.drawable.qiehuan);
        curPos = newPos;
    }
}
