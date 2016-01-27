package com.lib.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lib.base.utils.DisplayUtils;

/**
 * 字母快速定位列表控件
 */
public class LetterListView extends View {

    OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public static final String[] DEFAULT_LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private String[] letters = DEFAULT_LETTERS;

    Paint paint = new Paint();

    Rect mBgRect = new Rect();

    Rect mChooseRect = new Rect();

    Paint mBgPaint = new Paint();

    Paint mChoosePaint = new Paint();

    int choose = -1;

    private static boolean showBkg = false;

    private int mWidth;

    private int mHeight;

    private float mTextSize = -1;

    private float textSizeUnit = 1;

    private int paddingLeft;

    private int paddingRight;

    private int scrollSelectedColor;

    private int touchSelectedColor;

    private int textPaddingButtom;

    public LetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LetterListView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        mBgPaint.setColor(Color.BLACK);
        touchSelectedColor = 0x75b9e3;
        scrollSelectedColor = 0x75b9e3;
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * first onMeasure , and then onLayout
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        mWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight;
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        halfWidth = mWidth / 2;
        /**
         * 计算每一行的大小
         */
        int size = letters.length;
        singleHeight = mHeight / size;
        int remainHeight = mHeight - letters.length * singleHeight;
        startTop = remainHeight / 2;
        textSizeUnit = 1;
        if (mTextSize <= 0) {
            /**
             * 计算字体的大小
             */
            paint.setTextSize(textSizeUnit);
            FontMetrics fontMetrics = paint.getFontMetrics();
            float descent = fontMetrics.descent;
            float ascent = fontMetrics.ascent;
            // 这样未考虑 小于0的情况此时textHeightUnit=0 则mTextSize无穷大 绘制不出文字
            // float textHeightUnit = (int) (descent - ascent);
            float textHeightUnit = (descent - ascent);
            if (DisplayUtils.getScreenHeight() >= 480) {
                mTextSize = singleHeight / textHeightUnit / 3 * 2;
            } else {
                mTextSize = singleHeight / textHeightUnit / 8 * 7;
            }
        }
        paint.setTextSize(mTextSize);
        FontMetrics fontMetrics = paint.getFontMetrics();
        float descent = fontMetrics.descent;
        float ascent = fontMetrics.ascent;
        float textHeight = (int) (descent - ascent);
        textPaddingButtom = (int) ((singleHeight - textHeight) / 2 + Math.abs(fontMetrics.descent));
        // setMeasuredDimension(mWidth, mHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int startTop;

    private int singleHeight;

    private int halfWidth;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // int height = getHeight();
        float yPos = startTop;
        if (showBkg) {
            mBgRect.set(paddingLeft, 0, paddingLeft + mWidth, mHeight);
            canvas.drawRect(mBgRect, mBgPaint);
        }
        for (int i = 0; i < letters.length; i++) {

            if (i == choose) {
                if (isChosenByListViewScroll) {
                    mChoosePaint.setColor(scrollSelectedColor);
                } else {
                    mChoosePaint.setColor(touchSelectedColor);
                }
                int top = startTop + choose * singleHeight;
                mChooseRect.set(paddingLeft, top, paddingLeft + mWidth, top + singleHeight);
                canvas.drawRect(mChooseRect, mChoosePaint);
                paint.setColor(Color.WHITE);
                paint.setFakeBoldText(true);
            } else {
                if (showBkg) {
                    paint.setColor(Color.WHITE);
                } else {
                    // 设置触摸时的背景色
                    paint.setColor(0x878787);
                }
                paint.setFakeBoldText(false);
            }

            float xPos = paddingLeft + halfWidth - paint.measureText(letters[i]) / 2;
            yPos += singleHeight;
            canvas.drawText(letters[i], xPos, yPos - textPaddingButtom, paint);
            // paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        isChosenByListViewScroll = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                takeChosenLetterEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                takeChosenLetterEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                updateNoChosen();
                break;
            case MotionEvent.ACTION_CANCEL:
                updateNoChosen();
                break;
        }
        return true;
    }

    private void updateNoChosen() {
        showBkg = false;
        // choose = -1;
        invalidate();
        if (mScrolled) {
            onTouchingLetterChangedListener.onScrolledAndHandUp();
        }
        mScrolled = false;
        mLetterChangeCount = 0;
    }

    private synchronized void takeChosenLetterEvent(MotionEvent event) {
        showBkg = true;
        int chooseIndex = (int) (event.getY() / getHeight() * letters.length);
        if (chooseIndex < 0) {
            return;
        }
        if (chooseIndex >= letters.length) {
            chooseIndex = letters.length - 1;
        }
        final int oldChoose = choose;
        if (oldChoose != chooseIndex) {
            choose = chooseIndex;
            if (onTouchingLetterChangedListener != null) {
                onTouchingLetterChangedListener.onTouchingLetterChanged(letters[chooseIndex]);
            }
            postInvalidate();
            if (mLetterChangeCount++ > 0) {
                mScrolled = true;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }


    private boolean mScrolled = false;

    private int mLetterChangeCount = 0;

    private boolean isChosenByListViewScroll = false;

    public void changeChosenLetter(String letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].equals(letter.toUpperCase().trim())) {
                // showBkg = false;
                isChosenByListViewScroll = true;
                choose = i;
                invalidate();
                break;
            }
        }
    }

    public void resume() {
        showBkg = false;
        isChosenByListViewScroll = false;
        choose = -1;
        invalidate();
    }

    public static void releaseBg() {
        showBkg = false;
    }

    public String[] getLetters() {
        return letters;
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
    }

    /**
     * 字母控件所选择字母改变事件接口
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String letter);

        public void onScrolledAndHandUp();
    }

}
