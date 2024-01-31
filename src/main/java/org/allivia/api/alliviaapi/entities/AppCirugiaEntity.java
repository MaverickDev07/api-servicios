package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "app_cirugia", schema = "public")
public class AppCirugiaEntity {
    private long id;
    private String descripcion;
    private Collection<AppAntecedenteCirugiaEntity> appAntecedenteCirugiasById;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppCirugiaEntity that = (AppCirugiaEntity) o;
        return id == that.id && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "appCirugia")
    public Collection<AppAntecedenteCirugiaEntity> getAppAntecedenteCirugiasById() {
        return appAntecedenteCirugiasById;
    }

    public void setAppAntecedenteCirugiasById(Collection<AppAntecedenteCirugiaEntity> appAntecedenteCirugiasById) {
        this.appAntecedenteCirugiasById = appAntecedenteCirugiasById;
    }
}
