package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 12/04/2021.
 */
@Entity
@Table(name = "app_diagnostico", schema = "public")
public class AppDiagnosticoEntity {
    private long id;
    private String codigo;
    private String diagnostico;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "diagnostico")
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppDiagnosticoEntity that = (AppDiagnosticoEntity) o;

        if (id != that.id) return false;
        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (diagnostico != null ? !diagnostico.equals(that.diagnostico) : that.diagnostico != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (diagnostico != null ? diagnostico.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppDiagnosticoEntity{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
