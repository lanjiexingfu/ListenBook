package com.listen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.base.app.view.BusinessActivity;
import com.lib.base.app.view.BusinessFragment;
import com.listen.fragment.ChapterListFragment;
import com.listen.fragment.DetailFragment;
import com.listen.fragment.EnssenceFragment;
import com.listen.fragment.HotFragment;
import com.listen.fragment.RecommendFragment;
import com.listen.widget.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BookDetailActivity extends BusinessActivity{
    @InjectView(R.id.pagerSlidingTabStrip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    DetailFragment detailFragment;
    ChapterListFragment chapterListFragment;

    String[] titles = { "详情", "章节列表"};

    @Override
    protected void onSubCreated(Bundle savedInstanceState) {
        super.onSubCreated(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.inject(this);
        initView();
        viewPager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager(), titles));
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    private void initView(){
        getTitleDelegate().setTitle("汉武大帝");
    }

    public class TabAdapter extends FragmentPagerAdapter {
        String[] titles;
        public TabAdapter(FragmentManager fm,String[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (detailFragment == null) {
                        detailFragment = new DetailFragment();
                    }
                    return detailFragment;
                case 1:
                    if (chapterListFragment == null) {
                        chapterListFragment = new ChapterListFragment();
                    }
                    return chapterListFragment;
                default:
                    return null;
            }
        }
    }
}
