package com.lib.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lib.R;
import com.lib.base.utils.ViewUtil;

public class LoadingDialog extends Dialog {

	private Context context;
	private TextView tv_tip;

	private LoadingDialog(Context context) {
		super(context);
	}

	private LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		initView();
	}

	public static LoadingDialog create(Context context) {
		return new LoadingDialog(context, R.style.Loading_Dialog_Theme);
	}

	public void initView() {
		View view = LayoutInflater.from(this.context).inflate(R.layout.template_loading_dialog, null);
		tv_tip = ViewUtil.getView(view, R.id.tv_tip);
		setContentView(view);
	}

	//设置提示框内容
	public LoadingDialog setTipContent(@NonNull String tipContent) {
		tv_tip.setText(tipContent);
		return this;
	}
}
