package org.allivia.api.alliviaapi.entities;

import java.util.List;

public class InformacionLaboral {
    long doctorId;
    List<AppAfiliacionesEntity> afiliaciones;
    List<AppEspecialidadEntity> especialidades;

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public List<AppAfiliacionesEntity> getAfiliaciones() {
        return afiliaciones;
    }

    public void setAfiliaciones(List<AppAfiliacionesEntity> afiliaciones) {
        this.afiliaciones = afiliaciones;
    }

    public List<AppEspecialidadEntity> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<AppEspecialidadEntity> especialidades) {
        this.especialidades = especialidades;
    }
}
