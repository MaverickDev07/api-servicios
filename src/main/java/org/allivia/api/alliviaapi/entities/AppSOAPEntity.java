package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_soap")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppSOAPEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="idFichamedica")
    private AppFichamedicaEntity idFichamedica;

    // Atributos de la tabla
    @Column(name="subjetivo", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String subjetivo;

    @Column(name="objetivo", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String objetivo;

    @Column(name="analisis", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String analisis;

    @Column(name="plan", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String plan;
}
