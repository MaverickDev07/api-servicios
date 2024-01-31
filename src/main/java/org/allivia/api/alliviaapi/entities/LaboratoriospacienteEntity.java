package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by yeri_ on 02/06/2021.
 */
@Entity
@Table(name = "laboratoriospaciente", schema = "public")
public class LaboratoriospacienteEntity {
    private String nombre;
    private String apellido;
    private String fecha;
    private String laboratorio;
    private String motivo;
    private String nombrearchivo;
    private Long idPaciente;
    private Long id;
    private Long idArchivoLaboratorios;
    private String fechaSubida;
    private String urlPdf;

    @Basic
    @Column(name = "id_archivolaboratorios")
    public Long getIdArchivoLaboratorios() {
        return idArchivoLaboratorios;
    }

    public void setIdArchivoLaboratorios(Long idArchivoLaboratorios) {
        this.idArchivoLaboratorios = idArchivoLaboratorios;
    }

    @Basic
    @Column(name = "fecha_archivo")
    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Basic
    @Column(name = "archivo")
    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
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
    @Column(name = "apellido")
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Basic
    @Column(name = "fecha")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "laboratorio")
    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Basic
    @Column(name = "motivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Basic
    @Column(name = "nombrearchivo")
    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    @Basic
    @Column(name = "id_paciente")
    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaboratoriospacienteEntity that = (LaboratoriospacienteEntity) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(fecha, that.fecha) && Objects.equals(laboratorio, that.laboratorio) && Objects.equals(motivo, that.motivo) && Objects.equals(nombrearchivo, that.nombrearchivo) && Objects.equals(idPaciente, that.idPaciente) && Objects.equals(id, that.id) && Objects.equals(idArchivoLaboratorios, that.idArchivoLaboratorios) && Objects.equals(fechaSubida, that.fechaSubida) && Objects.equals(urlPdf, that.urlPdf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, fecha, laboratorio, motivo, nombrearchivo, idPaciente, id, idArchivoLaboratorios, fechaSubida, urlPdf);
    }

    @Override
    public String toString() {
        return "LaboratoriospacienteEntity{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fecha='" + fecha + '\'' +
                ", laboratorio='" + laboratorio + '\'' +
                ", motivo='" + motivo + '\'' +
                ", nombrearchivo='" + nombrearchivo + '\'' +
                ", idPaciente=" + idPaciente +
                ", id=" + id +
                ", idArchivoLaboratorios=" + idArchivoLaboratorios +
                ", fechaSubida=" + fechaSubida +
                ", urlPdf='" + urlPdf + '\'' +
                '}';
    }
}
