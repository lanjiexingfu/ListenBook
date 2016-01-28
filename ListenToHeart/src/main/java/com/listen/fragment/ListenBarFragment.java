package com.listen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.base.app.view.BusinessFragment;
import com.listen.activity.R;
import com.listen.widget.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListenBarFragment extends BusinessFragment {
    @InjectView(R.id.pagerSlidingTabStrip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    RecommendFragment recommendFragment;
    HotFragment hotFragment;
    EnssenceFragment enssenceFragment;

    String[] titles = { "推荐", "热门", "精华1" };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_bar,null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void onSubViewCreated(View view, Bundle savedInstanceState) {
        super.onSubViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager(),titles));
        pagerSlidingTabStrip.setViewPager(viewPager);
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
                    if (recommendFragment == null) {
                        recommendFragment = new RecommendFragment();
                    }
                    return recommendFragment;
                case 1:
                    if (hotFragment == null) {
                        hotFragment = new HotFragment();
                    }
                    return hotFragment;
                case 2:
                    if (enssenceFragment == null) {
                        enssenceFragment = new EnssenceFragment();
                    }
                    return enssenceFragment;
                default:
                    return null;
            }
        }
    }


}
