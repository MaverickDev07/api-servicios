package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.allivia.api.alliviaapi.entities.models.AppSeguimientoItemPremiumId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_seguimientoitem_premium")
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter @Setter
//@IdClass(AppSeguimientoItemPremiumId.class)
public class AppSeguimientoItemPremiumEntity extends Base {
    // Atributos de la tabla
    @Column(name = "estado", columnDefinition = "CHARACTER VARYING")
    private String estado;

    private transient AppItemPremiumEntity itemPremium;

    // Relaciones de tablas y cardinalidades
    @JsonIdentityInfo(scope = AppSeguimientoPremiumEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSeguimiento")
    private AppSeguimientoPremiumEntity seguimiento;

    @JsonIdentityInfo(scope = AppItemPremiumEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idItem")
    private AppItemPremiumEntity item;

    @Override
    public String toString() {
        return "AppSeguimientoItemPremiumEntity{" +
                "estado='" + estado + '\'' +
                ", itemPremium=" + itemPremium +
                ", seguimiento=" + seguimiento +
                ", item=" + item +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AppSeguimientoItemPremiumEntity that = (AppSeguimientoItemPremiumEntity) o;
        return Objects.equals(estado, that.estado) && Objects.equals(itemPremium, that.itemPremium) && Objects.equals(seguimiento, that.seguimiento) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estado, itemPremium, seguimiento, item);
    }

    public AppSeguimientoItemPremiumEntity() {
    }

    public AppSeguimientoItemPremiumEntity(String estado, AppItemPremiumEntity itemPremium, AppSeguimientoPremiumEntity seguimiento, AppItemPremiumEntity item) {
        this.estado = estado;
        this.itemPremium = itemPremium;
        this.seguimiento = seguimiento;
        this.item = item;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Transient
    public AppItemPremiumEntity getItemPremium() {
        return itemPremium;
    }

    public void setItemPremium(AppItemPremiumEntity itemPremium) {
        this.itemPremium = itemPremium;
    }

    public AppSeguimientoPremiumEntity getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(AppSeguimientoPremiumEntity seguimiento) {
        this.seguimiento = seguimiento;
    }

    public AppItemPremiumEntity getItem() {
        return item;
    }

    public void setItem(AppItemPremiumEntity item) {
        this.item = item;
    }
}
