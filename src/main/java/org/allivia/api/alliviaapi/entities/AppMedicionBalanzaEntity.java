package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_medicion_balanza")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppMedicionBalanzaEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    // Atributos de la tabla
    @Column(name="peso", columnDefinition = "NUMERIC")
    private Double peso;

    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;

    @Column(name="grasaCorporal", columnDefinition = "NUMERIC")
    private Double grasaCorporal;

    @Column(name="masaMagra", columnDefinition = "NUMERIC")
    private Double masaMagra;

    @Column(name="masaMuscular", columnDefinition = "NUMERIC")
    private Double masaMuscular;

    @Column(name="masaOsea", columnDefinition = "NUMERIC")
    private Double masaOsea;

    @Column(name="aguaCorporal", columnDefinition = "NUMERIC")
    private Double aguaCorporal;

    @Column(name="proteinas", columnDefinition = "NUMERIC")
    private Double proteinas;

    @Column(name="masaMusculoEsqueletal", columnDefinition = "NUMERIC")
    private Double masaMusculoEsqueletal;

    @Column(name="grasaVisceral", columnDefinition = "NUMERIC")
    private Double grasaVisceral;

    @Column(name="edadFisica", columnDefinition = "NUMERIC")
    private Double edadFisica;
}
