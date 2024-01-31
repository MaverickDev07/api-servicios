package org.allivia.api.alliviaapi.entities;

import lombok.ToString;

/**
 * Created by yeri_ on 24/06/2021.
 */
@ToString
public class AppPagoSuscripcionPaciente {

    private long idSuscripcion;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String fechaVencimiento;
    private boolean predeterminado;
    private String nombre;
    private long idPaciente;
    private String token;
    private boolean guardarTarjeta;
    private String cvn;
    private Double monto;
    private String nitComprador;
    private String razonSocial;
    private String tipoAgenda;

    public String getTipoAgenda() { return tipoAgenda; }

    public void setTipoAgenda(String tipoAgenda) { this.tipoAgenda = tipoAgenda; }

    public String getNitComprador() {
        return nitComprador;
    }

    public void setNitComprador(String nitComprador) {
        this.nitComprador = nitComprador;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public long getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(long idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isGuardarTarjeta() {
        return guardarTarjeta;
    }

    public void setGuardarTarjeta(boolean guardarTarjeta) {
        this.guardarTarjeta = guardarTarjeta;
    }

    public String getCvn() {
        return cvn;
    }

    public void setCvn(String cvn) {
        this.cvn = cvn;
    }
}
