package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_doctorafiliaciones", schema = "public")
public class AppDoctorafiliacionesEntity {
    private long id;
    private long idDoctor;
    private long idAfiliaciones;
    private AppAfiliacionesEntity afiliaciones;

    @Transient
    public AppAfiliacionesEntity getAfiliaciones() {
        return afiliaciones;
    }

    public void setAfiliaciones(AppAfiliacionesEntity afiliaciones) {
        this.afiliaciones = afiliaciones;
    }

    @Id
    @SequenceGenerator(name = "APP_AFILIACIONES_DOCTORID_GENERATOR", sequenceName = "APP_DOCTORAFILIACIONES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_AFILIACIONES_DOCTORID_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_doctor")
    public long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(long idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Basic
    @Column(name = "id_afiliaciones")
    public long getIdAfiliaciones() {
        return idAfiliaciones;
    }

    public void setIdAfiliaciones(long idAfiliaciones) {
        this.idAfiliaciones = idAfiliaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppDoctorafiliacionesEntity that = (AppDoctorafiliacionesEntity) o;

        if (id != that.id) return false;
        if (idDoctor != that.idDoctor) return false;
        if (idAfiliaciones != that.idAfiliaciones) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idDoctor ^ (idDoctor >>> 32));
        result = 31 * result + (int) (idAfiliaciones ^ (idAfiliaciones >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AppDoctorafiliacionesEntity{" +
                "id=" + id +
                ", idDoctor=" + idDoctor +
                ", idAfiliaciones=" + idAfiliaciones +
                ", afiliaciones=" + afiliaciones +
                '}';
    }
}
