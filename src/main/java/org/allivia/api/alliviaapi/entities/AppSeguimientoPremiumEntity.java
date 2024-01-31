package org.allivia.api.alliviaapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "app_seguimiento_premium")
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AppSeguimientoPremiumEntity extends Base {
    // Relaciones de tablas y cardinalidades
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPaciente")
    private AppPacienteEntity paciente;

    @OneToMany(mappedBy="seguimiento", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AppSeguimientoItemPremiumEntity> items = new ArrayList<>();
    //private Set<AppSeguimientoItemPremiumEntity> items = new HashSet<AppSeguimientoItemPremiumEntity>();

    // Atributos de la tabla
    @Column(name="fecha", columnDefinition = "CHARACTER VARYING")
    private String fecha;

    // Add items
    /*public void addItemsPremium(AppItemPremiumEntity item, String estado) {
        AppSeguimientoItemPremiumEntity seguimientoItem = new AppSeguimientoItemPremiumEntity();
        seguimientoItem.setSeguimiento(this);
        seguimientoItem.setItem(item);
        seguimientoItem.setEstado(estado);
        if(this.items == null)
            this.items = new ArrayList<>();

        this.items.add(seguimientoItem);
        // Also add the association object to the employee.
        item.getSeguimientosPremium().add(seguimientoItem);
    }*/

    @Override
    public String toString() {
        return "AppSeguimientoPremiumEntity{" +
                "paciente=" + paciente +
                ", items=" + items +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AppSeguimientoPremiumEntity that = (AppSeguimientoPremiumEntity) o;
        return Objects.equals(paciente, that.paciente) && Objects.equals(items, that.items) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paciente, items, fecha);
    }
}
