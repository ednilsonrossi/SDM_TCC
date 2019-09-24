package br.pro.ednilsonrossi.visitalocal.model;

public class Coordenada {

    private double latitude;
    private double longitude;

    public Coordenada() {
        latitude = 0;
        longitude = 0;
    }

    public Coordenada(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
