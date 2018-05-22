package com.zcy.ygs.retrofitdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ygs on 2018/5/8.
 */

public class ParcelableTest implements Parcelable {
    private String name;
    protected ParcelableTest(Parcel in) {

    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
