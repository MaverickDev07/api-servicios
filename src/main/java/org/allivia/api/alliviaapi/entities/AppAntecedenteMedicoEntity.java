package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "app_antecedente_medico", schema = "public")
public class AppAntecedenteMedicoEntity {
    private long id;
    private String genero;
    private String fechaNacimiento;
    private long idPaciente;
    private int fuma;
    private int alcohol;
    private int cafeina;
    private int ejercicio;
    private int drogas;
    private boolean testifico;
    private Collection<AppAntecedenteCirugiaEntity> appAntecedenteCirugias;
    private Collection<AppAntecedenteAlergiaEntity> appAntecedenteAlergias;
    private Collection<AppAntecedenteEnfermedadBaseEntity> appAntecedenteEnfermedadBase;
    private Collection<AppAntecedenteVacunaEntity> appAntecedenteVacuna;
    private Collection<AppAntecedenteFamiliaEntity> appAntecedenteFamilias;

    @Id
    @SequenceGenerator(name = "APP_ANTECEDENTE_MEDICO_GENERATOR", sequenceName = "APP_ANTECEDENTE_MEDICO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ANTECEDENTE_MEDICO_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Basic
    @Column(name = "fecha_nacimiento")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Basic
    @Column(name = "id_paciente")
    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Basic
    @Column(name = "fuma")
    public int getFuma() {
        return fuma;
    }

    public void setFuma(int fuma) {
        this.fuma = fuma;
    }

    @Basic
    @Column(name = "alcohol")
    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    @Basic
    @Column(name = "cafeina")
    public int getCafeina() {
        return cafeina;
    }

    public void setCafeina(int cafeina) {
        this.cafeina = cafeina;
    }

    @Basic
    @Column(name = "ejercicio")
    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    @Basic
    @Column(name = "drogas")
    public int getDrogas() {
        return drogas;
    }

    public void setDrogas(int drogas) {
        this.drogas = drogas;
    }

    @Basic
    @Column(name = "testifico")
    public boolean isTestifico() {
        return testifico;
    }

    public void setTestifico(boolean testifico) {
        this.testifico = testifico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppAntecedenteMedicoEntity that = (AppAntecedenteMedicoEntity) o;
        return id == that.id && idPaciente == that.idPaciente && Objects.equals(genero, that.genero) && Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genero, fechaNacimiento, idPaciente);
    }

    @OneToMany(mappedBy = "appAntecedenteMedicoByIdAntecedenteMedico")
    public Collection<AppAntecedenteCirugiaEntity> getAppAntecedenteCirugias() {
        return appAntecedenteCirugias;
    }

    public void setAppAntecedenteCirugias(Collection<AppAntecedenteCirugiaEntity> appAntecedenteCirugias) {
        this.appAntecedenteCirugias = appAntecedenteCirugias;
    }

    @OneToMany(mappedBy = "appAntecedenteMedicoByIdAntecedenteMedico")
    public Collection<AppAntecedenteAlergiaEntity> getAppAntecedenteAlergias() {
        return appAntecedenteAlergias;
    }

    public void setAppAntecedenteAlergias(Collection<AppAntecedenteAlergiaEntity> appAntecedenteAlergiasById) {
        this.appAntecedenteAlergias = appAntecedenteAlergiasById;
    }

    @OneToMany(mappedBy = "appAntecedenteMedicoByIdAntecedenteMedico")
    public Collection<AppAntecedenteEnfermedadBaseEntity> getAppAntecedenteEnfermedadBase() {
        return appAntecedenteEnfermedadBase;
    }

    public void setAppAntecedenteEnfermedadBase(Collection<AppAntecedenteEnfermedadBaseEntity> appAntecedenteEnfermedadBasesById) {
        this.appAntecedenteEnfermedadBase = appAntecedenteEnfermedadBasesById;
    }

    @OneToMany(mappedBy = "appAntecedenteMedicoByIdAntecedenteMedico")
    public Collection<AppAntecedenteVacunaEntity> getAppAntecedenteVacuna() {
        return appAntecedenteVacuna;
    }

    public void setAppAntecedenteVacuna(Collection<AppAntecedenteVacunaEntity> appAntecedenteVacunasById) {
        this.appAntecedenteVacuna = appAntecedenteVacunasById;
    }

    @OneToMany(mappedBy = "appAntecedenteMedicoByIdAntecedenteMedico")
    public Collection<AppAntecedenteFamiliaEntity> getAppAntecedenteFamilias() {
        return appAntecedenteFamilias;
    }

    public void setAppAntecedenteFamilias(Collection<AppAntecedenteFamiliaEntity> appAntecedenteFamilias) {
        this.appAntecedenteFamilias = appAntecedenteFamilias;
    }
}
