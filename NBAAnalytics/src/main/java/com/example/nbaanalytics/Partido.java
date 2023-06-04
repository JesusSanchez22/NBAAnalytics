package com.example.nbaanalytics;

public class Partido {


    private String equipo_local;
    private String equipo_visitante;
    private int puntos_local;
    private int puntos_visitante;


    public Partido(String equipo_local, String equipo_visitante, int puntos_local, int puntos_visitante) {

        this.equipo_local = equipo_local;
        this.equipo_visitante = equipo_visitante;
        this.puntos_local = puntos_local;
        this.puntos_visitante = puntos_visitante;

    }


    public String getEquipo_local() {
        return equipo_local;
    }

    public void setEquipo_local(String equipo_local) {
        this.equipo_local = equipo_local;
    }

    public String getEquipo_visitante() {
        return equipo_visitante;
    }

    public void setEquipo_visitante(String equipo_visitante) {
        this.equipo_visitante = equipo_visitante;
    }

    public int getPuntos_local() {
        return puntos_local;
    }

    public void setPuntos_local(int puntos_local) {
        this.puntos_local = puntos_local;
    }

    public int getPuntos_visitante() {
        return puntos_visitante;
    }

    public void setPuntos_visitante(int puntos_visitante) {
        this.puntos_visitante = puntos_visitante;
    }


}
