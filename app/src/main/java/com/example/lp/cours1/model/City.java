package com.example.lp.cours1.model;

public class City {

    private String Nom_Ville;
    private String MAJ;
    private String Code_Postal;
    private String Code_INSEE;
    private String Code_Region;
    private String Latitude;
    private String Longitude;
    private String Eloignement;

    public String getNom_Ville() {
        return Nom_Ville;
    }

    public String getMAJ() {
        return MAJ;
    }

    public String getCode_Postal() {
        return Code_Postal;
    }

    public String getCode_INSEE() {
        return Code_INSEE;
    }

    public String getCode_Region() {
        return Code_Region;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getEloignement() {
        return Eloignement;
    }

    public void setNom_Ville(String nom_Ville) {
        Nom_Ville = nom_Ville;
    }

    public void setMAJ(String MAJ) {
        this.MAJ = MAJ;
    }

    public void setCode_Postal(String code_Postal) {
        Code_Postal = code_Postal;
    }

    public void setCode_INSEE(String code_INSEE) {
        Code_INSEE = code_INSEE;
    }

    public void setCode_Region(String code_Region) {
        Code_Region = code_Region;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public void setEloignement(String eloignement) {
        Eloignement = eloignement;
    }
}