package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 3/17/2021.
 */
@Entity
@Table(name = "app_tipoconsulta", schema = "public")
public class AppTipoconsultaEntity {
    private long id;
    private String descripcion;
    private String tipo;
    private boolean eliminado;
    private String codigo;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "eliminado")
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Basic
    @Column(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppTipoconsultaEntity that = (AppTipoconsultaEntity) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        return tipo != null ? tipo.equals(that.tipo) : that.tipo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppTipoconsultaEntity{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
