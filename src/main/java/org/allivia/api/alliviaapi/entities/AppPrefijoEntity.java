package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

/**
 * Created by yeri_ on 01/04/2021.
 */
@Entity
@Table(name = "app_prefijo", schema = "public")
public class AppPrefijoEntity {
    private long id;
    private String valor;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "valor")
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppPrefijoEntity that = (AppPrefijoEntity) o;

        if (id != that.id) return false;
        if (valor != null ? !valor.equals(that.valor) : that.valor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppPrefijoEntity{" +
                "id=" + id +
                ", valor='" + valor + '\'' +
                '}';
    }
}
