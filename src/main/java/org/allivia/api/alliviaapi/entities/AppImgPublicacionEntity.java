package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_img_publicacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppImgPublicacionEntity extends Base {
    // Atributos de la tabla
    @Column(name="nombre", columnDefinition = "CHARACTER VARYING")
    private String nombre;

    @Column(name="urlImg", columnDefinition = "CHARACTER VARYING")
    private String urlImg;
}
