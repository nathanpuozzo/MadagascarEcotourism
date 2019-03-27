package com.biologiemarine.madagascarecotourism.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelsPOJO implements Parcelable {

    private String adresse,booking,description,email,hotel,image,ipe,prix,region,tel,trip_advisor,ville,communaute,dechets,energie_verte,salaire_equitable,web; //these names must match with the names in Firebase database



    public  HotelsPOJO(){ }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIpe() {
        return ipe;
    }

    public void setIpe(String ipe) {
        this.ipe = ipe;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTrip_advisor() {
        return trip_advisor;
    }

    public void setTrip_advisor(String trip_advisor) {
        this.trip_advisor = trip_advisor;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCommunaute() {
        return communaute;
    }

    public void setCommunaute(String communaute) {
        this.communaute = communaute;
    }

    public String getDechets() {
        return dechets;
    }

    public void setDechets(String dechets) {
        this.dechets = dechets;
    }

    public String getEnergie_verte() {
        return energie_verte;
    }

    public void setEnergie_verte(String energie_verte) {
        this.energie_verte = energie_verte;
    }

    public String getSalaire_equitable() {
        return salaire_equitable;
    }

    public void setSalaire_equitable(String salaire_equitable) {
        this.salaire_equitable = salaire_equitable;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }


    protected HotelsPOJO(Parcel in) {
        adresse = in.readString();
        booking = in.readString();
        description = in.readString();
        email = in.readString();
        hotel = in.readString();
        image = in.readString();
        ipe = in.readString();
        prix = in.readString();
        region = in.readString();
        tel = in.readString();
        trip_advisor = in.readString();
        ville = in.readString();
        communaute = in.readString();
        dechets = in.readString();
        energie_verte = in.readString();
        salaire_equitable = in.readString();
        web = in.readString();
    }

    public static final Creator <HotelsPOJO> CREATOR = new Creator <HotelsPOJO>() {
        @Override
        public HotelsPOJO createFromParcel(Parcel in) {
            return new HotelsPOJO( in );
        }

        @Override
        public HotelsPOJO[] newArray(int size) {
            return new HotelsPOJO[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( adresse );
        dest.writeString( booking );
        dest.writeString( description );
        dest.writeString( email );
        dest.writeString( hotel );
        dest.writeString( image );
        dest.writeString( ipe );
        dest.writeString( prix );
        dest.writeString( region );
        dest.writeString( tel );
        dest.writeString( trip_advisor );
        dest.writeString( ville );
        dest.writeString( communaute );
        dest.writeString( dechets );
        dest.writeString( energie_verte );
        dest.writeString( salaire_equitable );
        dest.writeString( web );
    }
}
