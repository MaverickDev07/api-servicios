package org.allivia.api.alliviaapi.entities.models;

import lombok.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppResponseFacturaBody {
    private String _id;
    private AppResponseFacturaData factura;
}
