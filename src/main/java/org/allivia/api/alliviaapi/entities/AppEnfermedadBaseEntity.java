package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "app_enfermedad_base", schema = "public")
public class AppEnfermedadBaseEntity {
    private long id;
    private String descripcion;
    private Collection<AppAntecedenteEnfermedadBaseEntity> appAntecedenteEnfermedadBasesById;

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
        AppEnfermedadBaseEntity that = (AppEnfermedadBaseEntity) o;
        return id == that.id && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "appEnfermedadBase")
    public Collection<AppAntecedenteEnfermedadBaseEntity> getAppAntecedenteEnfermedadBasesById() {
        return appAntecedenteEnfermedadBasesById;
    }

    public void setAppAntecedenteEnfermedadBasesById(Collection<AppAntecedenteEnfermedadBaseEntity> appAntecedenteEnfermedadBasesById) {
        this.appAntecedenteEnfermedadBasesById = appAntecedenteEnfermedadBasesById;
    }
}
