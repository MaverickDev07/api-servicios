package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/16/2021.
 */
@Entity
@Table(name = "app_respuesta", schema = "public")
public class AppRespuestaEntity {
    private long id;
    private String descripcion;
    private Long preguntaId;
    private Integer preguntaIdSiguiente;

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
    @Column(name = "pregunta_id")
    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    @Basic
    @Column(name = "pregunta_id_siguiente")
    public Integer getPreguntaIdSiguiente() {
        return preguntaIdSiguiente;
    }

    public void setPreguntaIdSiguiente(Integer preguntaIdSiguiente) {
        this.preguntaIdSiguiente = preguntaIdSiguiente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppRespuestaEntity that = (AppRespuestaEntity) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (preguntaId != null ? !preguntaId.equals(that.preguntaId) : that.preguntaId != null) return false;
        if (preguntaIdSiguiente != null ? !preguntaIdSiguiente.equals(that.preguntaIdSiguiente) : that.preguntaIdSiguiente != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (preguntaId != null ? preguntaId.hashCode() : 0);
        result = 31 * result + (preguntaIdSiguiente != null ? preguntaIdSiguiente.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppRespuestaEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", preguntaId=" + preguntaId +
                ", preguntaIdSiguiente=" + preguntaIdSiguiente +
                '}';
    }
}
