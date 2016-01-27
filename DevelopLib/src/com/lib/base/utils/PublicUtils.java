package com.lib.base.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lib.base.bean.PhoneBook;

public class PublicUtils {
	/**
	 * 将listView中的数据全部展示出来，用于嵌套与scrollView中的listView重设高度
	 * @param listview
	 * @param xHeight
	 * @return
	 */
	public static int setListViewHeightBasedOnChildren(ListView listview,
			int xHeight) {
		ListAdapter listAdapter = listview.getAdapter();

		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight() + xHeight;
		}

		ViewGroup.LayoutParams params = listview.getLayoutParams();

		params.height = totalHeight
				+ (listview.getDividerHeight() * (listAdapter.getCount() - 1));
		listview.setLayoutParams(params);
		return totalHeight;
	}
	
	/**
	 * 计算gridView的高度，并且设置
	 * @param gv
	 * @param numColumns
	 * @return
	 */
	public static int setGridViewHeight(GridView gv,int numColumns){
		ListAdapter listAdapter = gv.getAdapter();

		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		int count = 0;
		count = (int) Math.ceil(listAdapter.getCount() / (numColumns * 1.0));

		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i * count, null, gv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gv.getLayoutParams();

		params.height = totalHeight;
//				+ ( * (listAdapter.getCount() - 1));
		gv.setLayoutParams(params);
		return totalHeight;
	}

	protected static Calendar calendar = Calendar.getInstance();
	private static boolean isCancle;
	private static TextView textView;

	public static void showDateDialog(Context context, TextView tv) {
		isCancle = false;
		textView = tv;
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				listener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d("ww", "取消按钮");
						isCancle = true;
					}
				});
		datePickerDialog.show();
		datePickerDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
				}
				return false;
			}
		});
	}

	/**
	 * 日历对话框监听
	 */
	static DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

		@SuppressLint("SimpleDateFormat")
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			if (isCancle) return;
			textView.setText(sDateFormat.format(calendar.getTime()));
		}

	};

	public static ArrayList<PhoneBook> getPhoneBook(Context context) {
		ArrayList<PhoneBook> list = new ArrayList<PhoneBook>();
		// 获得所有的联系人
		Cursor cur = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			// 循环遍历
			 while (cur.moveToNext()){
				int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
				int displayNameColumn = cur
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				// 获得联系人的ID号
				String contactId = cur.getString(idColumn);
				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);
				// 查看该联系人有多少个电话号码。如果没有这返回值为0
				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = context.getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					
					if (phones.moveToFirst()) {
						do {
							// 遍历所有的电话号码
							String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							PhoneBook book = new PhoneBook();
							book.setName(disPlayName);
							book.setNumber(number);
							book.setStatus(new Random().nextInt(4)+1+"");
							list.add(book);
						} while (phones.moveToNext());
						phones.close();
					}

				}
			
			}
		cur.close();
		return list;
	}
	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			v.getLocationInWindow(leftTop);
			int left = leftTop[0], top = leftTop[1], bottom = top
					+ v.getHeight(), right = left + v.getWidth();
			return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
		}
		return false;
	}

	public static Boolean hideInputMethod(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return false;
	}
	
	/**
     * @param path 路径
     * @param displayWidth 需要显示的宽度
     * @param displayHeight 需要显示的高度
     * @return Bitmap
     */
    public static SpannableString decodeBitmap(Context context,int res, int displayWidth, int displayHeight) {
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), res,op); // 获取尺寸信息
            // op.outWidth表示的是图像真实的宽度
            // op.inSamplySize 表示的是缩小的比例
            // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
            // 获取比例大小
            int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
            int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
            // 如果超出指定大小，则缩小相应的比例
            if (wRatio > 1 && hRatio > 1) {
                    if (wRatio > hRatio) {
                            // 如果太宽，我们就缩小宽度到需要的大小，注意，高度就会变得更加的小。
                            op.inSampleSize = wRatio;
                    } else {
                            op.inSampleSize = hRatio;
                    }
            }
            op.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeResource(context.getResources(), res,op);
            // 从原Bitmap创建一个给定宽高的Bitmap
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bmp, displayWidth, displayHeight, true);
          
            ImageSpan imageSpan = new ImageSpan(context, createScaledBitmap);  
     		SpannableString spannableString = new SpannableString("0");
     		spannableString.setSpan(imageSpan, 0, 1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
     		return spannableString;
    } 
    /**
	 * 获取验证码倒计时
	 */
	int time;
	public  void getCodeButton(final Context context, final Button btn,
			int getcoding, final int getcode, final Handler handler) {
		btn.setBackgroundResource(getcoding);
		btn.setClickable(false);
		new Thread() {

			@Override
			public void run() {
				for (time = 60; time >= 0; time--) {
					try {
						sleep(1000);
						handler.post(new Runnable() {
							@Override
							public void run() {
//								btn.setTextSize(14);
								String str = time + "秒后重试";
								btn.setText(str);
							}
						});
						if (time == 0) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									btn.setBackgroundResource(getcode);
									btn.setText("获取验证码");
//									btn.setTextSize(18);
									btn.setClickable(true);
								}
							});
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}
	public interface DeleteStore{
		void delete(AlertDialog alertDialog);
	}
}
