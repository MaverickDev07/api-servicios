package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 15/04/2021.
 */
@Entity
@Table(name = "app_laboratorios", schema = "public")
public class AppLaboratoriosEntity {
    private long id;
    private String descripcion;
    private boolean eliminado;

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

        AppLaboratoriosEntity that = (AppLaboratoriosEntity) o;

        if (id != that.id) return false;
        if (eliminado != that.eliminado) return false;
        return descripcion != null ? descripcion.equals(that.descripcion) : that.descripcion == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (eliminado ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppLaboratoriosEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }
}
