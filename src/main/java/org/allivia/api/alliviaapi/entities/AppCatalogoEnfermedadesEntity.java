package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_catalogo_enfermedades")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppCatalogoEnfermedadesEntity extends Base  {
    @Column(name="enfermedad", columnDefinition = "CHARACTER VARYING")
    private String enfermedad;

    @Column(name="tipo", columnDefinition = "CHARACTER VARYING")
    private String tipo;
}
