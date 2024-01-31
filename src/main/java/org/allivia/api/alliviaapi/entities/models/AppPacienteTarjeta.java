package org.allivia.api.alliviaapi.entities.models;

import lombok.*;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;

import javax.persistence.Transient;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppPacienteTarjeta {
    private Long idPaciente;
    private String apellido;
    private String email;
    private String celular;
    @Transient
    private AppPacienteEntity paciente;
    @Transient
    private AppUsuarioEntity usuario;
    @Transient
    private AppSuscripcionEntity suscripcion;
    @Transient
    private AppPagoTarjetaPaciente pagoTarjetaPaciente;
}
