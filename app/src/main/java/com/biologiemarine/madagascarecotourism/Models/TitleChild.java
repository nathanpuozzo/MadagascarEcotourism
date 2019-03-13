package com.biologiemarine.madagascarecotourism.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TitleChild implements Parcelable {

    private String title;

    public TitleChild (String title){

        this.title = title;
    }

    protected TitleChild(Parcel in) {
        title = in.readString();
    }

    public static final Creator<TitleChild> CREATOR = new Creator<TitleChild>() {
        @Override
        public TitleChild createFromParcel(Parcel in) {
            return new TitleChild(in);
        }

        @Override
        public TitleChild[] newArray(int size) {
            return new TitleChild[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
