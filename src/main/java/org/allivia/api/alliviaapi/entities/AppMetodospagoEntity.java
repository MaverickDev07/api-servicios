package org.allivia.api.alliviaapi.entities;

import lombok.ToString;

import javax.persistence.*;

/**
 * Created by yeri_ on 31/05/2021.
 */
@Entity
@Table(name = "app_metodospago", schema = "public")
@ToString
public class AppMetodospagoEntity {
    private long id;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String fechaVencimiento;
    private boolean predeterminado;
    private String nombre;
    private long idPaciente;
    private String token;
    private String cvc;

    @Id
    @SequenceGenerator(name = "APP_METODOSPAGO_GENERATOR", sequenceName = "APP_METODOSPAGO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_METODOSPAGO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "numero_tarjeta")
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Basic
    @Column(name = "tipo_tarjeta")
    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    @Basic
    @Column(name = "fecha_vencimiento")
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Basic
    @Column(name = "predeterminado")
    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "id_paciente")
    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppMetodospagoEntity that = (AppMetodospagoEntity) o;

        if (id != that.id) return false;
        if (predeterminado != that.predeterminado) return false;
        if (idPaciente != that.idPaciente) return false;
        if (numeroTarjeta != null ? !numeroTarjeta.equals(that.numeroTarjeta) : that.numeroTarjeta != null)
            return false;
        if (tipoTarjeta != null ? !tipoTarjeta.equals(that.tipoTarjeta) : that.tipoTarjeta != null) return false;
        if (fechaVencimiento != null ? !fechaVencimiento.equals(that.fechaVencimiento) : that.fechaVencimiento != null)
            return false;
        return nombre != null ? nombre.equals(that.nombre) : that.nombre == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (numeroTarjeta != null ? numeroTarjeta.hashCode() : 0);
        result = 31 * result + (tipoTarjeta != null ? tipoTarjeta.hashCode() : 0);
        result = 31 * result + (fechaVencimiento != null ? fechaVencimiento.hashCode() : 0);
        result = 31 * result + (predeterminado ? 1 : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (int) (idPaciente ^ (idPaciente >>> 32));
        return result;
    }

    @Transient
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
