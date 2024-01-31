package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_antecedente_cirugia", schema = "public")
public class AppAntecedenteCirugiaEntity {
    private long id;
    /// private long idAntecedenteMedico;
    // private long idCirugia;
    private AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico;
    private AppCirugiaEntity appCirugia;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_CIRUGIA_GENERATOR", sequenceName = "APP_ANTECEDENTE_CIRUGIA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_CIRUGIA_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

   /* @Basic
    @Column(name = "id_antecedente_medico")
    public long getIdAntecedenteMedico() {
        return idAntecedenteMedico;
    }

    public void setIdAntecedenteMedico(long idAntecedenteMedico) {
        this.idAntecedenteMedico = idAntecedenteMedico;
    }*/

    public void setId(long id) {
        this.id = id;
    }

   /* @Basic
    @Column(name = "id_cirugia")
    public long getIdCirugia() {
        return idCirugia;
    }

    public void setIdCirugia(long idCirugia) {
        this.idCirugia = idCirugia;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppAntecedenteCirugiaEntity that = (AppAntecedenteCirugiaEntity) o;
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
    @JoinColumn(name = "id_cirugia", referencedColumnName = "id", nullable = false)
    public AppCirugiaEntity getAppCirugia() {
        return appCirugia;
    }

    public void setAppCirugia(AppCirugiaEntity appCirugiaByIdCirugia) {
        this.appCirugia = appCirugiaByIdCirugia;
    }
}
