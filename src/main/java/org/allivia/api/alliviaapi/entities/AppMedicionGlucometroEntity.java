package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_medicion_glucometro")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppMedicionGlucometroEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    // Atributos de la tabla
    @Column(name="medicionAzucar", columnDefinition = "NUMERIC")
    private Double medicionAzucar;

    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;
}
