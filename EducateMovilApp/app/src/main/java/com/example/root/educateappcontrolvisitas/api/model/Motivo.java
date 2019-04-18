package com.example.root.educateappcontrolvisitas.api.model;

public class Motivo {

    private String motivoId;
    private String motivoNombre;

    public Motivo(String motivoId, String motivoNombre) {
        this.motivoId = motivoId;
        this.motivoNombre = motivoNombre;
    }

    public String getMotivoId() {
        return motivoId;
    }

    public void setMotivoId(String motivoId) {
        this.motivoId = motivoId;
    }

    public String getMotivoNombre() {
        return motivoNombre;
    }

    public void setMotivoNombre(String motivoNombre) {
        this.motivoNombre = motivoNombre;
    }

}
