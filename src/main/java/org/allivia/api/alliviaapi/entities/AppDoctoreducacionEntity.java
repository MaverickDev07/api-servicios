package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_doctoreducacion", schema = "public")
public class AppDoctoreducacionEntity {
    private long id;
    private long idDoctor;
    private String annio;
    private String educacion;
    private Long idtitulo;
    private AppTituloEntity titulo;

    @Transient
    public AppTituloEntity getTitulo() {
        return titulo;
    }

    public void setTitulo(AppTituloEntity titulo) {
        this.titulo = titulo;
    }

    @Id
    @SequenceGenerator(name = "APP_EDUCACION_DOCTORID_GENERATOR", sequenceName = "APP_DOCTOREDUCACION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_EDUCACION_DOCTORID_GENERATOR")
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
    @Column(name = "annio")
    public String getAnnio() {
        return annio;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

    @Basic
    @Column(name = "educacion")
    public String getEducacion() {
        return educacion;
    }

    public void setEducacion(String educacion) {
        this.educacion = educacion;
    }

    @Basic
    @Column(name = "idtitulo")
    public Long getIdtitulo() {
        return idtitulo;
    }

    public void setIdtitulo(Long idtitulo) {
        this.idtitulo = idtitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppDoctoreducacionEntity that = (AppDoctoreducacionEntity) o;

        if (id != that.id) return false;
        if (idDoctor != that.idDoctor) return false;
        if (annio != null ? !annio.equals(that.annio) : that.annio != null) return false;
        if (educacion != null ? !educacion.equals(that.educacion) : that.educacion != null) return false;
        return idtitulo != null ? idtitulo.equals(that.idtitulo) : that.idtitulo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idDoctor ^ (idDoctor >>> 32));
        result = 31 * result + (annio != null ? annio.hashCode() : 0);
        result = 31 * result + (educacion != null ? educacion.hashCode() : 0);
        result = 31 * result + (idtitulo != null ? idtitulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppDoctoreducacionEntity{" +
                "id=" + id +
                ", idDoctor=" + idDoctor +
                ", annio='" + annio + '\'' +
                ", educacion='" + educacion + '\'' +
                ", idtitulo=" + idtitulo +
                ", titulo=" + titulo +
                '}';
    }
}
