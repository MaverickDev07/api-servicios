package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yeri_ on 3/25/2021.
 */
@Entity
@Table(name = "app_controlconsultaservicio", schema = "public")
public class AppControlconsultaservicioEntity {
    private long id;
    private long idAgendacita;
    private long idServicio;
    private String fecha;
    private Integer cantidad;

    @Id
    @SequenceGenerator(name = "APP_CONTROLCONSULTASERVICIO_GENERATOR", sequenceName = "APP_CONTROLCONSULTASERVICIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_CONTROLCONSULTASERVICIO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_agendacita")
    public long getIdAgendacita() {
        return idAgendacita;
    }

    public void setIdAgendacita(long idAgendacita) {
        this.idAgendacita = idAgendacita;
    }

    @Basic
    @Column(name = "id_servicio")
    public long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
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
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppControlconsultaservicioEntity that = (AppControlconsultaservicioEntity) o;

        if (id != that.id) return false;
        if (idAgendacita != that.idAgendacita) return false;
        if (idServicio != that.idServicio) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        return cantidad != null ? cantidad.equals(that.cantidad) : that.cantidad == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idAgendacita ^ (idAgendacita >>> 32));
        result = 31 * result + (int) (idServicio ^ (idServicio >>> 32));
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppControlconsultaservicioEntity{" +
                "id=" + id +
                ", idAgendacita=" + idAgendacita +
                ", idServicio=" + idServicio +
                ", fecha='" + fecha + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
