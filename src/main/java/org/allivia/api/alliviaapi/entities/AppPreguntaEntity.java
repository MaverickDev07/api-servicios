package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by yeri_ on 3/16/2021.
 */
@Entity
@Table(name = "app_pregunta", schema = "public")
public class AppPreguntaEntity {
    private long id;
    private String descripcion;
    private Long sintomaId;
    private Integer orden;
    private String estado;

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

    @Basic
    @Column(name = "sintoma_id")
    public Long getSintomaId() {
        return sintomaId;
    }

    public void setSintomaId(Long sintomaId) {
        this.sintomaId = sintomaId;
    }

    @Basic
    @Column(name = "orden")
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Basic
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppPreguntaEntity that = (AppPreguntaEntity) o;
        return id == that.id && Objects.equals(descripcion, that.descripcion) && Objects.equals(sintomaId, that.sintomaId) && Objects.equals(orden, that.orden) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, sintomaId, orden, estado);
    }

    @Override
    public String toString() {
        return "AppPreguntaEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", sintomaId=" + sintomaId +
                ", orden=" + orden +
                ", estado='" + estado + '\'' +
                '}';
    }
}
