package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_antecedente_vacuna", schema = "public")
public class AppAntecedenteVacunaEntity {
    private long id;
    private AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico;
    private AppVacunaEntity appVacuna;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_VACUNA_GENERATOR", sequenceName = "APP_ANTECEDENTE_VACUNA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_VACUNA_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppAntecedenteVacunaEntity that = (AppAntecedenteVacunaEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_antecedente_medico", referencedColumnName = "id", nullable = false)
    public AppAntecedenteMedicoEntity getAppAntecedenteMedicoByIdAntecedenteMedico() {
        return appAntecedenteMedicoByIdAntecedenteMedico;
    }

    public void setAppAntecedenteMedicoByIdAntecedenteMedico(AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico) {
        this.appAntecedenteMedicoByIdAntecedenteMedico = appAntecedenteMedicoByIdAntecedenteMedico;
    }

    @ManyToOne
    @JoinColumn(name = "id_vacuna", referencedColumnName = "id", nullable = false)
    public AppVacunaEntity getAppVacuna() {
        return appVacuna;
    }

    public void setAppVacuna(AppVacunaEntity appVacunaByIdVacuna) {
        this.appVacuna = appVacunaByIdVacuna;
    }
}
