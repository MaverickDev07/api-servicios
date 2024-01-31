package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_notificacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppNotificacionEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuarioIdEmisor")
    private AppUsuarioEntity usuarioEmisor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuarioIdReceptor")
    private AppUsuarioEntity usuarioReceptor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idDetalleNotificacion")
    private AppDetalleNotificacionEntity detalle;

    // Atributos de la tabla
    @Column(name="titulo", columnDefinition = "CHARACTER VARYING")
    private String titulo;

    @Column(name="mensaje", columnDefinition = "CHARACTER VARYING")
    private String mensaje;

    @Column(name="estado", nullable=false, columnDefinition = "CHARACTER VARYING")
    private String estado = "NO_LEIDO";
}
