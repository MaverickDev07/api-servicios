package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_medicamentos")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppMedicamentosEntity extends BaseSimple {
    // Atributos de la tabla
    @Column(name="grupo", columnDefinition = "CHARACTER VARYING")
    private String grupo;

    @Column(name="producto", columnDefinition = "CHARACTER VARYING")
    private String producto;

    @Column(name="precio", columnDefinition = "NUMERIC")
    private Double precio;
}
