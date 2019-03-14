package com.biologiemarine.madagascarecotourism;

public class ContactPOJO {

    private String Nom,Image,Langues,Zones; //these names must match with the names in Firebase database

    public ContactPOJO(){} //default constructor

    //Argument Constructor

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
}
