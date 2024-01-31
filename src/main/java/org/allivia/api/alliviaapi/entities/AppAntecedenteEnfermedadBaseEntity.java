package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_antecedente_enfermedad_base", schema = "public")
public class AppAntecedenteEnfermedadBaseEntity {
    private long id;
    private AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico;
    private AppEnfermedadBaseEntity appEnfermedadBase;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_ENFERMEDAD_BASE_GENERATOR", sequenceName = "APP_ANTECEDENTE_ENFERMEDAD_BASE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_ENFERMEDAD_BASE_GENERATOR")
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
        AppAntecedenteEnfermedadBaseEntity that = (AppAntecedenteEnfermedadBaseEntity) o;
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
    @JoinColumn(name = "id_enfermedad_base", referencedColumnName = "id", nullable = false)
    public AppEnfermedadBaseEntity getAppEnfermedadBase() {
        return appEnfermedadBase;
    }

    public void setAppEnfermedadBase(AppEnfermedadBaseEntity appEnfermedadBaseByIdEnfermedadBase) {
        this.appEnfermedadBase = appEnfermedadBaseByIdEnfermedadBase;
    }
}
