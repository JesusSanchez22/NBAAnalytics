package com.example.nbaanalytics;

public class Jugador {

    private String nombre;

    private String temporada;
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

    public Jugador(String temporada, String nombre, int puntos, int asistencias, int rebotes, int tapones) {
        this.nombre = nombre;
        this.temporada = temporada;
        this.puntos = puntos;
        this.asistencias = asistencias;
        this.rebotes = rebotes;
        this.tapones = tapones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
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
