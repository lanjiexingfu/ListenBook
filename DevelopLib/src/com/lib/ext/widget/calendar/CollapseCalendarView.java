package com.lib.ext.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.R;
import com.lib.ext.widget.calendar.manager.CalendarManager;
import com.lib.ext.widget.calendar.manager.Day;
import com.lib.ext.widget.calendar.manager.Formatter;
import com.lib.ext.widget.calendar.manager.Month;
import com.lib.ext.widget.calendar.manager.Week;
import com.lib.ext.widget.calendar.widget.DayView;
import com.lib.ext.widget.calendar.widget.WeekView;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Blaz Solar on 28/02/14.
 */
public class CollapseCalendarView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CalendarView";

    private static final int COMMON_WEEK = 0;
    private static final int FIRST_WEEK = 1;
    private static final int END_WEEK = 2;

    @Nullable
    private CalendarManager mManager;

    @NonNull
    private TextView mTitleView;
    @NonNull
    private ImageButton mPrev;
    @NonNull
    private ImageButton mNext;
    @NonNull
    private LinearLayout mWeeksView;

    @NonNull
    private final LayoutInflater mInflater;
    @NonNull
    private final RecycleBin mRecycleBin = new RecycleBin();

    @Nullable
    private OnDateSelect mListener;

    @NonNull
    private TextView mSelectionText;
    @NonNull
    private LinearLayout mHeader;

//    @NonNull
//    private ResizeManager mResizeManager;

    private boolean initialized;

    public CollapseCalendarView(Context context) {
        this(context, null);
    }

    public CollapseCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.calendarViewStyle);
    }

    public CollapseCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mInflater = LayoutInflater.from(context);

//        mResizeManager = new ResizeManager(this);

        inflate(context, R.layout.calendar_layout, this);

        setOrientation(VERTICAL);
    }

    public void init(@NonNull CalendarManager manager) {
        mManager = manager;
        populateLayout();
    }

    @Nullable
    public CalendarManager getManager() {
        return mManager;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "On click");
        if (mManager != null) {
            int id = v.getId();
            if (id == R.id.prev) {
                if (mManager.prev()) {
                    populateLayout();
                }
            } else if (id == R.id.next) {
                Log.d(TAG, "next");
                if (mManager.next()) {
                    Log.d(TAG, "populate");
                    populateLayout();
                }
            }

        }
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
//        mResizeManager.onDraw();
        super.dispatchDraw(canvas);
    }

    @Nullable
    public CalendarManager.State getState() {
        if (mManager != null) {
            return mManager.getState();
        } else {
            return null;
        }
    }

    public void setListener(@Nullable OnDateSelect listener) {
        mListener = listener;
    }

    /**
     * @deprecated This will be removed
     */
    public void setTitle(@Nullable String text) {
        if (StringUtils.isEmpty(text)) {
            mHeader.setVisibility(View.VISIBLE);
            mSelectionText.setVisibility(View.GONE);
        } else {
            mHeader.setVisibility(View.GONE);
            mSelectionText.setVisibility(View.VISIBLE);
            mSelectionText.setText(text);
        }
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return mResizeManager.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        super.onTouchEvent(event);
//
//        return mResizeManager.onTouchEvent(event);
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitleView = (TextView) findViewById(R.id.calendar_title);
        mTitleView.setTextSize(17);
        mPrev = (ImageButton) findViewById(R.id.prev);
        mNext = (ImageButton) findViewById(R.id.next);
        mWeeksView = (LinearLayout) findViewById(R.id.weeks);

        mHeader = (LinearLayout) findViewById(R.id.header);
        mSelectionText = (TextView) findViewById(R.id.selection_title);

        mPrev.setOnClickListener(this);
        mNext.setOnClickListener(this);

        populateLayout();
    }

    /**
     * 列标题（周日周一...周六）
     */
    private void populateDays() {

        if (!initialized) {
            if (mManager != null) {
                Formatter formatter = mManager.getFormatter();
                LinearLayout layout = (LinearLayout) findViewById(R.id.days);

                LocalDate date = LocalDate.now().withDayOfWeek(DateTimeConstants.SUNDAY);
                setDayName(formatter, layout, date, 0);

                date = LocalDate.now().withDayOfWeek(DateTimeConstants.MONDAY);
                for (int i = 1; i < 7; i++) {
                    setDayName(formatter, layout, date, i);
                    date = date.plusDays(1);
                }
                initialized = true;
            }
        }

    }

    private void setDayName(Formatter formatter, LinearLayout layout, LocalDate date, int i) {
        TextView textView = (TextView) layout.getChildAt(i);
        textView.setText(formatter.getDayName(date));
    }

    public void populateLayout() {
        if (mManager != null) {

            populateDays();

            setTitle(0, mManager.getActiveMonth());

            mPrev.setEnabled(mManager.hasPrev());
            mNext.setEnabled(mManager.hasNext());

            if (mManager.getState() == CalendarManager.State.MONTH) {
                populateMonthLayout((Month) mManager.getUnits());
            } else {
                populateWeekLayout((Week) mManager.getUnits());
            }
        }

    }

    /**
     * 本月
     *
     * @param selectedDay
     * @return
     */
    private boolean isCurrentMonth(LocalDate selectedDay) {
        return LocalDate.now().withDayOfMonth(1).isEqual(selectedDay.withDayOfMonth(1));
    }

    private void populateMonthLayout(Month month) {

        List<Week> weeks = month.getWeeks();

        int cnt = weeks.size();
        for (int i = 0; i < cnt; i++) {
            WeekView weekView = getWeekView(i);
            if (i != (cnt - 1)) {
                weekView.setIsDrawLine(true);
            } else {
                weekView.setIsDrawLine(false);
            }
            populateWeekLayout(weeks.get(i), weekView);
        }
        int childCnt = mWeeksView.getChildCount();
        if (cnt < childCnt) {
            for (int i = cnt; i < childCnt; i++) {
                cacheView(i);
            }
        }

    }

    private void populateWeekLayout(Week week) {
        WeekView weekView = getWeekView(0);
        populateWeekLayout(week, weekView);
        int cnt = mWeeksView.getChildCount();
        if (cnt > 1) {
            for (int i = cnt - 1; i > 0; i--) {
                cacheView(i);
            }
        }
    }

    private void populateWeekLayout(@NonNull Week week, @NonNull WeekView weekView) {
        List<Day> days = week.getDays();
        for (int i = 0; i < 7; i++) {
            setDayValue(weekView, days.get(i), i);
        }
    }

    private void setDayValue(@NonNull WeekView weekView, final Day day, int i) {

        DayView dayView = (DayView) weekView.getChildAt(i);

        dayView.setText(day.getText());
        dayView.setChecked(day.isChecked());
        dayView.setCurrent(day.isCurrent());

        boolean enables = dayIsCurrentMonth(day.getDate()) && day.isEnabled();
        dayView.setEnabled(enables);

        if (enables) {
            dayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalDate date = day.getDate();
                    if (mManager.selectDay(date)) {
                        populateLayout();
                        if (mListener != null) {
                            mListener.onDateSelected(date);
                        }
                    }
                }
            });
        } else {
            dayView.setOnClickListener(null);
        }
    }

    /**
     * day是否在当前选择月份内
     *
     * @param date
     * @return
     */
    private boolean dayIsCurrentMonth(LocalDate date) {
        return mManager.getActiveMonth().equals(date.withDayOfMonth(1));
    }

    @NonNull
    public LinearLayout getWeeksView() {
        return mWeeksView;
    }

    @NonNull
    private WeekView getWeekView(int index) {
        int cnt = mWeeksView.getChildCount();
        if (cnt < index + 1) {
            for (int i = cnt; i < index + 1; i++) {
                View view = getView();
                mWeeksView.addView(view);
            }
        }
        return (WeekView) mWeeksView.getChildAt(index);
    }

    private View getView() {
        View view = mRecycleBin.recycleView();
        if (view == null) {
            view = mInflater.inflate(R.layout.week_layout, this, false);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void cacheView(int index) {
        View view = mWeeksView.getChildAt(index);
        if (view != null) {
            mWeeksView.removeViewAt(index);
            mRecycleBin.addView(view);
        }
    }

    public LocalDate getSelectedDate() {
        return mManager.getSelectedDay();
    }

    public void setTitle(int flag, LocalDate date) {

        if (flag == 1) {
            mTitleView.setText(date.toString("yyyy-MM-dd") + (isToday(date) ? " 今天" : " "+formatWeek(date.getDayOfWeek())));
        } else {
            mTitleView.setText(date.toString("yyyy-MM") + (isCurrentMonth(date) ? "(本月)" : ""));
        }
    }

    public String formatWeek(int i){
        String week = "";
        switch (i){
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
            case 7:
                week = "周日";
                break;
        }
        return week;
    }

    public void setSelectedDate(LocalDate date) {
        if (date != null) {
            setTitle(1, date);
            mManager.selectDay(date);
        }
    }

    public void update() {
        mManager.update();
        populateLayout();
    }

    private boolean isToday(@NonNull LocalDate selectedDate) {
        return LocalDate.now().isEqual(selectedDate);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

//        mResizeManager.recycle();
    }

    private class RecycleBin {

        private final Queue<View> mViews = new LinkedList<>();

        @Nullable
        public View recycleView() {
            return mViews.poll();
        }

        public void addView(@NonNull View view) {
            mViews.add(view);
        }

    }

    public interface OnDateSelect {
        public void onDateSelected(LocalDate date);
    }
}
