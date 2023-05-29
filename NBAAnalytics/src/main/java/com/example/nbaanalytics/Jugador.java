package com.example.nbaanalytics;

public class Jugador {

    private int puntos;
    private int asistencias;
    private int rebotes;
    private int tapones;

    public Jugador() {
    }

    public Jugador(int puntos, int asistencias, int rebotes, int tapones) {
        this.puntos = puntos;
        this.asistencias = asistencias;
        this.rebotes = rebotes;
        this.tapones = tapones;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getTapones() {
        return tapones;
    }

    public void setTapones(int tapones) {
        this.tapones = tapones;
    }
}
