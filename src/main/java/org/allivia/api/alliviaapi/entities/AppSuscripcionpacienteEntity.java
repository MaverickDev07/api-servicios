package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by yeri_ on 3/25/2021.
 */
@Entity
@Table(name = "app_suscripcionpaciente", schema = "public")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppSuscripcionpacienteEntity {
    private long id;
    private long idSuscripcion;
    private long idPaciente;
    private Long idPago;
    private Date vigencia;
    private String estado;
    private String motivo;
    private Date fecha;
    @Getter @Setter
    private String tipoAgenda;


    public AppSuscripcionEntity suscripcion;
    public AppPagoEntity pago;

    @Transient
    public AppSuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(AppSuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }


    @Transient
    public AppPagoEntity getPago() {
        return pago;
    }

    public void setPago(AppPagoEntity pago) {
        this.pago = pago;
    }

    @Id
    @SequenceGenerator(name = "APP_SUSCRIPCIONPACIENTE_GENERATOR", sequenceName = "APP_SUSCRIPCIONPACIENTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_SUSCRIPCIONPACIENTE_GENERATOR")
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
    @Column(name = "id_paciente")
    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Basic
    @Column(name = "id_pago")
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    @Basic
    @Column(name = "vigencia")
    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    @Basic
    @Column(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Basic
    @Column(name = "motivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
