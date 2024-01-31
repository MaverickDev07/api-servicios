package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by yeri_ on 3/16/2021.
 */
@Entity
@Table(name = "app_sintoma", schema = "public")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppSintomaEntity {
    private long id;
    private String descripcion;
    private String estado;
    private String pregunta;

    // Relaciones de tablas y cardinalidades
    // @OneToMany(mappedBy="sintoma", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    // private List<AppPreguntaPremiumEntity> preguntas;

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
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Basic
    @Column(name = "pregunta")
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /*@Transient
    public List<AppPreguntaPremiumEntity> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<AppPreguntaPremiumEntity> preguntas) {
        this.preguntas = preguntas;
    }*/
}
