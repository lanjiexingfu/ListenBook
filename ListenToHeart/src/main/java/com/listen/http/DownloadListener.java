package com.listen.http;

public interface DownloadListener {

	void finish(Object object);

	void error(String msg);
}
