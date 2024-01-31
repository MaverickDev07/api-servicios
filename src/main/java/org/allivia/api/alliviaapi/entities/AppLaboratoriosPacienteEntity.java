package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_laboratorios_paciente")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppLaboratoriosPacienteEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="idLaboratorioEmpresa")
    private AppLaboratorioEmpresaEntity laboratorioEmpresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<AppLaboratoriosEntity> laboratorios;

    // Atributos de la tabla
    @Column(name="estado", columnDefinition = "CHARACTER VARYING")
    private String estado = "Pendiente";
}
