package com.lib.ext.widget.calendar.manager;

import android.support.annotation.NonNull;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Blaz Solar on 24/02/14.
 */
public class Day {

    private static final DateTimeFormatter mFormatter = DateTimeFormat.forPattern("d");

    @NonNull private final LocalDate mDate;
    private boolean mToday;
    private boolean mChecked;
    private boolean mEnabled;
    private boolean mCurrent;

    public Day(@NonNull LocalDate date, boolean today) {
        mDate = date;
        mToday = today;
        mEnabled = true;
        mCurrent = today;
    }

    @NonNull
    public LocalDate getDate() {
        return mDate;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public boolean isCurrent() {
        return mCurrent;
    }

    public void setCurrent(boolean current) {
        mCurrent = current;
    }

    public boolean isToday() {
        return mToday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (mEnabled != day.mEnabled) return false;
        if (mChecked != day.mChecked) return false;
        if (mToday != day.mToday) return false;
        if (!mDate.isEqual(day.mDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mDate.hashCode();
        result = 31 * result + (mToday ? 1 : 0);
        result = 31 * result + (mChecked ? 1 : 0);
        result = 31 * result + (mEnabled ? 1 : 0);
        return result;
    }

    @NonNull
    public String getText() {
        return mDate.toString(mFormatter);
    }
}
