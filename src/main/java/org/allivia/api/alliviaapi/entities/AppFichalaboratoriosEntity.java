package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_fichalaboratorios", schema = "public")
public class AppFichalaboratoriosEntity {
    private long id;
    private long idFichamedica;
    private long idLaboratorios;
    private long idLaboratorioEmpresa;

    private String descripcion;
    private String fecha;
    private String estado;

    private AppLaboratoriosEntity laboratorio;

    @Transient
    public AppLaboratoriosEntity getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(AppLaboratoriosEntity laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Transient
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Id
    @SequenceGenerator(name = "APP_FICHALABORATORIOS_GENERATOR", sequenceName = "APP_FICHALABORATORIOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHALABORATORIOS_GENERATOR")
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
    @Column(name = "id_laboratorios")
    public long getIdLaboratorios() {
        return idLaboratorios;
    }

    public void setIdLaboratorios(long idLaboratorios) {
        this.idLaboratorios = idLaboratorios;
    }

    @Basic
    @Column(name = "id_laboratorio_empresa")
    public long getIdLaboratorioEmpresa() {
        return idLaboratorioEmpresa;
    }

    public void setIdLaboratorioEmpresa(long idLaboratorioEmpresa) {
        this.idLaboratorioEmpresa = idLaboratorioEmpresa;
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
        AppFichalaboratoriosEntity that = (AppFichalaboratoriosEntity) o;
        return id == that.id && idFichamedica == that.idFichamedica && idLaboratorios == that.idLaboratorios && idLaboratorioEmpresa == that.idLaboratorioEmpresa && Objects.equals(descripcion, that.descripcion) && Objects.equals(estado, that.estado) && Objects.equals(laboratorio, that.laboratorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idFichamedica, idLaboratorios, idLaboratorioEmpresa, descripcion, estado, laboratorio);
    }

    @Override
    public String toString() {
        return "AppFichalaboratoriosEntity{" +
                "id=" + id +
                ", idFichamedica=" + idFichamedica +
                ", idLaboratorios=" + idLaboratorios +
                ", idLaboratorioEmpresa=" + idLaboratorioEmpresa +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", laboratorio=" + laboratorio +
                '}';
    }
}
