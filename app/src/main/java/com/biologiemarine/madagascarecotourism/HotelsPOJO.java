package com.biologiemarine.madagascarecotourism;

import android.graphics.drawable.Drawable;

public class HotelsPOJO {

    private Drawable HotelPhoto;
    private String HotelScore;
    private String HotelName;
    private String HotelLocation;

    public HotelsPOJO(){}//Default constructor

    public HotelsPOJO(Drawable hotel_photo, String hotel_score, String hotel_name, String hotel_location){

        HotelPhoto = hotel_photo;

        HotelName = hotel_name;
        HotelLocation = hotel_location;
        HotelScore = hotel_score;


    }

    public Drawable getHotelPhoto() {
        return HotelPhoto;
    }

    public void setHotelPhoto(Drawable hotelPhoto) {
        this.HotelPhoto = hotelPhoto;
    }



    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        this.HotelName = hotelName;
    }

    public String getHotelLocation() {
        return HotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.HotelLocation = hotelLocation;
    }

    public String getHotelScore() {
        return HotelScore;
    }

    public void setHotelScore(String hotelScore) {
        this.HotelScore = hotelScore;
    }
}
