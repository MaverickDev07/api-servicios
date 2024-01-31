package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_especialidad", schema = "public")
public class AppEspecialidadEntity {
    private long id;
    private String descripcion;
    private boolean eliminado;

//    private List<AppDoctorEntity> listDoctor;
//
//    @Transient
//    public List<AppDoctorEntity> getListDoctor() {
//        return listDoctor;
//    }
//
//    public void setListDoctor(List<AppDoctorEntity> listDoctor) {
//        this.listDoctor = listDoctor;
//    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "eliminado")
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppEspecialidadEntity that = (AppEspecialidadEntity) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppEspecialidadEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
