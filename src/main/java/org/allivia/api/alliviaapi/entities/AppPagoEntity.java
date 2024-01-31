package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/22/2021.
 */
@Entity
@Table(name = "app_pago", schema = "public")
public class AppPagoEntity {
    private long id;
    private String transaccion;
    private String detalle;
    private Double monto;
    private String tipo;
    private String estado;

    @Id
    @SequenceGenerator(name = "APP_PAGO_GENERATOR", sequenceName = "APP_PAGO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_PAGO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "transaccion")
    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
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
    @Column(name = "monto")
    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Basic
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppPagoEntity that = (AppPagoEntity) o;

        if (id != that.id) return false;
        if (transaccion != null ? !transaccion.equals(that.transaccion) : that.transaccion != null) return false;
        if (detalle != null ? !detalle.equals(that.detalle) : that.detalle != null) return false;
        if (monto != null ? !monto.equals(that.monto) : that.monto != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (transaccion != null ? transaccion.hashCode() : 0);
        result = 31 * result + (detalle != null ? detalle.hashCode() : 0);
        result = 31 * result + (monto != null ? monto.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppPagoEntity{" +
                "id=" + id +
                ", transaccion='" + transaccion + '\'' +
                ", detalle='" + detalle + '\'' +
                ", monto=" + monto +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
