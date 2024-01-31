package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_fichaexamenes", schema = "public")
public class AppFichaexamenesEntity {
    private long id;
    private long idFichamedica;
    private String indicaciones;
    private long idExamen;

    @Id
    @SequenceGenerator(name = "APP_FICHAEXAMENES_GENERATOR", sequenceName = "APP_FICHAEXAMENES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHAEXAMENES_GENERATOR")
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
    @Column(name = "indicaciones")
    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }


    @Basic
    @Column(name = "id_examen")
    public long getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(long idExamen) {
        this.idExamen = idExamen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppFichaexamenesEntity that = (AppFichaexamenesEntity) o;

        if (id != that.id) return false;
        if (idFichamedica != that.idFichamedica) return false;
        if (idExamen != that.idExamen) return false;
        return indicaciones != null ? indicaciones.equals(that.indicaciones) : that.indicaciones == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idFichamedica ^ (idFichamedica >>> 32));
        result = 31 * result + (indicaciones != null ? indicaciones.hashCode() : 0);
        result = 31 * result + (int) (idExamen ^ (idExamen >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AppFichaexamenesEntity{" +
                "id=" + id +
                ", idFichamedica=" + idFichamedica +
                ", indicaciones='" + indicaciones + '\'' +
                ", idExamen=" + idExamen +
                '}';
    }
}
