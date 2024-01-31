package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 08/07/2021.
 */
@Entity
@Table(name = "programas", schema = "public")
public class ProgramasEntity {
    private Long id;
    private Long idcita;
    private String programa;
    private String fase;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "idcita")
    public Long getIdcita() {
        return idcita;
    }

    public void setIdcita(Long idcita) {
        this.idcita = idcita;
    }

    @Basic
    @Column(name = "programa")
    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    @Basic
    @Column(name = "fase")
    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramasEntity that = (ProgramasEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idcita != null ? !idcita.equals(that.idcita) : that.idcita != null) return false;
        if (programa != null ? !programa.equals(that.programa) : that.programa != null) return false;
        if (fase != null ? !fase.equals(that.fase) : that.fase != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idcita != null ? idcita.hashCode() : 0);
        result = 31 * result + (programa != null ? programa.hashCode() : 0);
        result = 31 * result + (fase != null ? fase.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProgramasEntity{" +
                "id=" + id +
                ", idcita=" + idcita +
                ", programa='" + programa + '\'' +
                ", fase='" + fase + '\'' +
                '}';
    }
}
