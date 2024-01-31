package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by yeri_ on 3/11/2021.
 */
@Entity
@Table(name = "app_doctor")
public class AppDoctorEntity {
    private long id;
    private Long usuarioId;
    private String descripcion;
    private String pais;
    private byte[] adjunto;
    private Integer recomendacion;
    private String biografia;
    private String pacientes;
    private String experiencias;
    private AppUsuarioEntity perfilDoctor;
    private List<AppEspecialidadEntity> listEspecialidad;
    private List<AppAfiliacionesEntity> listAfiliaciones;
    private List<AppDiasEntity> diasAtencion;
    private String path;
    private String nombrearchivo;
    private String tipoDoctor;
    private String firma;

    @Basic
    @Column(name = "firma")
    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    @Basic
    @Column(name = "tipoDoctor")
    public String getTipoDoctor() {
        return tipoDoctor;
    }

    public void setTipoDoctor(String tipoDoctor) {
        this.tipoDoctor = tipoDoctor;
    }

    @Transient
    public List<AppDiasEntity> getDiasAtencion() {
        return diasAtencion;
    }

    public void setDiasAtencion(List<AppDiasEntity> diasAtencion) {
        this.diasAtencion = diasAtencion;
    }


    @Transient
    public List<AppEspecialidadEntity> getListEspecialidad() {
        return listEspecialidad;
    }

    public void setListEspecialidad(List<AppEspecialidadEntity> listEspecialidad) {
        this.listEspecialidad = listEspecialidad;
    }

    @Transient
    public List<AppAfiliacionesEntity> getListAfiliaciones() {
        return listAfiliaciones;
    }

    public void setListAfiliaciones(List<AppAfiliacionesEntity> listAfiliaciones) {
        this.listAfiliaciones = listAfiliaciones;
    }

    @Transient
    public AppUsuarioEntity getPerfilDoctor() {
        return perfilDoctor;
    }

    public void setPerfilDoctor(AppUsuarioEntity perfilDoctor) {
        this.perfilDoctor = perfilDoctor;
    }

    @Id
    @SequenceGenerator(name = "APP_DOCTOR_DOCTORID_GENERATOR", sequenceName = "APP_DOCTOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_DOCTOR_DOCTORID_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "usuario_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
    @Column(name = "pais")
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    @Basic
    @Column(name = "adjunto")
    public byte[] getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(byte[] adjunto) {
        this.adjunto = adjunto;
    }

    @Basic
    @Column(name = "recomendacion")
    public Integer getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(Integer recomendacion) {
        this.recomendacion = recomendacion;
    }
    @Basic
    @Column(name = "biografia")
    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    @Basic
    @Column(name = "pacientes")
    public String getPacientes() {
        return pacientes;
    }

    public void setPacientes(String pacientes) {
        this.pacientes = pacientes;
    }
    @Basic
    @Column(name = "experiencia")
    public String getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(String experiencias) {
        this.experiencias = experiencias;
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
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppDoctorEntity that = (AppDoctorEntity) o;
        return id == that.id && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(descripcion, that.descripcion) && Objects.equals(pais, that.pais) && Arrays.equals(adjunto, that.adjunto) && Objects.equals(recomendacion, that.recomendacion) && Objects.equals(biografia, that.biografia) && Objects.equals(pacientes, that.pacientes) && Objects.equals(experiencias, that.experiencias) && Objects.equals(perfilDoctor, that.perfilDoctor) && Objects.equals(listEspecialidad, that.listEspecialidad) && Objects.equals(listAfiliaciones, that.listAfiliaciones) && Objects.equals(diasAtencion, that.diasAtencion) && Objects.equals(path, that.path) && Objects.equals(nombrearchivo, that.nombrearchivo) && Objects.equals(tipoDoctor, that.tipoDoctor) && Objects.equals(firma, that.firma);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, usuarioId, descripcion, pais, recomendacion, biografia, pacientes, experiencias, perfilDoctor, listEspecialidad, listAfiliaciones, diasAtencion, path, nombrearchivo, tipoDoctor, firma);
        result = 31 * result + Arrays.hashCode(adjunto);
        return result;
    }

    @Override
    public String toString() {
        return "AppDoctorEntity{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", descripcion='" + descripcion + '\'' +
                ", pais='" + pais + '\'' +
                ", adjunto=" + Arrays.toString(adjunto) +
                ", recomendacion=" + recomendacion +
                ", biografia='" + biografia + '\'' +
                ", pacientes='" + pacientes + '\'' +
                ", experiencias='" + experiencias + '\'' +
                ", perfilDoctor=" + perfilDoctor +
                ", listEspecialidad=" + listEspecialidad +
                ", listAfiliaciones=" + listAfiliaciones +
                ", diasAtencion=" + diasAtencion +
                ", path='" + path + '\'' +
                ", nombrearchivo='" + nombrearchivo + '\'' +
                ", tipoDoctor='" + tipoDoctor + '\'' +
                ", firma='" + firma + '\'' +
                '}';
    }
}
