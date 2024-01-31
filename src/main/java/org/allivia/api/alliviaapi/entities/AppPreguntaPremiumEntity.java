package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_pregunta_premium")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppPreguntaPremiumEntity extends Base {
    // Relaciones de tablas y cardinalidades
    /*@ManyToOne
    @JoinColumn(name="idSintoma")*/
    private Long idSintoma;

    // Atributos de la tabla
    @Column(name="pregunta", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String pregunta;
}
