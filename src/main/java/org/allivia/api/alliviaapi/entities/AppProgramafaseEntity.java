package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_programafase", schema = "public")
public class AppProgramafaseEntity {
    private long id;
    private long idPrograma;
    private String descripcion;
    private boolean eliminado;

    @Id
    @SequenceGenerator(name = "APP_PROGRAMAFASE_GENERATOR", sequenceName = "APP_PROGRAMAFASE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_PROGRAMAFASE_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_programa")
    public long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(long idPrograma) {
        this.idPrograma = idPrograma;
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
    @Column(name = "eliminado")
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppProgramafaseEntity that = (AppProgramafaseEntity) o;

        if (id != that.id) return false;
        if (idPrograma != that.idPrograma) return false;
        return descripcion != null ? descripcion.equals(that.descripcion) : that.descripcion == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idPrograma ^ (idPrograma >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppProgramafaseEntity{" +
                "id=" + id +
                ", idPrograma=" + idPrograma +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
