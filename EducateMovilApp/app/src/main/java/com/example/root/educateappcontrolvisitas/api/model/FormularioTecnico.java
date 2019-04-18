package com.example.root.educateappcontrolvisitas.api.model;

public class FormularioTecnico implements Comparable{

    private int id;
    private String actividadesRealizadas;
    private String accionTomada;
    private String observaciones;

    public FormularioTecnico(int id, String actividadesRealizadas, String accionTomada, String observaciones) {
        this.id = id;
        this.actividadesRealizadas = actividadesRealizadas;
        this.accionTomada = accionTomada;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActividadesRealizadas() {
        return actividadesRealizadas;
    }

    public void setActividadesRealizadas(String actividadesRealizadas) {
        this.actividadesRealizadas = actividadesRealizadas;
    }

    public String getAccionTomada() {
        return accionTomada;
    }

    public void setAccionTomada(String accionTomada) {
        this.accionTomada = accionTomada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int compareTo(Object o) {
        int id2 = ((FormularioTecnico) o).getId();
        //Descendente
        return id2 - this.getId();
    }
}
