package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_facturacion")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppFacturaEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    // Atributos de la tabla
    @Column(name="factura", columnDefinition = "CHARACTER VARYING")
    private String factura;
    @Column(name="facturaRollo", columnDefinition = "CHARACTER VARYING")
    private String facturaRollo;
    @Column(name="autorizacion", columnDefinition = "CHARACTER VARYING")
    private String autorizacion;
    @Column(name="detalle", columnDefinition = "CHARACTER VARYING")
    private String detalle;
}
