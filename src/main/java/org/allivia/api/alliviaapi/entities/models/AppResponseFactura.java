package org.allivia.api.alliviaapi.entities.models;

import lombok.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppResponseFactura {
    private String message;
    private AppResponseFacturaBody data;
}
