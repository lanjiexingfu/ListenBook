package com.lib.base.utils;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lib.base.app.BaseApp;

public class SystemUtil extends Basic {

	public static void changeLanguage(Context context, int languageIndex) {
		// SharedPreferences languagePre =
		// context.getSharedPreferences("language_choice",
		// Context.MODE_PRIVATE);
		// 应用内配置语言
		Resources resources = context.getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		switch (languageIndex) {
		case 0:
			config.locale = Locale.getDefault(); // 系统默认语言
			break;
		case 1:
			config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文

			break;
		case 2:
			config.locale = Locale.ENGLISH; // 英文
			break;
		case 3:
			// config.locale = Locale.;//阿拉伯语

			config.locale = new Locale("ar");
			break;
		default:
			config.locale = Locale.getDefault();
			break;
		}
		resources.updateConfiguration(config, dm);
	}
	
	/**
	 * 显示输入法
	 * @param activity
	 */
	public static void showSoftInput(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	/**
	 * 隐藏输入法
	 * @param activity
	 */
	public static void hideSoftInput(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
	}
	
	/**
	 * 判斷鍵盤是否已經顯示在界面上
	 * @param activity
	 * @return
	 */
	public static boolean isSoftInputIsActive(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		return inputMethodManager.isActive();
	}

	/**
	 * 设置音乐音量
	 * 
	 * @param context
	 * @param volume
	 */
	public static void setMuicVolume(Context context, int volume) {
		AudioManager mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
	}

	/**
	 * 获取音乐音量
	 * 
	 * @param context
	 * @return
	 */
	public static int getMusicVolume(Context context) {
		AudioManager mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}
	
	/**
	 * 获取音量的最大值
	 * @param context
	 * @return
	 */
	public static int getMaxMusicVolume(Context context){
		AudioManager mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	/**
	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	public static int getScreenMode(Context context) {
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception localException) {

		}
		return screenMode;
	}

	/**
	 * 获得当前屏幕亮度值 0--255
	 */
	public static int getScreenBrightness(Context context) {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception localException) {

		}
		return screenBrightness;
	}

	/**
	 * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	public static void setScreenMode(Context context,int paramInt) {
		try {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 设置当前屏幕亮度值 0--255
	 */
	public static void saveScreenBrightness(Context context,int paramInt) {
		try {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 保存当前的屏幕亮度值，并使之生效
	 */
	public static void setScreenBrightness(Activity activity,int paramInt) {
		Window localWindow = activity.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow
				.getAttributes();
		float f = paramInt / 255.0F;
		localLayoutParams.screenBrightness = f;
		localWindow.setAttributes(localLayoutParams);
	}

	public static void checkRunInUiThread() {
		Thread currThread = Thread.currentThread();
		if (currThread != BaseApp.getUIThread()) {
			throw new RuntimeException(
					"Make sure the content of your adapter is modified from UI thread");
		}
	}

	/**
	 * 控制软键盘的显示隐藏
	 */
	public static void showSoftInput(EditText editText) {
		if (editText == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// manager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
		if (imm != null && imm.isActive()) {
			imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
		}
	}

	/**
	 * 控制软键盘的显示隐藏
	 */
	public static void showSoftInput() {
		if (getActivity() == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
		if (imm != null && imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInput() {
		if (getActivity() == null) {
			return;
		}
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager != null && inputManager.isActive()) {
			//            View focusView = BaseApp.getCurrentFocus();
			//            if (focusView != null) {
			//                inputManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
			//            }
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInput(View editText) {
		if (editText == null) {
			return;
		}
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager != null && inputManager.isActive(editText)) {
			inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

}
