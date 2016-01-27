package com.listen.factor;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.listen.activity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Chiely on 15/7/1.
 */
public class ImageOptionFactor {

    private static DisplayImageOptions DefaultImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head)
            .showImageForEmptyUri(R.drawable.default_head)
            .showImageOnFail(R.drawable.default_head)
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();


    public static DisplayImageOptions getDefaultImageOptions() {
        return DefaultImageOptions;
    }

    public static DisplayImageOptions getImageOptions(@DrawableRes int resId) {
        return new DisplayImageOptions.Builder().showImageOnLoading(resId)
                .showImageForEmptyUri(resId)
                .showImageOnFail(resId)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }
}
