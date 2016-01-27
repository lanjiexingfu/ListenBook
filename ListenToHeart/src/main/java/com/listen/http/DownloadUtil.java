package com.listen.http;

public class DownloadUtil {

    /**
     * 获取照片
     *
     * @param url      图片链接地址
     * @param listener
     */
    public static void getFile(String savePath, String url, DownloadListener listener) {
//        String fileName = FileUtils.getFileName(url);
//        String filePath = savePath + fileName;
//        if(new File(Configure.APP_EXT_PATH + "exam_picture_and_vedio/exam_picture/"+fileName).exists()){
//            listener.finish(Configure.APP_EXT_PATH + "exam_picture_and_vedio/exam_picture/"+fileName);
//        }else{
//            FileUtils.checkNotExistsFilePathAndCreate(savePath);
//            File file = new File(filePath);
//            if (file.exists()) {
//                listener.finish(filePath);
//            } else {
//                Downloader down = new Downloader(url, savePath, listener);
//                down.execute();
//            }
//        }
    }
}
