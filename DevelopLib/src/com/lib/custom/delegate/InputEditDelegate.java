package com.lib.custom.delegate;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lib.R;
import com.lib.base.app.delegate.BasicDelegate;
import com.lib.base.app.view.BaseActivity;
import com.lib.base.app.view.BaseFragment;
import com.lib.base.utils.AppUtil;
import com.lib.base.utils.CheckUtils;
import com.lib.base.utils.ToastUtil;

/**
 * 输入编辑
 * Created by chiely on 15/3/27.
 */
public class InputEditDelegate extends BasicDelegate {

    private View     mMainLayout;
    private EditText mInputEdit;
    private TextView mSendButton;

    public interface OnSendListener {
        public void onSend(View v, String inputText);
    }

    private OnSendListener onSendListener;

    public InputEditDelegate(BaseActivity activity) {
        super(activity);
    }

    public InputEditDelegate(BaseFragment fragment) {
        super(fragment);
    }

    public void init() {
        mMainLayout = findViewById(R.id.input_edit_layout);
        mInputEdit = findViewById(R.id.input_edit);
        mSendButton = findViewById(R.id.send_btn);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String input = mInputEdit.getText().toString().trim();
                if (!CheckUtils.isAvailable(input)) {
                    ToastUtil.showShort("不能发送空的信息！");
                    return;
                }

                if (onSendListener != null) {
                    onSendListener.onSend(v, input);
                }
            }
        });

    }

    @Override
    public void destroy() {
    }

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }

}
