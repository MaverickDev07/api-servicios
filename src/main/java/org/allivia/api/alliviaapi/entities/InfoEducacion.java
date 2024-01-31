package org.allivia.api.alliviaapi.entities;

import java.util.List;

public class InfoEducacion {
    long doctorId;
    boolean create;
    String nroLicencia;
    String pais;
    List<AppDoctoreducacionEntity> doctoreducaciones;

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getNroLicencia() {
        return nroLicencia;
    }

    public void setNroLicencia(String nroLicencia) {
        this.nroLicencia = nroLicencia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<AppDoctoreducacionEntity> getDoctoreducaciones() {
        return doctoreducaciones;
    }

    public void setDoctoreducaciones(List<AppDoctoreducacionEntity> doctoreducaciones) {
        this.doctoreducaciones = doctoreducaciones;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }
}
