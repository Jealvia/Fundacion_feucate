package com.example.root.educateappcontrolvisitas.api.model;

public class Responsable {

    public String responsableId;
    public String responsableNombreUsuario;

    public Responsable(String responsableId, String responsableNombreUsuario) {
        this.responsableId = responsableId;
        this.responsableNombreUsuario = responsableNombreUsuario;
    }

    public String getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }

    public String getResponsableNombreUsuario() {
        return responsableNombreUsuario;
    }

    public void setResponsableNombreUsuario(String responsableNombreUsuario) {
        this.responsableNombreUsuario = responsableNombreUsuario;
    }
}
