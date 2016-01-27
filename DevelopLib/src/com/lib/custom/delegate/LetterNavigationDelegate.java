package com.lib.custom.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lib.R;
import com.lib.base.app.delegate.BasicDelegate;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.custom.adapter.LetterAdapter;
import com.lib.base.bean.LetterBean;
import com.lib.custom.widget.LetterListView;

/**
 * 字母导航列表
 * Created by chiely on 15/3/4.
 */
public class LetterNavigationDelegate extends BasicDelegate {

    private ListView listView;

    private LetterAdapter adapter;

    public LetterListView mLetterListView;

    private String mPrevLetter = "";

    public LetterNavigationDelegate(BaseActivity activity) {
        super(activity);
    }

    public LetterNavigationDelegate(BaseFragment fragment) {
        super(fragment);
    }

    public void setAdapter(LetterAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void init() {
        this.listView = findViewById(android.R.id.list);
        this.mLetterListView = findViewById(R.id.letter_view);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (adapter == null) {
                    return;
                }

                if (adapter.getCount() > 0) {
                    LetterBean letterBean = adapter.getItem(firstVisibleItem);
                    if (letterBean != null) {
                        String firstLetter = letterBean.firstLetter;
                        if (!TextUtils.isEmpty(firstLetter)) {
                            firstLetter = firstLetter.toLowerCase();
                            final String regEx = "[a-z]";
                            if (!firstLetter.matches(regEx)) {
                                firstLetter = "#";
                            }
                            if (!firstLetter.equals(mPrevLetter)) {
                                mLetterListView.changeChosenLetter(firstLetter);
                            }
                            mPrevLetter = firstLetter;
                        }
                    }
                }

            }
        });

        mLetterListView.setOnTouchingLetterChangedListener(new LetterListView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String letter) {
                if (adapter == null) {
                    return;
                }

                int position = adapter.getLetterPosition(letter);
                if (position != -1) {
                    LetterNavigationDelegate.this.listView.setSelection(position);
                }
            }

            @Override
            public void onScrolledAndHandUp() {
            }
        });
    }

    @Override
    public void destroy() {
    }

    public void showLetterView() {
        if (mLetterListView == null) {
            return;
        }
        mLetterListView.setVisibility(View.VISIBLE);
    }

    public void hideLetterView() {
        if (mLetterListView == null) {
            return;
        }
        mLetterListView.setVisibility(View.GONE);
    }
}
