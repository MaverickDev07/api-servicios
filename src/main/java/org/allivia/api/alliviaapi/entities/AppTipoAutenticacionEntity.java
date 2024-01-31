package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yeri_ on 3/11/2021.
 */
@Entity
@Table(name = "app_tipo_autenticacion")
public class AppTipoAutenticacionEntity {
    private String nombre;
    private int tipoAutenticacionId;
    private String descripcion;
    private Timestamp fechaFin;
    private String clave;

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Id
    @Column(name = "tipo_autenticacion_id")
    public int getTipoAutenticacionId() {
        return tipoAutenticacionId;
    }

    public void setTipoAutenticacionId(int tipoAutenticacionId) {
        this.tipoAutenticacionId = tipoAutenticacionId;
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
    @Column(name = "fecha_fin")
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic
    @Column(name = "clave")
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppTipoAutenticacionEntity that = (AppTipoAutenticacionEntity) o;

        if (tipoAutenticacionId != that.tipoAutenticacionId) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (clave != null ? !clave.equals(that.clave) : that.clave != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + tipoAutenticacionId;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (clave != null ? clave.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppTipoAutenticacionEntity{" +
                "nombre='" + nombre + '\'' +
                ", tipoAutenticacionId=" + tipoAutenticacionId +
                ", descripcion='" + descripcion + '\'' +
                ", fechaFin=" + fechaFin +
                ", clave='" + clave + '\'' +
                '}';
    }
}
