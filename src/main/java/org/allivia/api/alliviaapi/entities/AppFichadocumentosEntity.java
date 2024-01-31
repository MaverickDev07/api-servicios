package org.allivia.api.alliviaapi.entities;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;


/**
 * Created by yeri_ on 3/23/2021.
 */
@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "app_fichadocumentos", schema = "public")
public class AppFichadocumentosEntity{


    private long id;
    private long idFichamedica;
    private String path;
    private String nombrearchivo;
    private String descripcion;
    private String formato;


    @Id
    @SequenceGenerator(name = "APP_FICHADOCUMENTOS_GENERATOR", sequenceName = "APP_FICHADOCUMENTOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_FICHADOCUMENTOS_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_fichamedica")
    public long getIdFichamedica() {
        return idFichamedica;
    }

    public void setIdFichamedica(long idFichamedica) {
        this.idFichamedica = idFichamedica;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "nombrearchivo")
    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
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
    @Column(name = "formato")
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppFichadocumentosEntity that = (AppFichadocumentosEntity) o;

        if (id != that.id) return false;
        if (idFichamedica != that.idFichamedica) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (nombrearchivo != null ? !nombrearchivo.equals(that.nombrearchivo) : that.nombrearchivo != null)
            return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        return formato != null ? formato.equals(that.formato) : that.formato == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idFichamedica ^ (idFichamedica >>> 32));
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (nombrearchivo != null ? nombrearchivo.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (formato != null ? formato.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppFichadocumentosEntity{" +
                "id=" + id +
                ", idFichamedica=" + idFichamedica +
                ", path='" + path + '\'' +
                ", nombrearchivo='" + nombrearchivo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", formato='" + formato + '\'' +
                '}';
    }
}
