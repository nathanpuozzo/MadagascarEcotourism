package com.biologiemarine.madagascarecotourism;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class ContactPOJO {

    private Drawable mPhoto;
    private String mName;
    private String mLocation;
    private String mNote;

    public ContactPOJO(){} //default constructor

    //Argument Constructor
    public ContactPOJO(Drawable photo, String name, String location, String note){
        mPhoto = photo;
        mName = name;
        mLocation = location;
        mNote = note;

    }


    public String getmName() {

        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {

        this.mLocation = mLocation;
    }
    public String getmNote() {

        return mNote;
    }

    public void setmNote(String mNote) {

        this.mNote = mNote;
    }

    public Drawable getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Drawable mPhoto) {

        this.mPhoto = mPhoto;
    }
}
