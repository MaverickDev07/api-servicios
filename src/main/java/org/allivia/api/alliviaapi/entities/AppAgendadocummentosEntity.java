package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 04/05/2021.
 */
@Entity
@Table(name = "app_agendadocummentos", schema = "public")
public class AppAgendadocummentosEntity {
    private long id;
    private long idAgendacita;
    private String nombrearchivo;

    @Id
    @SequenceGenerator(name = "APP_AGENDADOCUMENTO_GENERATOR", sequenceName = "APP_AGENDADOCUMENTOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_AGENDADOCUMENTO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_agendacita")
    public long getIdAgendacita() {
        return idAgendacita;
    }

    public void setIdAgendacita(long idAgendacita) {
        this.idAgendacita = idAgendacita;
    }

    @Basic
    @Column(name = "nombrearchivo")
    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppAgendadocummentosEntity that = (AppAgendadocummentosEntity) o;

        if (id != that.id) return false;
        if (idAgendacita != that.idAgendacita) return false;
        if (nombrearchivo != null ? !nombrearchivo.equals(that.nombrearchivo) : that.nombrearchivo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idAgendacita ^ (idAgendacita >>> 32));
        result = 31 * result + (nombrearchivo != null ? nombrearchivo.hashCode() : 0);
        return result;
    }
}
