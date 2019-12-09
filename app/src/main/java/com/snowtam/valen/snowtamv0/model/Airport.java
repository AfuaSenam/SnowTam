package com.snowtam.valen.snowtamv0.model;

public class Airport {

    private String nom;
    private String ville;
    private String pays;
    private String OACI;
    private Double lat;
    private Double lon;

    public Airport(String nom, String ville, String pays, String OACI, Double lat, Double lon) {
        this.nom = nom;
        this.ville = ville;
        this.pays = pays;
        this.OACI = OACI;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNom() {
        return nom;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public String getOACI() {
        return OACI;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
