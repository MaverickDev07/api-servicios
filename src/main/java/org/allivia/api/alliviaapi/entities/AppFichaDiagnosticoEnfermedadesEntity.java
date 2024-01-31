package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_fichadiagnostico_enfermedades")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppFichaDiagnosticoEnfermedadesEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name="idFichamedica")
    private AppFichamedicaEntity fichamedica;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<AppCatalogoEnfermedadesEntity> enfermedades;
}
