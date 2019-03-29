package com.biologiemarine.madagascarecotourism.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AirePOJO implements Parcelable {

    private String Image, SHORT_NAME,STATUT_JUR,PROVINCE,Description,Catégorie,Lien1,Lien2,Lien3,Recherche1,Recherche2,Recherche3,REGION,DISTRICT,Type;

    public AirePOJO(){}

    protected AirePOJO(Parcel in) {
        Image = in.readString();
        SHORT_NAME = in.readString();
        STATUT_JUR = in.readString();
        PROVINCE = in.readString();
        Description = in.readString();
        Catégorie = in.readString();
        Lien1 = in.readString();
        Lien2 = in.readString();
        Lien3 = in.readString();
        Recherche1 = in.readString();
        Recherche2 = in.readString();
        Recherche3 = in.readString();
        REGION = in.readString();
        DISTRICT = in.readString();
        Type = in.readString();
    }

    public static final Creator <AirePOJO> CREATOR = new Creator <AirePOJO>() {
        @Override
        public AirePOJO createFromParcel(Parcel in) {
            return new AirePOJO( in );
        }

        @Override
        public AirePOJO[] newArray(int size) {
            return new AirePOJO[size];
        }
    };

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }

    public String getSTATUT_JUR() {
        return STATUT_JUR;
    }

    public void setSTATUT_JUR(String STATUT_JUR) {
        this.STATUT_JUR = STATUT_JUR;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCatégorie() {
        return Catégorie;
    }

    public void setCatégorie(String catégorie) {
        Catégorie = catégorie;
    }

    public String getLien1() {
        return Lien1;
    }

    public void setLien1(String lien1) {
        Lien1 = lien1;
    }

    public String getLien2() {
        return Lien2;
    }

    public void setLien2(String lien2) {
        Lien2 = lien2;
    }

    public String getLien3() {
        return Lien3;
    }

    public void setLien3(String lien3) {
        Lien3 = lien3;
    }

    public String getRecherche1() {
        return Recherche1;
    }

    public void setRecherche1(String recherche1) {
        Recherche1 = recherche1;
    }

    public String getRecherche2() {
        return Recherche2;
    }

    public void setRecherche2(String recherche2) {
        Recherche2 = recherche2;
    }

    public String getRecherche3() {
        return Recherche3;
    }

    public void setRecherche3(String recherche3) {
        Recherche3 = recherche3;
    }

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( Image );
        dest.writeString( SHORT_NAME );
        dest.writeString( STATUT_JUR );
        dest.writeString( PROVINCE );
        dest.writeString( Description );
        dest.writeString( Catégorie );
        dest.writeString( Lien1 );
        dest.writeString( Lien2 );
        dest.writeString( Lien3 );
        dest.writeString( Recherche1 );
        dest.writeString( Recherche2 );
        dest.writeString( Recherche3 );
        dest.writeString( REGION );
        dest.writeString( DISTRICT );
        dest.writeString( Type );
    }
}
