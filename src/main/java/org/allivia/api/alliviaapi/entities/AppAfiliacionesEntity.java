package org.allivia.api.alliviaapi.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_afiliaciones", schema = "public")
@ToString
@EqualsAndHashCode
public class AppAfiliacionesEntity {
    private long id;
    private String descripcion;
    private String telefono;
    private boolean eliminado;
    private String latitud;
    private String longitud;
    private String direccion;
    private String web;
    private String tipoEntidad;

    @Basic
    @Column(name = "tipoEntidad")
    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
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
    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "eliminado")
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Basic
    @Column(name = "latitud")
    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    @Basic
    @Column(name = "longitud")
    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Basic
    @Column(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Basic
    @Column(name = "web")
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
