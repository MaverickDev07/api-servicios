package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_antecedente_alergia", schema = "public")
public class AppAntecedenteAlergiaEntity {
    private long id;
    private AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico;
    private AppAlergiaEntity appAlergia;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_ALERGIA_GENERATOR", sequenceName = "APP_ANTECEDENTE_ALERGIA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_ALERGIA_GENERATOR")
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
        AppAntecedenteAlergiaEntity that = (AppAntecedenteAlergiaEntity) o;
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
    @JoinColumn(name = "id_alergia", referencedColumnName = "id", nullable = false)
    public AppAlergiaEntity getAppAlergia() {
        return appAlergia;
    }

    public void setAppAlergia(AppAlergiaEntity appAlergiaByIdAlergia) {
        this.appAlergia = appAlergiaByIdAlergia;
    }
}
