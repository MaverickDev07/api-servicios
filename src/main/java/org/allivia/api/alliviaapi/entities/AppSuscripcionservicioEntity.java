package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by yeri_ on 3/15/2021.
 */
@Entity
@Table(name = "app_suscripcionservicio", schema = "public")
public class AppSuscripcionservicioEntity {
    private long id;
    private long idSuscripcion;
    private long idServicio;
    private Integer cantidad;
    private BigInteger descuento;
    private Float precio;
    private Float precioPresencial;

    private AppSuscripcionEntity suscripcion;

    @Transient
    public AppSuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(AppSuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
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
    @Column(name = "id_suscripcion")
    public long getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(long idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
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
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "descuento")
    public BigInteger getDescuento() {
        return descuento;
    }

    public void setDescuento(BigInteger descuento) {
        this.descuento = descuento;
    }

    @Basic
    @Column(name = "precio")
    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Basic
    @Column(name = "precioPresencial")
    public Float getPrecioPresencial() { return precioPresencial; }

    public void setPrecioPresencial(Float precioPresencial) { this.precioPresencial = precioPresencial; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppSuscripcionservicioEntity that = (AppSuscripcionservicioEntity) o;
        return id == that.id && idSuscripcion == that.idSuscripcion && idServicio == that.idServicio && Objects.equals(cantidad, that.cantidad) && Objects.equals(descuento, that.descuento) && Objects.equals(precio, that.precio) && Objects.equals(precioPresencial, that.precioPresencial) && Objects.equals(suscripcion, that.suscripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idSuscripcion, idServicio, cantidad, descuento, precio, precioPresencial, suscripcion);
    }

    @Override
    public String toString() {
        return "AppSuscripcionservicioEntity{" +
                "id=" + id +
                ", idSuscripcion=" + idSuscripcion +
                ", idServicio=" + idServicio +
                ", cantidad=" + cantidad +
                ", descuento=" + descuento +
                ", precio=" + precio +
                ", precioPresencial=" + precioPresencial +
                ", suscripcion=" + suscripcion +
                '}';
    }
}
