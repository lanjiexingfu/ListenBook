package com.lib.custom.http.listener;

public interface OnRequestListener {
	void onStart();
	void onLoading(long count, long current);
	void onSuccess(String resultJson);
	void onError(int errorNo, String strMsg);
}
