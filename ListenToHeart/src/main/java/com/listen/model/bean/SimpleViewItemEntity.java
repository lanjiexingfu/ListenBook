package com.listen.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chiely on 15/6/10.
 */
public class SimpleViewItemEntity implements Parcelable {
    public int index;
    public int iconId1;
    public int iconId2;
    public String text1;
    public String text2;

    private SimpleViewItemEntity(int index, int iconId1, String text1) {
        this.index = index;
        this.iconId1 = iconId1;
        this.text1 = text1;
    }

    private SimpleViewItemEntity(int index, int iconId1, String text1, String text2) {
        this.index = index;
        this.iconId1 = iconId1;
        this.text1 = text1;
        this.text2 = text2;
    }

    private SimpleViewItemEntity(int index, int iconId1, String text1, int iconId2) {
        this.index = index;
        this.iconId1 = iconId1;
        this.text1 = text1;
        this.iconId2 = iconId2;
    }


    private static int saveIndex = 1;

    public static void reset() {
        saveIndex = 1;
    }

    public static SimpleViewItemEntity newInstance(int iconId1, String text1) {
        return new SimpleViewItemEntity(saveIndex++, iconId1, text1);
    }

    public static SimpleViewItemEntity newInstance(int iconId1, String text1, String text2) {
        return new SimpleViewItemEntity(saveIndex++, iconId1, text1, text2);
    }

    public static SimpleViewItemEntity newInstance(int iconId1, String text1,int iconId2) {
        return new SimpleViewItemEntity(saveIndex++, iconId1, text1, iconId2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeInt(this.iconId1);
        dest.writeInt(this.iconId2);
        dest.writeString(this.text1);
        dest.writeString(this.text2);
    }

    protected SimpleViewItemEntity(Parcel in) {
        this.index = in.readInt();
        this.iconId1 = in.readInt();
        this.iconId2 = in.readInt();
        this.text1 = in.readString();
        this.text2 = in.readString();
    }

    public static final Creator<SimpleViewItemEntity> CREATOR = new Creator<SimpleViewItemEntity>() {
        public SimpleViewItemEntity createFromParcel(Parcel source) {
            return new SimpleViewItemEntity(source);
        }

        public SimpleViewItemEntity[] newArray(int size) {
            return new SimpleViewItemEntity[size];
        }
    };
}
