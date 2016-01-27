package com.lib.custom.delegate;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lib.R;
import com.lib.base.app.delegate.BasicDelegate;
import com.lib.base.app.view.DelegateActivity;
import com.lib.base.app.view.DelegateFragment;
import com.lib.base.utils.CheckUtils;
import com.lib.custom.adapter.AbsFilterAdapter;
import com.lib.custom.widget.ClearableAutoCompleteTextView;

/**
 * Created by chiely on 15/6/7.
 */
public class SearchDelegate extends BasicDelegate implements View.OnClickListener, ClearableAutoCompleteTextView.TextChangeListener, AdapterView.OnItemClickListener {


    private ClearableAutoCompleteTextView mSearchInputView;
    private ListView listView;
    private Button cancelSearchBtn;

    private AbsFilterAdapter adapter;

    private OnSearchResultItemClickListener onItemClickListener;

    public SearchDelegate(DelegateActivity activity) {
        super(activity);
    }

    public SearchDelegate(DelegateFragment fragment) {
        super(fragment);
    }

    @Override
    public void init() {
        this.listView = findViewById(android.R.id.list);
        this.mSearchInputView = findViewById(R.id.search_input);
        this.cancelSearchBtn = findViewById(R.id.btn_cancel_search);

        listView.setOnItemClickListener(this);
//        listView.setFocusable(true);
//        listView.setFocusableInTouchMode(true);
        listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        mSearchInputView.setListView(listView);
        mSearchInputView.setOnTextChangeClickListener(this);
        cancelSearchBtn.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    public void setOnItemClickListener(OnSearchResultItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public <T> void setAdapter(AbsFilterAdapter<T> adapter) {
        this.adapter = adapter;
        mSearchInputView.setAdapter(adapter);
    }

    public ClearableAutoCompleteTextView getSearchInputView() {
        return mSearchInputView;
    }

    public ListView getListView() {
        return listView;
    }

    public Button getCancelSearchBtn() {
        return cancelSearchBtn;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_cancel_search) {
            mSearchInputView.setText("");
            mSearchInputView.clearFocus();

            cancelSearchBtn.setVisibility(View.GONE);

        }
    }

    @Override
    public void onTextChanged(CharSequence newText) {
        if (CheckUtils.isAvailable(newText.toString())) {
            cancelSearchBtn.setVisibility(View.VISIBLE);
        } else {
            cancelSearchBtn.setVisibility(View.GONE);
        }
        mSearchInputView.clearFocus();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onItemClickListener != null && adapter != null) {
            int realPos = position - listView.getHeaderViewsCount();
            if (realPos >= 0 && realPos < adapter.getCount()) {
                onItemClickListener.onItemClick(view, adapter.getItem(realPos));
            }
        }

    }

    public interface OnSearchResultItemClickListener<T> {
        void onItemClick(View view, T obj);
    }
}
