package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by yeri_ on 08/06/2021.
 */
@Entity
@Table(name = "app_antecedente_familia", schema = "public")
public class AppAntecedenteFamiliaEntity {
    private long id;
    private String nombre;
    private Integer diabetes;
    private String cancer;
    private Boolean enfemedadCorazon;
    private Boolean hipertension;
    private AppAntecedenteMedicoEntity appAntecedenteMedicoByIdAntecedenteMedico;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_FAMILIAR_GENERATOR", sequenceName = "APP_ANTECEDENTESFAMILIAR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_FAMILIAR_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "diabetes")
    public Integer getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Integer diabetes) {
        this.diabetes = diabetes;
    }

    @Basic
    @Column(name = "cancer")
    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    @Basic
    @Column(name = "enfemedadCorazon")
    public Boolean getEnfemedadCorazon() {
        return enfemedadCorazon;
    }

    public void setEnfemedadCorazon(Boolean enfemedadCorazon) {
        this.enfemedadCorazon = enfemedadCorazon;
    }

    @Basic
    @Column(name = "hipertension")
    public Boolean getHipertension() {
        return hipertension;
    }

    public void setHipertension(Boolean hipertension) {
        this.hipertension = hipertension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppAntecedenteFamiliaEntity that = (AppAntecedenteFamiliaEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (diabetes != null ? !diabetes.equals(that.diabetes) : that.diabetes != null) return false;
        if (cancer != null ? !cancer.equals(that.cancer) : that.cancer != null) return false;
        if (enfemedadCorazon != null ? !enfemedadCorazon.equals(that.enfemedadCorazon) : that.enfemedadCorazon != null)
            return false;
        if (hipertension != null ? !hipertension.equals(that.hipertension) : that.hipertension != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (diabetes != null ? diabetes.hashCode() : 0);
        result = 31 * result + (cancer != null ? cancer.hashCode() : 0);
        result = 31 * result + (enfemedadCorazon != null ? enfemedadCorazon.hashCode() : 0);
        result = 31 * result + (hipertension != null ? hipertension.hashCode() : 0);
        return result;
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
}
