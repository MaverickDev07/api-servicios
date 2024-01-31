package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_objetivos")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppObjetivosEntity extends Base {
    // Atributos de la tabla
    @Column(name="objetivo", columnDefinition = "CHARACTER VARYING")
    private String objetivo;

    @Column(name="estado", columnDefinition = "CHARACTER VARYING")
    private String estado;
}
