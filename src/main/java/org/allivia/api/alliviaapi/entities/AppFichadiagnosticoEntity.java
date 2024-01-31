package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_fichadiagnostico", schema = "public")
public class AppFichadiagnosticoEntity {
    private long id;
    private long idFichamedica;
    private String descripcion;

    @Id
    @SequenceGenerator(name = "APP_FICHADIAGNOSTICO_GENERATOR", sequenceName = "APP_FICHADIAGNOSTICO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHADIAGNOSTICO_GENERATOR")
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
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppFichadiagnosticoEntity that = (AppFichadiagnosticoEntity) o;

        if (id != that.id) return false;
        if (idFichamedica != that.idFichamedica) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idFichamedica ^ (idFichamedica >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppFichadiagnosticoEntity{" +
                "id=" + id +
                ", idFichamedica=" + idFichamedica +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
