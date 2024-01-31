package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "app_item_premium")
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppItemPremiumEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @Getter @Setter
    @OneToMany(mappedBy = "item", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AppSeguimientoItemPremiumEntity> SeguimientosPremium = new ArrayList<>();
    //private List<AppSeguimientoItemPremiumEntity> SeguimientosPremium = new ArrayList<AppSeguimientoItemPremiumEntity>();
    //private List<AppSeguimientoItemPremiumEntity> SeguimientosPremium  = new ArrayList<AppSeguimientoItemPremiumEntity>();
    //private Set<AppSeguimientoItemPremiumEntity> SeguimientosPremium = new HashSet<AppSeguimientoItemPremiumEntity>();

    // Atributos de la tabla
    @Getter @Setter
    @Column(name="idData")
    private Long idData;

    @Getter @Setter
    @Column(name="nombre", columnDefinition = "CHARACTER VARYING")
    private String nombre;

    @Getter @Setter
    @Column(name="titulo", columnDefinition = "CHARACTER VARYING")
    private String titulo;

    @Getter @Setter
    @Column(name="subtitulo", columnDefinition = "CHARACTER VARYING")
    private String subtitulo;

    @Getter @Setter
    @Column(name="textoBoton", columnDefinition = "CHARACTER VARYING")
    private String textoBoton;

    @Override
    public String toString() {
        return "AppItemPremiumEntity{" +
                "SeguimientosPremium=" + SeguimientosPremium +
                ", idData=" + idData +
                ", nombre='" + nombre + '\'' +
                ", titulo='" + titulo + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                ", textoBoton='" + textoBoton + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AppItemPremiumEntity that = (AppItemPremiumEntity) o;
        return Objects.equals(SeguimientosPremium, that.SeguimientosPremium) && Objects.equals(idData, that.idData) && Objects.equals(nombre, that.nombre) && Objects.equals(titulo, that.titulo) && Objects.equals(subtitulo, that.subtitulo) && Objects.equals(textoBoton, that.textoBoton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), SeguimientosPremium, idData, nombre, titulo, subtitulo, textoBoton);
    }
}
