package org.allivia.api.alliviaapi.entities;

import Invokers.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yeri_ on 3/11/2021.
 */
@Entity
@Table(name = "app_usuario")
public class AppUsuarioEntity {
    private String descricpion;
    private String email;
    private String nombre;
    private String apellido;
    private String carnet;
    private String fechaNacimiento;
    private String path;
    private String usuario;
    private String pinPassword;
    private String estado;
    private String token;
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp creado;
    private String creadoPor;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp modificado;
    private String modificadoPor;
    private Boolean reestablecerPassword;
    private long usuarioId;
    private Long grupoId;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp fechaFin;
    private Integer tipoAutenticacionId;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp passwordValidoHasta;
    private String genero;
    private String direccion;
    private String telefono;
    private String nombrearchivo;
    private String idDevice;
    @Basic
    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] devices;
    private boolean revision;

    public String[] getDevices() {
        return devices;
    }

    public void setDevices(String[] devices) {
        this.devices = devices;
    }

    @Basic
    @Column(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Basic
    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "descricpion")
    public String getDescricpion() {
        return descricpion;
    }

    public void setDescricpion(String descricpion) {
        this.descricpion = descricpion;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellido")
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Basic
    @Column(name = "carnet")
    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    @Basic
    @Column(name = "fecha_nacimiento")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    @Column(name = "usuario")
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "pin_password")
    public String getPinPassword() {
        return pinPassword;
    }

    public void setPinPassword(String pinPassword) {
        this.pinPassword = pinPassword;
    }

    @Basic
    @Column(name = "estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "creado")
    public Timestamp getCreado() {
        return creado;
    }

    public void setCreado(Timestamp creado) {
        this.creado = creado;
    }

    @Basic
    @Column(name = "creado_por")
    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    @Basic
    @Column(name = "modificado")
    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    @Basic
    @Column(name = "modificado_por")
    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    @Basic
    @Column(name = "reestablecer_password")
    public Boolean getReestablecerPassword() {
        return reestablecerPassword;
    }

    public void setReestablecerPassword(Boolean reestablecerPassword) {
        this.reestablecerPassword = reestablecerPassword;
    }
    @Basic
    @Column(name = "revision")
    public boolean isRevision() {
        return revision;
    }

    public void setRevision(boolean revision) {
        this.revision = revision;
    }

    @Id
    @SequenceGenerator(name = "APP_USUARIO_USUARIOID_GENERATOR", sequenceName = "APP_USUARIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_USUARIO_USUARIOID_GENERATOR")
    @Column(name = "usuario_id")
    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Basic
    @Column(name = "grupo_id")
    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    @Basic
    @Column(name = "fecha_fin")
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic
    @Column(name = "tipo_autenticacion_id")
    public Integer getTipoAutenticacionId() {
        return tipoAutenticacionId;
    }

    public void setTipoAutenticacionId(Integer tipoAutenticacionId) {
        this.tipoAutenticacionId = tipoAutenticacionId;
    }

    @Basic
    @Column(name = "password_valido_hasta")
    public Timestamp getPasswordValidoHasta() {
        return passwordValidoHasta;
    }

    public void setPasswordValidoHasta(Timestamp passwordValidoHasta) {
        this.passwordValidoHasta = passwordValidoHasta;
    }

    @Basic
    @Column(name = "id_device")
    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUsuarioEntity that = (AppUsuarioEntity) o;

        if (usuarioId != that.usuarioId) return false;
        if (grupoId != that.grupoId) return false;
        if (descricpion != null ? !descricpion.equals(that.descricpion) : that.descricpion != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (apellido != null ? !apellido.equals(that.apellido) : that.apellido != null) return false;
        if (carnet != null ? !carnet.equals(that.carnet) : that.carnet != null) return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(that.fechaNacimiento) : that.fechaNacimiento != null)
            return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;
        if (pinPassword != null ? !pinPassword.equals(that.pinPassword) : that.pinPassword != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (creado != null ? !creado.equals(that.creado) : that.creado != null) return false;
        if (creadoPor != null ? !creadoPor.equals(that.creadoPor) : that.creadoPor != null) return false;
        if (modificado != null ? !modificado.equals(that.modificado) : that.modificado != null) return false;
        if (modificadoPor != null ? !modificadoPor.equals(that.modificadoPor) : that.modificadoPor != null)
            return false;
        if (reestablecerPassword != null ? !reestablecerPassword.equals(that.reestablecerPassword) : that.reestablecerPassword != null)
            return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (tipoAutenticacionId != null ? !tipoAutenticacionId.equals(that.tipoAutenticacionId) : that.tipoAutenticacionId != null)
            return false;
        if (passwordValidoHasta != null ? !passwordValidoHasta.equals(that.passwordValidoHasta) : that.passwordValidoHasta != null)
            return false;
        if (genero != null ? !genero.equals(that.genero) : that.genero != null) return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (nombrearchivo != null ? !nombrearchivo.equals(that.nombrearchivo) : that.nombrearchivo != null)
            return false;
        return idDevice != null ? idDevice.equals(that.idDevice) : that.idDevice == null;
    }

    @Override
    public int hashCode() {
        int result = descricpion != null ? descricpion.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + (carnet != null ? carnet.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        result = 31 * result + (pinPassword != null ? pinPassword.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (creado != null ? creado.hashCode() : 0);
        result = 31 * result + (creadoPor != null ? creadoPor.hashCode() : 0);
        result = 31 * result + (modificado != null ? modificado.hashCode() : 0);
        result = 31 * result + (modificadoPor != null ? modificadoPor.hashCode() : 0);
        result = 31 * result + (reestablecerPassword != null ? reestablecerPassword.hashCode() : 0);
        result = 31 * result + (int) (usuarioId ^ (usuarioId >>> 32));
        result = 31 * result + (int) (grupoId ^ (grupoId >>> 32));
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (tipoAutenticacionId != null ? tipoAutenticacionId.hashCode() : 0);
        result = 31 * result + (passwordValidoHasta != null ? passwordValidoHasta.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (nombrearchivo != null ? nombrearchivo.hashCode() : 0);
        result = 31 * result + (idDevice != null ? idDevice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppUsuarioEntity{" +
                "descricpion='" + descricpion + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", carnet='" + carnet + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", path='" + path + '\'' +
                ", usuario='" + usuario + '\'' +
                ", pinPassword='" + pinPassword + '\'' +
                ", estado='" + estado + '\'' +
                ", token='" + token + '\'' +
                ", creado=" + creado +
                ", creadoPor='" + creadoPor + '\'' +
                ", modificado=" + modificado +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", reestablecerPassword=" + reestablecerPassword +
                ", usuarioId=" + usuarioId +
                ", grupoId=" + grupoId +
                ", fechaFin=" + fechaFin +
                ", tipoAutenticacionId=" + tipoAutenticacionId +
                ", passwordValidoHasta=" + passwordValidoHasta +
                ", genero='" + genero + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nombrearchivo='" + nombrearchivo + '\'' +
                ", idDevice='" + idDevice + '\'' +
                '}';
    }
}
