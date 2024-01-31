package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_programa", schema = "public")
public class AppProgramaEntity {
    private long id;
    private String descripcion;
    private boolean eliminado;

    private AppProgramafaseEntity fase;

    @Transient
    public AppProgramafaseEntity getFase() {
        return fase;
    }

    public void setFase(AppProgramafaseEntity fase) {
        this.fase = fase;
    }

    @Id
    @SequenceGenerator(name = "APP_PROGRAMA_GENERATOR", sequenceName = "APP_PROGRAMA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_PROGRAMA_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        AppProgramaEntity that = (AppProgramaEntity) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppProgramaEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fase=" + fase +
                '}';
    }
}
