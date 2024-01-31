package org.allivia.api.alliviaapi.entities.models;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppSeguimientoItemPremiumId {
    private Long idSeguimiento;

    private Long idItem;
}
