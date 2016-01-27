package com.lib.ext.widget.zxing.view;

import java.io.IOException;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lib.R;
import com.lib.ext.widget.zxing.camera.CameraManager;
import com.lib.ext.widget.zxing.decoding.CaptureLayoutHandler;
import com.lib.ext.widget.zxing.decoding.InactivityTimer;
import com.lib.ext.widget.zxing.listener.OnQueryCodeListener;

@SuppressLint("NewApi")
public class CaptureLayout extends LinearLayout implements Callback{

	private Context mContext;
	private LayoutInflater mInflater;
	private View contentView;
	
	private CaptureLayoutHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private OnQueryCodeListener mOnQueryCodeListener;
	
	public CaptureLayout(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	public CaptureLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}
	public CaptureLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}
	
	/**
	 * 初始化View
	 */
	private void initView(){
		mInflater = LayoutInflater.from(mContext);
		contentView = mInflater.inflate(R.layout.camera, this, true);
		
		CameraManager.init(mContext);
//		CameraManager.getInstance().getInstance
		
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(mContext);
		
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
//		initSurfaceViewWH();
		
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}
	
//	/**
//	 * 初始化
//	 */
//	public void initSurfaceViewWH(){
//
//		float temp = 0.75f;
//		int width = (int) (GlobalConfigure.screenHeight * temp);
//		
//		LayoutParams lp2 = new LayoutParams(width, GlobalConfigure.screenHeight);
//		contentView.setLayoutParams(lp2);
//		
//		android.widget.FrameLayout.LayoutParams lp = 
//				new android.widget.FrameLayout.LayoutParams(width,android.widget.FrameLayout.LayoutParams.MATCH_PARENT);
//		surfaceView.setLayoutParams(lp);
		
//	}
	
	/**
	 * 设置查询代码的回调监听
	 * @param l
	 */
	public void setOnQueryCodeListener(OnQueryCodeListener l){
		mOnQueryCodeListener = l;
	}
	
	/**
	 * Handler scan result
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if(mOnQueryCodeListener != null){
			mOnQueryCodeListener.onResult(resultString);
			
		}
	}
	
	/**
	 * 在onDestroy中调用
	 */
	public void shutDown(){
		inactivityTimer.shutdown();
	}
	
	/**
	 * 在onPauser中调用
	 */
	public void closeDriver(){
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}
	
	
	/**
	 * 扫描失败
	 */
	public void handleDecodeFail(){
		if(mOnQueryCodeListener != null){
			mOnQueryCodeListener.onFail();
			
		}
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureLayoutHandler(CaptureLayout.this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 初始化声音
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
//			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}
