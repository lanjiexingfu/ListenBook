package com.lib.ext.widget.zxing.listener;

public interface OnQueryCodeListener {

	void onResult(String text);
	void onFail();
}
