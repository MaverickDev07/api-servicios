package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_mail")
@ToString
@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppMailEntity extends Base {
    @Column(name="para", columnDefinition = "CHARACTER VARYING")
    private String para;

    @Column(name="asunto", columnDefinition = "CHARACTER VARYING")
    private String asunto;

    @Column(name="mensaje", columnDefinition = "CHARACTER VARYING")
    private String mensaje;

    @Column(name="archivoPath", columnDefinition = "CHARACTER VARYING")
    private String archivoPath;
}
