package com.listen;

import android.content.Context;

import com.lib.base.app.BaseApp;
import com.lib.base.utils.SPUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ListenToHeartAPP extends BaseApp{

	@Override
	public void onCreate() {
		super.onCreate();
		SPUtil.setPreferenceName(getClass().getSimpleName());
		initImageLoader(this);
	}

	public static ListenToHeartAPP getInstance() {
		return BaseApp.getInstance();
	}

	// 初始化imageLoader
	@SuppressWarnings("deprecation")
	public void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}
}
