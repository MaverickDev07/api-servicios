package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_doctoresespecialidades", schema = "public")
public class AppDoctoresespecialidadesEntity {
    private long id;
    private long idDoctor;
    private long idEspecialidad;
    private AppEspecialidadEntity especialidades;

    @Transient
    public AppEspecialidadEntity getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(AppEspecialidadEntity especialidades) {
        this.especialidades = especialidades;
    }

    @Id
    @SequenceGenerator(name = "APP_ESPECIALIDAD_DOCTORID_GENERATOR", sequenceName = "APP_DOCTORESESPECIALIDADES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ESPECIALIDAD_DOCTORID_GENERATOR")
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
    @Column(name = "id_especialidad")
    public long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppDoctoresespecialidadesEntity that = (AppDoctoresespecialidadesEntity) o;

        if (id != that.id) return false;
        if (idDoctor != that.idDoctor) return false;
        if (idEspecialidad != that.idEspecialidad) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idDoctor ^ (idDoctor >>> 32));
        result = 31 * result + (int) (idEspecialidad ^ (idEspecialidad >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AppDoctoresespecialidadesEntity{" +
                "id=" + id +
                ", idDoctor=" + idDoctor +
                ", idEspecialidad=" + idEspecialidad +
                ", especialidades=" + especialidades +
                '}';
    }
}
