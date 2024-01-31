package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yeri_ on 3/18/2021.
 */
@Entity
@Table(name = "app_horarioatencion", schema = "public")
public class AppHorarioatencionEntity {
    private long id;
    private long idDias;
    private long idDoctor;
    private long idHoras;
    private String fecha;
    private Boolean estado;

    @Id
    @SequenceGenerator(name = "APP_HORARIOATENCION_GENERATOR", sequenceName = "APP_HORARIOATENCION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_HORARIOATENCION_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_dias")
    public long getIdDias() {
        return idDias;
    }

    public void setIdDias(long idDias) {
        this.idDias = idDias;
    }

    @Basic
    @Column(name = "id_doctor")
    public long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(long idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Basic
    @Column(name = "id_horas")
    public long getIdHoras() {
        return idHoras;
    }

    public void setIdHoras(long idHoras) {
        this.idHoras = idHoras;
    }

    @Basic
    @Column(name = "fecha")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "estado")
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppHorarioatencionEntity that = (AppHorarioatencionEntity) o;

        if (id != that.id) return false;
        if (idDias != that.idDias) return false;
        if (idDoctor != that.idDoctor) return false;
        if (idHoras != that.idHoras) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        return estado != null ? estado.equals(that.estado) : that.estado == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idDias ^ (idDias >>> 32));
        result = 31 * result + (int) (idDoctor ^ (idDoctor >>> 32));
        result = 31 * result + (int) (idHoras ^ (idHoras >>> 32));
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppHorarioatencionEntity{" +
                "id=" + id +
                ", idDias=" + idDias +
                ", idDoctor=" + idDoctor +
                ", idHoras=" + idHoras +
                ", fecha='" + fecha + '\'' +
                ", estado=" + estado +
                '}';
    }
}
