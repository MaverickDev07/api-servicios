package org.allivia.api.alliviaapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/15/2021.
 */
@Entity
@Table(name = "app_suscripcion", schema = "public")
public class AppSuscripcionEntity {
    private long id;
    private String descripcion;
    private String detalle;
    private Double precio;
    private boolean eliminado;
    private String codigo;

    @Getter @Setter
    private String tipoAgenda;

    private transient List<AppServicioEntity> listServicios;


    @Transient
    public List<AppServicioEntity> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<AppServicioEntity> listServicios) {
        this.listServicios = listServicios;
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
    @Column(name = "detalle")
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Basic
    @Column(name = "precio")
    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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
    @Column(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppSuscripcionEntity that = (AppSuscripcionEntity) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (detalle != null ? !detalle.equals(that.detalle) : that.detalle != null) return false;
        if (precio != null ? !precio.equals(that.precio) : that.precio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (detalle != null ? detalle.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppSuscripcionEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", detalle='" + detalle + '\'' +
                ", precio='" + precio + '\'' +
                ", listServicios=" + listServicios +
                '}';
    }
}
