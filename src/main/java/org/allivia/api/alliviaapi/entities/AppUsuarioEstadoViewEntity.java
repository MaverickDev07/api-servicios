package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "app_view_usuario")
public class AppUsuarioEstadoViewEntity {
    // @Id
    // @Column(name = "usuario_id")
    // private long id;

    @Id
    @Column(name = "id")
    private long rowId;


    @Column(name = "usuario_id")
    private long id;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Basic
    @Column(name = "estado")
    private String estado;

    @Basic
    @Column(name = "nombrearchivo")
    private String nombrearchivo;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((nombrearchivo == null) ? 0 : nombrearchivo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            AppUsuarioEstadoViewEntity other = (AppUsuarioEstadoViewEntity) obj;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fechaNacimiento == null) {
            if (other.fechaNacimiento != null)
                return false;
        } else if (!fechaNacimiento.equals(other.fechaNacimiento))
            return false;
        if (id != other.id)
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (nombrearchivo == null) {
            if (other.nombrearchivo != null)
                return false;
        } else if (!nombrearchivo.equals(other.nombrearchivo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AppUsuarioEstadoViewEntity [estado=" + estado + ", fechaNacimiento=" + fechaNacimiento + ", id=" + id
                + ", nombre=" + nombre + ", nombrearchivo=" + nombrearchivo + "]";
    }
}