package org.allivia.api.alliviaapi.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "app_fichamedicamentos", schema = "public")
@ToString
@EqualsAndHashCode
public class AppFichamedicamentosEntity {
    private long id;
    private long idFichamedica;
    private long idAgendacita;
    private Integer idMedicamento;
    private String nombreMedicamento;
    private String indicaciones;
    private Boolean tratamiento;
    private String duracion;
    private String dosis;
    private Integer cantidad;
    private String fecha;

    @Transient
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private Optional<AppMedicamentosEntity> medicamento;

    @Transient
    public Optional<AppMedicamentosEntity> getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Optional<AppMedicamentosEntity> medicamento) {
        this.medicamento = medicamento;
    }

    @Transient
    public long getIdAgendacita() {
        return idAgendacita;
    }

    public void setIdAgendacita(long idAgendacita) {
        this.idAgendacita = idAgendacita;
    }

    @Id
    @SequenceGenerator(name = "APP_FICHAMEDICAMENTOS_GENERATOR", sequenceName = "APP_FICHAMEDICAMENTOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHAMEDICAMENTOS_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_fichamedica")
    public long getIdFichamedica() {
        return idFichamedica;
    }

    public void setIdFichamedica(long idFichamedica) {
        this.idFichamedica = idFichamedica;
    }

    @Basic
    @Column(name = "id_medicamento")
    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamentos) {
        this.idMedicamento = idMedicamentos;
    }

    @Basic
    @Column(name = "nombre_medicamento")
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    @Basic
    @Column(name = "indicaciones")
    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    @Basic
    @Column(name = "tratamiento")
    public Boolean getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Boolean tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Basic
    @Column(name = "duracion")
    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Basic
    @Column(name = "dosis")
    public String getDosis() { return dosis; }

    public void setDosis(String dosis) { this.dosis = dosis; }

    @Basic
    @Column(name = "cantidad")
    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
