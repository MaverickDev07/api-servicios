package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 06/04/2021.
 */
@Entity
@Table(name = "app_titulo", schema = "public")
public class AppTituloEntity {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "descripcion")
    private String nombre;
    private boolean eliminado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String descripcion) {
        this.nombre = descripcion;
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

        AppTituloEntity that = (AppTituloEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppTituloEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
