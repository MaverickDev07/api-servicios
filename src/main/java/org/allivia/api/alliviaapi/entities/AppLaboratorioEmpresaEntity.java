package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_laboratorio_empresa")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppLaboratorioEmpresaEntity extends Base {
    // Relaciones de tablas y cardinalidades
    /*@ManyToMany(cascade = CascadeType.REFRESH)
    private List<AppLaboratoriosEntity> laboratorios;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<AppPacienteEntity> pacientes;*/

    // Atributos de la tabla
    @Column(name="nombre", columnDefinition = "CHARACTER VARYING")
    private String nombre;

    @Column(name="direccion", columnDefinition = "CHARACTER VARYING")
    private String direccion;

    @Column(name="horariosAtencion", columnDefinition = "CHARACTER VARYING")
    private String horariosAtencion;

    @Column(name="latitud", columnDefinition = "CHARACTER VARYING")
    private String latitud;

    @Column(name="longitud", columnDefinition = "CHARACTER VARYING")
    private String longitud;

    @Column(name="telefono", columnDefinition = "CHARACTER VARYING")
    private String telefono;

    @Column(name="celular", columnDefinition = "CHARACTER VARYING")
    private String celular;
}
