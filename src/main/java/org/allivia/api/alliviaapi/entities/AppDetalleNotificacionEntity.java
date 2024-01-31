package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_detalle_notificacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppDetalleNotificacionEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idAgendacita")
    private AppAgendacitaEntity idAgendacita;

    // Atributos de la tabla
    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;

    @Column(name="hora", columnDefinition = "CHARACTER VARYING")
    private String hora;

    @Column(name="estado", columnDefinition = "CHARACTER VARYING")
    private String estado;
}
