package com.listen.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.listen.activity.R;

public class CustomRadioButton extends RadioButton {
	private Drawable drawableTop, drawableBottom, drawableLeft, drawableRight;
	private int mTopWith, mTopHeight, mBottomWith, mBottomHeight, mRightWith, mRightHeight, mLeftWith, mLeftHeight;

	public CustomRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	public CustomRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public CustomRadioButton(Context context) {
		super(context);
		initView(context, null);
	}

	private void initView(Context context, AttributeSet attrs) {
		if (attrs != null) {
			float scale = context.getResources().getDisplayMetrics().density;
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton);
			int n = a.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = a.getIndex(i);
				switch (attr) {
				case R.styleable.CustomRadioButton_custom_drawableBottom:
					drawableBottom = a.getDrawable(attr);
					break;
				case R.styleable.CustomRadioButton_custom_drawableTop:
					drawableTop = a.getDrawable(attr);
					break;
				case R.styleable.CustomRadioButton_custom_drawableLeft:
					drawableLeft = a.getDrawable(attr);
					break;
				case R.styleable.CustomRadioButton_custom_drawableRight:
					drawableRight = a.getDrawable(attr);
					break;
				case R.styleable.CustomRadioButton_custom_drawableTopWith:
					mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableTopHeight:
					mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableBottomWith:
					mBottomWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableBottomHeight:
					mBottomHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableRightWith:
					mRightWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableRightHeight:
					mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableLeftWith:
					mLeftWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;
				case R.styleable.CustomRadioButton_custom_drawableLeftHeight:
					mLeftHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
					break;

				default:
					break;
				}
			}
			a.recycle();
			setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
		}
	}

	// 设置Drawable定义的大小
	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {

		if (left != null) {
			left.setBounds(0, 0, mLeftWith <= 0 ? left.getIntrinsicWidth() : mLeftWith, mLeftHeight <= 0 ? left.getMinimumHeight() : mLeftHeight);
		}
		if (right != null) {
			right.setBounds(0, 0, mRightWith <= 0 ? right.getIntrinsicWidth() : mRightWith, mRightHeight <= 0 ? right.getMinimumHeight() : mRightHeight);
		}
		if (top != null) {
			top.setBounds(0, 0, mTopWith <= 0 ? top.getIntrinsicWidth() : mTopWith, mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);
		}
		if (bottom != null) {
			bottom.setBounds(0, 0, mBottomWith <= 0 ? bottom.getIntrinsicWidth() : mBottomWith, mBottomHeight <= 0 ? bottom.getMinimumHeight()
					: mBottomHeight);
		}
		setCompoundDrawables(left, top, right, bottom);
	}
	
}
