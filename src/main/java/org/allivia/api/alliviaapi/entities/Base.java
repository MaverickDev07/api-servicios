package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@ToString
@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Base implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected Timestamp creado;

    @Column(nullable = false)
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected Timestamp modificado;

    @PrePersist
    @PreUpdate
    protected void onCreate() {
        modificado = new Timestamp(new Date().getTime());
        if (creado == null) {
            creado = new Timestamp(new Date().getTime());
        }
    }
}
