package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_categoria_publicacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppCategoriaPublicacionEntity extends Base {
    @Column(name="nombre", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String nombre;

    @Column(name="descripcion", columnDefinition = "CHARACTER VARYING")
    private String descripcion;
}
