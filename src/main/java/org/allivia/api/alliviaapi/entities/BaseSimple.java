package org.allivia.api.alliviaapi.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@ToString
@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseSimple implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INTEGER", updatable = false, nullable = false)
    private Integer id;
}
