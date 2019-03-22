package com.biologiemarine.madagascarecotourism.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactPOJO implements Parcelable {

    private String Nom,Image,Langues,Zones,Agregation,Association,Specialites,Description,Grade,Mail,Tel; //these names must match with the names in Firebase database

    public  ContactPOJO(){ }

    protected ContactPOJO(Parcel in) {
        Nom = in.readString();
        Image = in.readString();
        Langues = in.readString();
        Zones = in.readString();
        Agregation = in.readString();
        Association = in.readString();
        Specialites = in.readString();
        Description = in.readString();
        Grade = in.readString();
        Mail = in.readString();
        Tel = in.readString();
    }

    public static final Creator <ContactPOJO> CREATOR = new Creator <ContactPOJO>() {
        @Override
        public ContactPOJO createFromParcel(Parcel in) {
            return new ContactPOJO( in );
        }

        @Override
        public ContactPOJO[] newArray(int size) {
            return new ContactPOJO[size];
        }
    };

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLangues() {
        return Langues;
    }

    public void setLangues(String langues) {
        Langues = langues;
    }

    public String getZones() {
        return Zones;
    }

    public void setZones(String zones) {
        Zones = zones;
    }


    @Override
    public String toString() {
        return "Contact{"+
                "Nom = '" + Nom + '\'' +
                ", Langues='" + Langues + '\'' +
                ", Zones='" + Zones + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( Nom );
        dest.writeString( Image );
        dest.writeString( Langues );
        dest.writeString( Zones );
        dest.writeString( Agregation );
        dest.writeString( Association );
        dest.writeString( Specialites );
        dest.writeString( Description );
        dest.writeString( Grade );
        dest.writeString( Mail );
        dest.writeString( Tel );
    }

    public String getAgregation() {
        return Agregation;
    }

    public void setAgregation(String agregation) {
        Agregation = agregation;
    }

    public String getAssociation() {
        return Association;
    }

    public void setAssociation(String association) {
        Association = association;
    }

    public String getSpecialites() {
        return Specialites;
    }

    public void setSpecialites(String specialites) {
        Specialites = specialites;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }
}
