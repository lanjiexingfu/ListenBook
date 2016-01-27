package com.lib.base.utils.camerautil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;

import com.lib.base.app.Configure;
import com.lib.base.app.DLog;
import com.lib.base.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class CameraUtil {
	private static final String TAG = "x.y.afinal.utils.CameraUtil";
	public final int CAMERA = 10001;
	public final int PHONTO = 10002;
	public final int CUT_PHOTO = 10003;
	public final int CUT_PIC = 10004;
	private Activity activity;
	private Fragment fragment;
	private boolean isCut = false;
	private boolean isFreeCut = false;

	public CameraUtil(Activity actvity) {
		this.activity = actvity;
	}
	
	public CameraUtil(Fragment frgment){
		this.fragment = frgment;
	}
	
	
	public boolean isCut() {
		return isCut;
	}
	public void setCut(boolean isCut,boolean isFreeCut) {
		this.isCut = isCut;
		this.isFreeCut = isFreeCut;
	}
	
	/**
	 * 打开摄像头
	 */
	public void openCamera(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//		if(isCut){
//			intent.putExtra("crop", "true"); // 说明要裁剪
//			if(!isFreeCut){
//				intent.putExtra("aspectX", 1); // 宽度
//				intent.putExtra("aspectY", 1); // 高度			
//			}
//		}
		startActivityForResult(intent, CAMERA);
		
	}
	
	private void startActivityForResult(Intent intent ,int requestCode){
		if(activity !=null){
			activity.startActivityForResult(intent, requestCode);	
		}else{
			fragment.startActivityForResult(intent, requestCode);
		}
	}
	
	/**
	 * 打开相册
	 */
	public void openAlbum(){
		if (isCut) {
			getCutPic();
//			crop(actvity,getUri());
		} else {
			getPic();
		}
	}

	String imagePath;

	private Uri getUri() {

//		File file = new File(Environment.getExternalStorageDirectory(), GlobalConfigure.IMAGE_PATH);
		
		//兼容性控制
		File file;
		if (Build.VERSION.SDK_INT > 8) {
			file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} else {
			file = new File(Environment.getExternalStorageDirectory(), Configure.IMAGE_PATH);
		}

		if (!file.exists()) { // 创建目录
			file.mkdirs();
		}

		
		String name = "temp_" + System.currentTimeMillis() + ".png";
		File file1 = new File(file, name);
//		file1.deleteOnExit();
		imagePath = file1.getAbsolutePath();
		Uri uri = Uri.fromFile(file1);
		return uri;
	}

	private void getCutPic() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.putExtra("crop", "true"); // 说明要裁剪
		if(!isFreeCut){
			intent.putExtra("aspectX", 1); // 宽度
			intent.putExtra("aspectY", 1); // 高度			
		}


//		intent.putExtra("outputX", 500);
//		intent.putExtra("outputY", 500);

		intent.setType("image/*"); // 说明你想获得图片
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//		actvity.startActivityForResult(intent, CUT_PHOTO);
		startActivityForResult(intent, CUT_PHOTO);
	}

	private void getPic() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.putExtra("return-data", true);
		intent.setType("image/*"); // 说明你想获得图片
//		actvity.startActivityForResult(intent, PHONTO);
		startActivityForResult(intent, PHONTO);
	}

	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//		intent.putExtra("crop", "true");
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
		intent.putExtra("crop", "true"); // 说明要裁剪
		if(!isFreeCut){
			intent.putExtra("aspectX", 1); // 宽度
			intent.putExtra("aspectY", 1); // 高度			
		}
		intent.putExtra("return-data", false);
//		actvity.startActivityForResult(intent, CUT_PIC);
		startActivityForResult(intent, CUT_PIC);

	}
	
	/*
	 * 剪切图片
	 */
	public void crop(Activity activity, Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);

		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		if (intent.resolveActivity(activity.getPackageManager()) != null) {
			activity.startActivityForResult(intent, CUT_PHOTO);
		} else {
			ToastUtil.showShort("无法剪切图片");
		}
	}


	/**
	 * 做剪切处理
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public String getImgPath(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return null;
		}
		if (requestCode == CAMERA && isCut) {
			File file = new File(imagePath);
			startPhotoZoom(Uri.fromFile(file));
		} else if(requestCode == CAMERA){
			return decodeFile(imagePath);
//			return imagePath;
		}
		
		if (requestCode == CUT_PHOTO) {
			return decodeFile(imagePath);
//			return imagePath;
			
		}
		if (requestCode == PHONTO && data != null) {
			Uri uri = data.getData();
			if (uri == null)
				return null;
			String[] proj = { MediaColumns.DATA };
			Cursor cursor = null;
			if(activity!=null){
//				@SuppressWarnings("deprecation")
				cursor = activity.managedQuery(uri, proj, null, null, null);	
			}else{
				cursor = fragment.getActivity().managedQuery(uri, proj, null, null, null);	
			}
			
			
			
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			String imagePath = cursor.getString(column_index);
			return decodeFile(imagePath);
//			return imagePath;
		}
		if (requestCode == CUT_PIC) {
			return decodeFile(imagePath);
//			return imagePath;
		}
		return null;
	}
	
	/**
	 * 做剪切处理
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public void getImgPath(int requestCode, int resultCode, Intent data,ImageDealTheard theard) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == CAMERA && isCut) {
			File file = new File(imagePath);
			startPhotoZoom(Uri.fromFile(file));
		} else if(requestCode == CAMERA){
//			return decodeFile(imagePath);
//			return imagePath;
			theard.setImagePath(imagePath);
			theard.start();
		}
		
		if (requestCode == CUT_PHOTO) {
//			return decodeFile(imagePath);
//			return imagePath;
			theard.setImagePath(imagePath);
			theard.start();
			
		}
		if (requestCode == PHONTO && data != null) {
			Uri uri = data.getData();
			if (uri == null)
				return;
			String[] proj = { MediaColumns.DATA };
			Cursor cursor = null;
			if(activity!=null){
//				@SuppressWarnings("deprecation")
				cursor = activity.managedQuery(uri, proj, null, null, null);	
			}else{
				cursor = fragment.getActivity().managedQuery(uri, proj, null, null, null);	
			}
//			Cursor cursor = actvity.managedQuery(uri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			String imagePath = cursor.getString(column_index);
//			return decodeFile(imagePath);
			theard.setImagePath(imagePath);
			theard.start();
//			return imagePath;
		}
		if (requestCode == CUT_PIC) {
//			return decodeFile(imagePath);
			theard.setImagePath(imagePath);
			theard.start();
//			return imagePath;
		}
//		return null;
	}

	private static final int imageUpperLimitPix = 1000;//图片的上限

	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	public static String decodeFile(String imagePath) {
		File file = new File(imagePath);
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(file), null, o);

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
//			while (true) {
//				if (width_tmp / 2 < imageUpperLimitPix
//						&& height_tmp / 2 < imageUpperLimitPix)
//					break;
//				width_tmp /= 2;
//				height_tmp /= 2;
//				scale *= 2;
//			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap newBitmap = BitmapFactory.decodeStream(new FileInputStream(
					file), null, o2);
			return storeImageToFile(newBitmap);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	/**
	 * 压缩图片
	 * @param imagePath 图片地址
	 * @param persentage 压缩系数 example：30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0  
	 */
	public static String compress(String imagePath,int persentage){
		File file = new File(imagePath);
		try {
			// decode image size
			DLog.e(TAG, "file path:" + file.getAbsolutePath());
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, o);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bitmap.compress(Bitmap.CompressFormat.PNG, persentage, baos);
			return storeImageToFile(bitmap);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private static String storeImageToFile(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		File file1 = new File(Configure.IMAGE_PATH);

		if (!file1.exists()) { // 创建目录
			file1.mkdirs();
		}

		String name = System.currentTimeMillis() + ".png";
		File file = new File(file1, name);

		RandomAccessFile accessFile = null;

		ByteArrayOutputStream steam = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 40, steam);
		byte[] buffer = steam.toByteArray();

		try {
			accessFile = new RandomAccessFile(file, "rw");
			accessFile.write(buffer);
		} catch (Exception e) {
			return null;
		}

		try {
			steam.close();
			// XXX annother trye
			accessFile.close();
		} catch (IOException e) {
			// Note: do nothing.
		}

		return file.getAbsolutePath();
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
