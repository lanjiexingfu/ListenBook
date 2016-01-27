package com.lib.ext.widget.faceselector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lib.R;

@SuppressLint("NewApi")
public class FaceSelector extends LinearLayout{
	
	private View contentView;
	private LayoutInflater mInflater;
	private static Context mContext;
	
	private FaceLayout mFaceLayout;
	
	private LinearLayout ll_face;
	
	public FaceSelector(Context context) {
		super(context);
		mContext = context;
		init();
	}
	public FaceSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	public FaceSelector(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}
	
	private void init(){
		mInflater = LayoutInflater.from(mContext);
		contentView = mInflater.inflate(R.layout.widget_face_selector,this,true);
		
		mFaceLayout = new FaceLayout(mContext);
		
		ll_face = (LinearLayout) findViewById(R.id.ll_face);
		ll_face.addView(mFaceLayout);
	}
	
	public void setOnClickFaceListener(OnClickFaceListener l){
		mFaceLayout.setOnClickFaceListener(l);
	}
    
    /**
     * 使用正则表达式来解析字符串
     * @param inputStr
     * @return
     */
    public static SpannableString parseString(String inputStr){
        SpannableStringBuilder spb = new SpannableStringBuilder();
        Pattern mPattern =  Pattern.compile("\\\\..");
        Matcher mMatcher = mPattern.matcher(inputStr);
        String tempStr = inputStr;
        
        while(mMatcher.find()){
            int start = mMatcher.start();
            int end = mMatcher.end();
            spb.append(tempStr.substring(0,start));
            String faceName = mMatcher.group();
            setFace(spb, faceName);
            tempStr = tempStr.substring(end, tempStr.length());
            /**
             * 更新查找的字符串
             */
            mMatcher.reset(tempStr);
        }
        spb.append(tempStr);
        return new SpannableString(spb);
    }
    
    private static void setFace(SpannableStringBuilder spb, String faceName){
        Integer faceId = 0;
	    for(int i = 0 ;i<FaceData.size();i++){
        	if(faceName.equals(FaceData.get(i).getFaceName())){
        		faceId = FaceData.get(i).getFaceRes();
        		break;
        	}
        }
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), faceId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        ImageSpan imageSpan = new ImageSpan(mContext,bitmap);
        SpannableString spanStr = new SpannableString(faceName);
        spanStr.setSpan(imageSpan, 0, faceName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spb.append(spanStr);
    }
}
