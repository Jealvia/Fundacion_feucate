package com.example.root.educateappcontrolvisitas.api.model;

import android.graphics.Bitmap;

public class Imagen {

    private String nombre;
    private String ruta;
    private Bitmap bitmap;

    public Imagen(String nombre, String ruta, Bitmap bitmap) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.bitmap = bitmap;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
