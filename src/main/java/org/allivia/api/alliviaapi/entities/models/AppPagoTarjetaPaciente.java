package org.allivia.api.alliviaapi.entities.models;

import lombok.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppPagoTarjetaPaciente {
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String fechaVencimiento;
    private String cvn;
    private String nombre;
    private String token;
    private boolean guardarTarjeta;
    private Double monto;
}
