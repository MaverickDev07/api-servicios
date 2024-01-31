package org.allivia.api.alliviaapi.entities;


import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/11/2021.
 */
@Entity
@Table(name = "app_paciente")
public class AppPacienteEntity {
    private long id;
//    private long idSuscripcion;
    private Long usuarioId;
    private String descripcion;

    private AppUsuarioEntity perfilPaciente;

    @Transient
    public AppUsuarioEntity getPerfilPaciente() {
        return perfilPaciente;
    }

    public void setPerfilPaciente(AppUsuarioEntity perfilPaciente) {
        this.perfilPaciente = perfilPaciente;
    }

    private AppSuscripcionEntity suscripcion;
    private AppSuscripcionpacienteEntity suscripcionPaciente;
    private List<AppControlsuscripcionservicioEntity> servicios;

    @Transient
    public AppSuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(AppSuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }

    @Transient
    public AppSuscripcionpacienteEntity getSuscripcionPaciente() {
        return suscripcionPaciente;
    }

    public void setSuscripcionPaciente(AppSuscripcionpacienteEntity suscripcionPaciente) {
        this.suscripcionPaciente = suscripcionPaciente;
    }

    @Transient
    public List<AppControlsuscripcionservicioEntity> getServicios() {
        return servicios;
    }

    public void setServicios(List<AppControlsuscripcionservicioEntity> servicios) {
        this.servicios = servicios;
    }





    @Id
    @SequenceGenerator(name = "APP_PACIENTE_PACIENTEID_GENERATOR", sequenceName = "APP_PACIENTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_PACIENTE_PACIENTEID_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    @Basic
//    @Column(name = "id_suscripcion")
//    public long getIdSuscripcion() {
//        return idSuscripcion;
//    }
//
//    public void setIdSuscripcion(long idSuscripcion) {
//        this.idSuscripcion = idSuscripcion;
//    }

    @Basic
    @Column(name = "usuario_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppPacienteEntity that = (AppPacienteEntity) o;

        if (id != that.id) return false;
//        if (idSuscripcion != that.idSuscripcion) return false;
        if (usuarioId != null ? !usuarioId.equals(that.usuarioId) : that.usuarioId != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
//        result = 31 * result + (int) (idSuscripcion ^ (idSuscripcion >>> 32));
        result = 31 * result + (usuarioId != null ? usuarioId.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppPacienteEntity{" +
                "id=" + id +
//                ", idSuscripcion=" + idSuscripcion +
                ", usuarioId=" + usuarioId +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }


}
