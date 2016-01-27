package com.listen.http;

import android.os.AsyncTask;
import java.io.File;
import com.lib.base.utils.FileUtils;

/**
 * 异步下载主要逻辑代码
 */
public class Downloader extends AsyncTask<String, Integer, Object> {
    private String fileName;
    private String downloadUrl;
    private String savePath;
    private DownloadListener listener = null;

    public Downloader(String downloadUrl, String savePath, DownloadListener listener) {
        this.downloadUrl = downloadUrl;
        this.listener = listener;
        this.savePath = savePath;
    }

    @Override
    protected Object doInBackground(String... params) {
        if (fileName == null) {
            fileName = FileUtils.getFileName(downloadUrl);
        }

        File file = new File(savePath + "/" + fileName);
        if (!file.exists()) {
            SynFileDownloader down = new SynFileDownloader(downloadUrl, fileName, savePath);
            down.download();
        }
        return savePath + "/" + fileName;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (result != null) {

            if (listener != null) {
                listener.finish(result);
            }
        } else {
            if (listener != null) {
                listener.error("download error");
            }
        }
    }
}