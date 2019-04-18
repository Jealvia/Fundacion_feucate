package com.example.root.educateappcontrolvisitas.api.model;

import android.net.Uri;

import java.io.File;

public class Evidencias {

    private String nombreArchivo;
    private String pathEvidencia;

    public Evidencias(String nombreArchivo, String pathEvidencia) {
        this.nombreArchivo = nombreArchivo;
        this.pathEvidencia = pathEvidencia;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getPathEvidencia() {
        return pathEvidencia;
    }

    public void setPathEvidencia(String pathEvidencia) {
        this.pathEvidencia = pathEvidencia;
    }
}
