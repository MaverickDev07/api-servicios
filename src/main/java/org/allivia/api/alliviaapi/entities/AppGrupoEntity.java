package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yeri_ on 3/11/2021.
 */
@Entity
@Table(name = "app_grupo")
public class AppGrupoEntity {
    private String nombreGrupo;
    private String descripcion;
    private Timestamp creado;
    private String creadoPor;
    private Timestamp modificado;
    private String modificadoPor;
    private long grupoId;
    private Timestamp fechaFin;
    private Long rolId;
    private Boolean grupoLdap;

    @Basic
    @Column(name = "nombre_grupo")
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
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
    @Column(name = "creado")
    public Timestamp getCreado() {
        return creado;
    }

    public void setCreado(Timestamp creado) {
        this.creado = creado;
    }

    @Basic
    @Column(name = "creado_por")
    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
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

    @Id
    @Column(name = "grupo_id")
    public long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(long grupoId) {
        this.grupoId = grupoId;
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
    @Column(name = "rol_id")
    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    @Basic
    @Column(name = "grupo_ldap")
    public Boolean getGrupoLdap() {
        return grupoLdap;
    }

    public void setGrupoLdap(Boolean grupoLdap) {
        this.grupoLdap = grupoLdap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppGrupoEntity that = (AppGrupoEntity) o;

        if (grupoId != that.grupoId) return false;
        if (nombreGrupo != null ? !nombreGrupo.equals(that.nombreGrupo) : that.nombreGrupo != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (creado != null ? !creado.equals(that.creado) : that.creado != null) return false;
        if (creadoPor != null ? !creadoPor.equals(that.creadoPor) : that.creadoPor != null) return false;
        if (modificado != null ? !modificado.equals(that.modificado) : that.modificado != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(that.modificadoPor) : that.modificadoPor != null)
            return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (rolId != null ? !rolId.equals(that.rolId) : that.rolId != null) return false;
        if (grupoLdap != null ? !grupoLdap.equals(that.grupoLdap) : that.grupoLdap != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombreGrupo != null ? nombreGrupo.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (creado != null ? creado.hashCode() : 0);
        result = 31 * result + (creadoPor != null ? creadoPor.hashCode() : 0);
        result = 31 * result + (modificado != null ? modificado.hashCode() : 0);
        result = 31 * result + (modificadoPor != null ? modificadoPor.hashCode() : 0);
        result = 31 * result + (int) (grupoId ^ (grupoId >>> 32));
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (rolId != null ? rolId.hashCode() : 0);
        result = 31 * result + (grupoLdap != null ? grupoLdap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppGrupoEntity{" +
                "nombreGrupo='" + nombreGrupo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", creado=" + creado +
                ", creadoPor='" + creadoPor + '\'' +
                ", modificado=" + modificado +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", grupoId=" + grupoId +
                ", fechaFin=" + fechaFin +
                ", rolId=" + rolId +
                ", grupoLdap=" + grupoLdap +
                '}';
    }
}
