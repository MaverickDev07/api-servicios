package org.allivia.api.alliviaapi.entities.models;

import lombok.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppResponseFacturaData {
    private String link;
    private String pdfRollo;
    private String autorizacion;
}
