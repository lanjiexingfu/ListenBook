/**
 * Copyright (C) 2012 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 */
package com.lib.base.utils;


import android.view.Gravity;
import android.widget.Toast;


/**
 * 
 * @ClassName: ToastUtil 
 * @Description: Toast封装
 * @author daxiong
 * @date 2013-8-9 下午3:37:52 
 *
 */
@SuppressWarnings("all")
public class ToastUtil extends Basic {

	/**
	 * 短时显示
	 * @param context	上下文
	 * @param msg	提示内容
	 */
	public static void showShort(String msg) {
		if (!CheckUtils.isAvailable(msg)) {
			return;
		}
		try {
			Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}
	
	/**
	 * 长时显示
	 * @param context	上下文
	 * @param msg	提示内容
	 */
	public static void showLong(String msg){
		if (!CheckUtils.isAvailable(msg)) {
			return;
		}
		try {
			Toast.makeText(getAppContext(), msg, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
		}
	}

	private static Toast toast;

	public static void makeToast(String text, boolean isLongToast, int offsetX, int offsetY) {
		if (toast != null) {
			toast.cancel();
		}
		if (!CheckUtils.isAvailable(text)) {
			return;
		}
		if (offsetY == 0) {
			offsetY = DisplayUtils.dip2px(-100);
		}
		toast = Toast.makeText(getAppContext(), text, isLongToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, offsetX, offsetY);
		toast.show();
	}
}
