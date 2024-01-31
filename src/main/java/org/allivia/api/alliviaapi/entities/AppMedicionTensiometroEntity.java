package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_medicion_tensiometro")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppMedicionTensiometroEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    // Atributos de la tabla
    @Column(name="systole", columnDefinition = "NUMERIC")
    private Integer systole;

    @Column(name="diastole", columnDefinition = "NUMERIC")
    private Integer diastole;

    @Column(name="arritmia", columnDefinition = "BOOLEAN")
    private Boolean arritmia;

    @Column(name="fuerzaPulso", columnDefinition = "NUMERIC")
    private Integer pulso;

    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;
}
