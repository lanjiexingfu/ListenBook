package com.listen.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 2016/1/28.
 */
public class BookBean implements Parcelable {
    private String bookId;//图书id
    private String bookName;//图书名称
    private String bookIntro;//图书简介
    private String bookImageUrl;//图书封面

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookId);
        dest.writeString(this.bookName);
        dest.writeString(this.bookIntro);
        dest.writeString(this.bookImageUrl);
    }

    public BookBean() {
    }

    protected BookBean(Parcel in) {
        this.bookId = in.readString();
        this.bookName = in.readString();
        this.bookIntro = in.readString();
        this.bookImageUrl = in.readString();
    }

    public static final Parcelable.Creator<BookBean> CREATOR = new Parcelable.Creator<BookBean>() {
        public BookBean createFromParcel(Parcel source) {
            return new BookBean(source);
        }

        public BookBean[] newArray(int size) {
            return new BookBean[size];
        }
    };
}
