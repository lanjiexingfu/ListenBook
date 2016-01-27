package com.listen.http;

import com.lib.base.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SynFileDownloader {

	private String downloadUrl;
	private String fileName;
	private String savePath;

	public SynFileDownloader(String downloadUrl, String fileName,
			String savePath) {
		this.fileName = fileName;
		this.downloadUrl = downloadUrl;
		this.savePath = savePath;
	}

	public void download() {
		File saveFile = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			FileUtils.checkNotExistsFilePathAndCreate(savePath);
			saveFile = new File(savePath + "/" + fileName);
			URL url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			if (!(conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == 206)) {
				throw new Exception();
			}
			// 写到本地文件
			is = conn.getInputStream();
			fos = new FileOutputStream(saveFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (saveFile != null) {
				saveFile.delete();
			}
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}