package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/23/2021.
 */
@Entity
@Table(name = "app_fichamedica", schema = "public")
public class AppFichamedicaEntity {
    private long id;
    private long idAgendacita;
    private Long idProgramafase;

    @Id
    @SequenceGenerator(name = "APP_FICHAMEDICA_GENERATOR", sequenceName = "APP_FICHAMEDICA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHAMEDICA_GENERATOR")
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
    @Column(name = "id_programafase")
    public Long getIdProgramafase() {
        return idProgramafase;
    }

    public void setIdProgramafase(Long idProgramafase) {
        this.idProgramafase = idProgramafase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppFichamedicaEntity that = (AppFichamedicaEntity) o;

        if (id != that.id) return false;
        if (idAgendacita != that.idAgendacita) return false;
        if (idProgramafase != that.idProgramafase) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idAgendacita ^ (idAgendacita >>> 32));
        result = 31 * result + (int) (idProgramafase ^ (idProgramafase >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AppFichamedicaEntity{" +
                "id=" + id +
                ", idAgendacita=" + idAgendacita +
                ", idProgramafase=" + idProgramafase +
                '}';
    }
}
