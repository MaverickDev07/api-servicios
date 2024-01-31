package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yeri_ on 3/15/2021.
 */
@Entity
@Table(name = "app_parametros", schema = "public")
public class AppParametrosEntity {
    private String clave;
    private String valor;
    private String parType;
    private String descripcion;
    private long parametrosId;
    private Timestamp fechaFin;
    private String regexValidator;
    private Integer parOrder;
    private Timestamp modificado;
    private String modificadoPor;

    @Basic
    @Column(name = "clave")
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Basic
    @Column(name = "valor")
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Basic
    @Column(name = "par_type")
    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Id
    @Column(name = "parametros_id")
    public long getParametrosId() {
        return parametrosId;
    }

    public void setParametrosId(long parametrosId) {
        this.parametrosId = parametrosId;
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
    @Column(name = "regex_validator")
    public String getRegexValidator() {
        return regexValidator;
    }

    public void setRegexValidator(String regexValidator) {
        this.regexValidator = regexValidator;
    }

    @Basic
    @Column(name = "par_order")
    public Integer getParOrder() {
        return parOrder;
    }

    public void setParOrder(Integer parOrder) {
        this.parOrder = parOrder;
    }

    @Basic
    @Column(name = "modificado")
    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    @Basic
    @Column(name = "modificado_por")
    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppParametrosEntity that = (AppParametrosEntity) o;

        if (parametrosId != that.parametrosId) return false;
        if (clave != null ? !clave.equals(that.clave) : that.clave != null) return false;
        if (valor != null ? !valor.equals(that.valor) : that.valor != null) return false;
        if (parType != null ? !parType.equals(that.parType) : that.parType != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (regexValidator != null ? !regexValidator.equals(that.regexValidator) : that.regexValidator != null)
            return false;
        if (parOrder != null ? !parOrder.equals(that.parOrder) : that.parOrder != null) return false;
        if (modificado != null ? !modificado.equals(that.modificado) : that.modificado != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(that.modificadoPor) : that.modificadoPor != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clave != null ? clave.hashCode() : 0;
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + (parType != null ? parType.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (int) (parametrosId ^ (parametrosId >>> 32));
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (regexValidator != null ? regexValidator.hashCode() : 0);
        result = 31 * result + (parOrder != null ? parOrder.hashCode() : 0);
        result = 31 * result + (modificado != null ? modificado.hashCode() : 0);
        result = 31 * result + (modificadoPor != null ? modificadoPor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppParametrosEntity{" +
                "clave='" + clave + '\'' +
                ", valor='" + valor + '\'' +
                ", parType='" + parType + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", parametrosId=" + parametrosId +
                ", fechaFin=" + fechaFin +
                ", regexValidator='" + regexValidator + '\'' +
                ", parOrder=" + parOrder +
                ", modificado=" + modificado +
                ", modificadoPor='" + modificadoPor + '\'' +
                '}';
    }
}
