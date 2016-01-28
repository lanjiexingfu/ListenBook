package com.listen.model.respond;

import android.os.Parcel;
import android.os.Parcelable;

import com.listen.model.bean.BookBean;

import java.util.ArrayList;

/**
 * Created by Chiely on 15/6/18.
 */
public class HotBooksRespond extends BaseRespond implements Parcelable {
   private ArrayList<BookBean> hotBooks;

    public ArrayList<BookBean> getHotBooks() {
        return hotBooks;
    }

    public void setHotBooks(ArrayList<BookBean> hotBooks) {
        this.hotBooks = hotBooks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(hotBooks);
    }

    public HotBooksRespond() {
    }

    protected HotBooksRespond(Parcel in) {
        this.hotBooks = in.createTypedArrayList(BookBean.CREATOR);
    }

    public static final Parcelable.Creator<HotBooksRespond> CREATOR = new Parcelable.Creator<HotBooksRespond>() {
        public HotBooksRespond createFromParcel(Parcel source) {
            return new HotBooksRespond(source);
        }

        public HotBooksRespond[] newArray(int size) {
            return new HotBooksRespond[size];
        }
    };
}
