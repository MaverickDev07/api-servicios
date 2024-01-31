package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/25/2021.
 */
@Entity
@Table(name = "app_controlsuscripcionservicio", schema = "public")
public class AppControlsuscripcionservicioEntity {
    private long id;
    private long idServicio;
    private long idPaciente;
    private Integer cantidad;
    private Boolean estado;
    private Long idSuscripcion;
    private List<AppSuscripcionservicioEntity> suscripcionServicio;


    @Transient
    public Long getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(Long idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    @Transient
    public List<AppSuscripcionservicioEntity> getSuscripcionServicio() {
        return suscripcionServicio;
    }

    public void setSuscripcionServicio(List<AppSuscripcionservicioEntity> suscripcionServicio) {
        this.suscripcionServicio = suscripcionServicio;
    }





    @Id
    @SequenceGenerator(name = "APP_CONTROLSUSCRIPCIONSERVICIO_GENERATOR", sequenceName = "APP_CONTROLSUSCRIPCIONSERVICIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_CONTROLSUSCRIPCIONSERVICIO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "id_paciente")
    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Basic
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

        AppControlsuscripcionservicioEntity that = (AppControlsuscripcionservicioEntity) o;

        if (id != that.id) return false;
        if (idServicio != that.idServicio) return false;
        if (idPaciente != that.idPaciente) return false;
        if (cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null) return false;
        return estado != null ? estado.equals(that.estado) : that.estado == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idServicio ^ (idServicio >>> 32));
        result = 31 * result + (int) (idPaciente ^ (idPaciente >>> 32));
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppControlsuscripcionservicioEntity{" +
                "id=" + id +
                ", idServicio=" + idServicio +
                ", idPaciente=" + idPaciente +
                ", cantidad=" + cantidad +
                ", estado=" + estado +
                '}';
    }
}
