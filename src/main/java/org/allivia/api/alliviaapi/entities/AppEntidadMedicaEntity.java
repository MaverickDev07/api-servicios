package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_entidad_medica")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppEntidadMedicaEntity extends Base {
    // Atributos de la tabla
    @Column(name="nombre", columnDefinition = "CHARACTER VARYING")
    private String nombre;

    @Column(name="email", columnDefinition = "CHARACTER VARYING")
    private String email;

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

    @Column(name="web", columnDefinition = "CHARACTER VARYING")
    private String web;

    @Column(name="tipoEntidad", columnDefinition = "CHARACTER VARYING")
    private String tipoEntidad;

    @Column(name="img", columnDefinition = "CHARACTER VARYING")
    private String img;

    @Column(name="eliminado", columnDefinition = "BOOLEAN")
    private Boolean eliminado;
}
