package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_educacion", schema = "public")
public class AppEducacionEntity {
    private long id;
    private String descripcion;
    private String nombre;

    @Id
    @SequenceGenerator(name = "APP_EDUCACION_EDUCACIONID_GENERATOR", sequenceName = "APP_EDUCACION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_EDUCACION_EDUCACIONID_GENERATOR")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppEducacionEntity that = (AppEducacionEntity) o;

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

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "AppEducacionEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
