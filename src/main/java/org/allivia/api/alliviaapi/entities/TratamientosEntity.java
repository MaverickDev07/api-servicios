package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 08/07/2021.
 */
@Entity
@Table(name = "tratamientos", schema = "public")
public class TratamientosEntity {
    private Long id;
    private Long idcita;
    private String nombreMedicamento;
    private String indicaciones;
    private Boolean tratamiento;
    private String duracion;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "idcita")
    public Long getIdcita() {
        return idcita;
    }

    public void setIdcita(Long idcita) {
        this.idcita = idcita;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TratamientosEntity that = (TratamientosEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idcita != null ? !idcita.equals(that.idcita) : that.idcita != null) return false;
        if (nombreMedicamento != null ? !nombreMedicamento.equals(that.nombreMedicamento) : that.nombreMedicamento != null)
            return false;
        if (indicaciones != null ? !indicaciones.equals(that.indicaciones) : that.indicaciones != null) return false;
        if (tratamiento != null ? !tratamiento.equals(that.tratamiento) : that.tratamiento != null) return false;
        if (duracion != null ? !duracion.equals(that.duracion) : that.duracion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idcita != null ? idcita.hashCode() : 0);
        result = 31 * result + (nombreMedicamento != null ? nombreMedicamento.hashCode() : 0);
        result = 31 * result + (indicaciones != null ? indicaciones.hashCode() : 0);
        result = 31 * result + (tratamiento != null ? tratamiento.hashCode() : 0);
        result = 31 * result + (duracion != null ? duracion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TratamientosEntity{" +
                "id=" + id +
                ", idcita=" + idcita +
                ", nombreMedicamento='" + nombreMedicamento + '\'' +
                ", indicaciones='" + indicaciones + '\'' +
                ", tratamiento=" + tratamiento +
                ", duracion='" + duracion + '\'' +
                '}';
    }
}
