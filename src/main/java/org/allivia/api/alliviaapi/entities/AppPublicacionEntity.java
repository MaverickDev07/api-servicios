package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "app_publicacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppPublicacionEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="usuarioId")
    private AppUsuarioEntity usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "imgPublicacionId")
    private AppImgPublicacionEntity imgPublicacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoriaPublicacionId")
    private AppCategoriaPublicacionEntity categoriaPublicacion;

    @Column(name="titulo", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String titulo;

    @Column(columnDefinition = "CHARACTER VARYING", nullable = false)
    private String cuerpo;

    @Column(columnDefinition = "DATE")
    private Date fecha_publicacion;

    @Column(columnDefinition = "CHARACTER VARYING")
    private String web;
}
