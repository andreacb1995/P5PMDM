package com.example.andreacarballidop5pmdm.core;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.Timer;

@Entity(tableName = "Tarea")
public class Tarea implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String textoTarea;
    public Date fecha;
    public Date hora;
    public boolean fav;
    public boolean fin;
    private boolean tareaSeleccionada;
    public double latitud;
    public double longitud;


    public Tarea(String textoTarea, Date fecha, Date hora, double latitud, double longitud) {
        this.textoTarea = textoTarea;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        tareaSeleccionada = false;
    }




    public String getTextotarea() {
        return textoTarea;
    }

    public void setTextoTarea(String textoTarea) {
        this.textoTarea = textoTarea;
    }


    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }



    public String getTextoTarea() {
        return textoTarea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFormatoFecha() {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        return formatoFecha.format(fecha);

    }
    public String getFormatoHora() {

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        return formatoHora.format(hora);

    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }


    public boolean isTareaSeleccionada() {
        return tareaSeleccionada;
    }

    public void setTareaSeleccionada(boolean tareaSeleccionada) {
        this.tareaSeleccionada = tareaSeleccionada;
    }
/*
    public String getFormatoHora() {
        SimpleDateFormat formatoHora = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        return formatoHora.format(hora);
    }*/

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void modificarTarea(String textoTarea, Date fecha, Date hora, double latitud, double longitud) {
        this.textoTarea = textoTarea;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
    }


}

