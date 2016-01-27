package com.lib.base.utils.camerautil;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 处理图片线程
 * @author Administrator
 *
 */
public class ImageDealTheard extends Thread{
	
	public static final int THEARD_START = 1;
	public static final int THEARD_END = 2;
	private String imagePath = "";
	private Handler handle;	
	
    /**
     * 获取图片处理线程
     * @return
     */
	public static ImageDealTheard getImageDealTheard(Handler handler){
    	ImageDealTheard mImageDealTheard = new ImageDealTheard();
    	mImageDealTheard.setPriority(Thread.NORM_PRIORITY + 2);
    	mImageDealTheard.setHandle(handler);
    	return mImageDealTheard;
    }
	
	@Override
	public void run() {
		super.run();
		
		Looper.prepare();
		Message msg1 = new Message();
		msg1.what = THEARD_START;
		getHandle().sendMessage(msg1);
		
//		CameraUtil.decodeFile(imagePath);
//		CameraUtil.compress(imagePath, 20);
		
		Message msg2 = new Message();
		msg2.obj = imagePath;
		msg2.what = THEARD_END;
		getHandle().sendMessage(msg2);
		
		Looper.loop();
	}

	
	
	public Handler getHandle() {
		return handle;
	}
	public void setHandle(Handler handle) {
		this.handle = handle;
	}
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
