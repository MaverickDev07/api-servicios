package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_objetivos_seleccionados")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppObjetivosSeleccionadosEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="usuarioId")
    private AppUsuarioEntity usuario;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<AppObjetivosEntity> objetivos;

    // Atributos de la tabla
    @Column(name="estado", columnDefinition = "CHARACTER VARYING")
    private String estado;   
}
