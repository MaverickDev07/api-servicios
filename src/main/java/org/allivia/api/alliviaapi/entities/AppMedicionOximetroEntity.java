package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_medicion_oximetro")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppMedicionOximetroEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    // Atributos de la tabla
    @Column(name="oxigeno", columnDefinition = "NUMERIC")
    private Double oxigeno;

    @Column(name="pulso", columnDefinition = "NUMERIC")
    private Double pulso;

    @Column(name="pi", columnDefinition = "NUMERIC")
    private Double pi;

    @Column(name="fuerzaPulso", columnDefinition = "NUMERIC")
    private Double fuerzaPulso;

    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;
}
