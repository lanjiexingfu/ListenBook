package com.listen.util;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.lib.base.app.Configure;
import com.lib.base.utils.CheckUtils;
import com.listen.activity.R;
import com.listen.factor.ImageOptionFactor;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Chiely on 15/7/1.
 */
public class ImageLoaderUtils {

    public static void displayImage(ImageView imageView, String urlPath) {
        if (CheckUtils.isAvailable(urlPath)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getResUrl(urlPath), imageView, ImageOptionFactor.getDefaultImageOptions());
        } else {
            imageView.setImageResource(R.drawable.default_head);
        }
    }

    public static void displayImage(ImageView imageView, String urlPath, @DrawableRes int resId) {
        if (CheckUtils.isAvailable(urlPath)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getResUrl(urlPath), imageView, ImageOptionFactor.getImageOptions(resId));
        } else {
            imageView.setImageResource(resId);
        }
    }

    public static void displayImage(String imageUrl,ImageView imageView) {
        if (CheckUtils.isAvailable(imageUrl)) {
            ImageLoader.getInstance().displayImage(imageUrl, imageView, ImageOptionFactor.getDefaultImageOptions());
        } else {
            imageView.setImageResource(R.drawable.default_head);
        }
    }

}
