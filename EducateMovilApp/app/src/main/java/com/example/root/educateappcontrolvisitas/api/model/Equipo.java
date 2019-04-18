package com.example.root.educateappcontrolvisitas.api.model;

public class Equipo {

    private String descripcion;
    private int cantidad;
    private String serie_del_equipo;
    private String codigo_municipal;


    public Equipo(String descripcion, int cantidad, String serie_del_equipo, String codigo_municipal) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.serie_del_equipo = serie_del_equipo;
        this.codigo_municipal = codigo_municipal;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getSerie_del_equipo() {
        return serie_del_equipo;
    }

    public void setSerie_del_equipo(String serie_del_equipo) {
        this.serie_del_equipo = serie_del_equipo;
    }

    public String getCodigo_municipal() {
        return codigo_municipal;
    }

    public void setCodigo_municipal(String codigo_municipal) {
        this.codigo_municipal = codigo_municipal;
    }
}
