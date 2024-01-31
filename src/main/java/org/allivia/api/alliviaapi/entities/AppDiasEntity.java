package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/18/2021.
 */
@Entity
@Table(name = "app_dias", schema = "public")
public class AppDiasEntity {
    private long id;
    private String descripcion;
    private boolean eliminado;

    private List<AppHorasEntity> horasAtencion;


    @Transient
    public List<AppHorasEntity> getHorasAtencion() {
        return horasAtencion;
    }

    public void setHorasAtencion(List<AppHorasEntity> horasAtencion) {
        this.horasAtencion = horasAtencion;
    }




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

        AppDiasEntity that = (AppDiasEntity) o;

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
        return "AppDiasEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", horasAtencion=" + horasAtencion +
                '}';
    }
}
