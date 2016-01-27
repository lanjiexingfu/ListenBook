package com.lib.custom.http.listener;

public interface OnRequestErrorListener {
	void onError(int errorNo, String strMsg);
}
