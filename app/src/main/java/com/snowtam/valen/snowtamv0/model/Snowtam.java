package com.snowtam.valen.snowtamv0.model;

public class Snowtam {
    String OACI;
    String name;
    String city;
    String country;
    int id;
    double lat;
    double lon;

    public Snowtam(String OACI, String name, String city, String country, int id, double lat, double lon) {
        this.OACI = OACI;
        this.name = name;
        this.city = city;
        this.country = country;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public void setOACI(String OACI) {
        this.OACI = OACI;
    }

    public String getOACI() {

        return OACI;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {

        return lat;
    }

    public double getLon() {
        return lon;
    }
}
