package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_detalle_envio")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppDetalleEnvioEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="idEntidadMedica")
    private AppEntidadMedicaEntity entidadMedica;

    @ManyToOne(optional = false)
    @JoinColumn(name="idAgendaCita")
    private AppAgendacitaEntity agendaCita;

    // Atributos de la tabla
    @Column(name="direccionEnvio", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String direccionEnvio;

    @Column(name="numeroCasa", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String numeroCasa;

    @Column(name="indicacion", columnDefinition = "CHARACTER VARYING")
    private String indicacion;

    @Column(name="telefono", columnDefinition = "CHARACTER VARYING", nullable = false)
    private String telefono;

    @Column(name="latitud", columnDefinition = "CHARACTER VARYING")
    private String latitud;

    @Column(name="longitud", columnDefinition = "CHARACTER VARYING")
    private String longitud;

    @Column(name="estado", columnDefinition = "CHARACTER VARYING")
    private String estado = "Pendiente";
}
