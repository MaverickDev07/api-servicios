package org.allivia.api.alliviaapi.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yeri_ on 3/22/2021.
 */
@Entity
@Table(name = "app_agendacita", schema = "public")
public class AppAgendacitaEntity {
    private long id;
    private long idPaciente;
    private long idDoctor;
    private long idTipocita;
    private Long idPago;
    private long idTipoconsulta;
    private long idEspecialidad;
    private String fecha;
    private String horario;
    private String inicioconsulta;
    private String finconsulta;
    private String estadoconsulta;
    private String motivoConsulta;
    private Double precio;
    private String motivoCancelacion;
    private AppTipocitaEntity tipoCita;
    private AppTipoconsultaEntity tipoConsulta;
    private AppDoctorEntity doctor;
    private AppPacienteEntity paciente;
    private AppFichadiagnosticoEntity diagnostico;

    private List<AppAgendadocummentosEntity> motivoarchivos;
    private List<AppFichamedicamentosEntity> medicamentos;
    private List<AppFichalaboratoriosEntity> laboratorios;
    private AppProgramaEntity programa;
    private List<AppFichaexamenesEntity> examenes;
    private List<AppFichadocumentosEntity> archivos;
    private String tipoespecialidad;
    private boolean reconsulta;

    private String nitComprador;
    private String razonSocial;
    private String tipoAgenda;

    @Basic
    @Column(name = "tipoAgenda")
    public String getTipoAgenda() {
        return tipoAgenda;
    }

    public void setTipoAgenda(String tipoAgenda) {
        this.tipoAgenda = tipoAgenda;
    }

    public String getNitComprador() {
        return nitComprador;
    }

    public void setNitComprador(String nitComprador) {
        this.nitComprador = nitComprador;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Transient
    public AppFichadiagnosticoEntity getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(AppFichadiagnosticoEntity diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Transient
    public List<AppFichamedicamentosEntity> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<AppFichamedicamentosEntity> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Transient
    public List<AppFichalaboratoriosEntity> getLaboratorios() {
        return laboratorios;
    }

    public void setLaboratorios(List<AppFichalaboratoriosEntity> laboratorios) {
        this.laboratorios = laboratorios;
    }

    @Transient
    public AppProgramaEntity getPrograma() {
        return programa;
    }

    public void setPrograma(AppProgramaEntity programa) {
        this.programa = programa;
    }

    @Transient
    public List<AppFichaexamenesEntity> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<AppFichaexamenesEntity> examenes) {
        this.examenes = examenes;
    }

    @Transient
    public List<AppFichadocumentosEntity> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<AppFichadocumentosEntity> archivos) {
        this.archivos = archivos;
    }

    @Transient
    public AppPacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(AppPacienteEntity paciente) {
        this.paciente = paciente;
    }

    @Transient
    public AppDoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(AppDoctorEntity doctor) {
        this.doctor = doctor;
    }

    @Transient
    public List<AppAgendadocummentosEntity> getMotivoarchivos() {
        return motivoarchivos;
    }

    public void setMotivoarchivos(List<AppAgendadocummentosEntity> motivoarchivos) {
        this.motivoarchivos = motivoarchivos;
    }

    @Transient
    public AppTipocitaEntity getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(AppTipocitaEntity tipoCita) {
        this.tipoCita = tipoCita;
    }

    @Transient
    public AppTipoconsultaEntity getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(AppTipoconsultaEntity tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }


    @Id
    @SequenceGenerator(name = "APP_AGENDAR_CITA_GENERATOR", sequenceName = "APP_AGENDACITA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_AGENDAR_CITA_GENERATOR")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_paciente")
    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Basic
    @Column(name = "id_doctor")
    public long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(long idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Basic
    @Column(name = "id_tipocita")
    public long getIdTipocita() {
        return idTipocita;
    }

    public void setIdTipocita(long idTipocita) {
        this.idTipocita = idTipocita;
    }

    @Basic
    @Column(name = "id_pago")
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    @Basic
    @Column(name = "id_tipoconsulta")
    public long getIdTipoconsulta() {
        return idTipoconsulta;
    }

    public void setIdTipoconsulta(long idTipoconsulta) {
        this.idTipoconsulta = idTipoconsulta;
    }

    @Basic
    @Column(name = "id_especialidad")
    public long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Basic
    @Column(name = "fecha")
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    @Basic
    @Column(name = "horario")
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Basic
    @Column(name = "inicioconsulta")
    public String getInicioconsulta() {
        return inicioconsulta;
    }

    public void setInicioconsulta(String inicioconsulta) {
        this.inicioconsulta = inicioconsulta;
    }

    @Basic
    @Column(name = "finconsulta")
    public String getFinconsulta() {
        return finconsulta;
    }

    public void setFinconsulta(String finconsulta) {
        this.finconsulta = finconsulta;
    }

    @Basic
    @Column(name = "estadoconsulta")
    public String getEstadoconsulta() {
        return estadoconsulta;
    }

    public void setEstadoconsulta(String estadoconsulta) {
        this.estadoconsulta = estadoconsulta;
    }

    @Basic
    @Column(name = "motivoconsulta")
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    @Basic
    @Column(name = "precio")
    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Basic
    @Column(name = "motivocancelacion")
    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    @Basic
    @Column(name = "reconsulta")
    public boolean isReconsulta() {
        return reconsulta;
    }

    public void setReconsulta(boolean reconsulta) {
        this.reconsulta = reconsulta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppAgendacitaEntity that = (AppAgendacitaEntity) o;

        if (id != that.id) return false;
        if (idPaciente != that.idPaciente) return false;
        if (idDoctor != that.idDoctor) return false;
        if (idTipocita != that.idTipocita) return false;
        if (idTipoconsulta != that.idTipoconsulta) return false;
        if (idEspecialidad != that.idEspecialidad) return false;
        if (reconsulta != that.reconsulta) return false;
        if (idPago != null ? !idPago.equals(that.idPago) : that.idPago != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (horario != null ? !horario.equals(that.horario) : that.horario != null) return false;
        if (inicioconsulta != null ? !inicioconsulta.equals(that.inicioconsulta) : that.inicioconsulta != null)
            return false;
        if (finconsulta != null ? !finconsulta.equals(that.finconsulta) : that.finconsulta != null) return false;
        if (estadoconsulta != null ? !estadoconsulta.equals(that.estadoconsulta) : that.estadoconsulta != null)
            return false;
        if (motivoConsulta != null ? !motivoConsulta.equals(that.motivoConsulta) : that.motivoConsulta != null)
            return false;
        if (precio != null ? !precio.equals(that.precio) : that.precio != null) return false;
        if (motivoCancelacion != null ? !motivoCancelacion.equals(that.motivoCancelacion) : that.motivoCancelacion != null)
            return false;
        if (tipoCita != null ? !tipoCita.equals(that.tipoCita) : that.tipoCita != null) return false;
        if (tipoConsulta != null ? !tipoConsulta.equals(that.tipoConsulta) : that.tipoConsulta != null) return false;
        if (doctor != null ? !doctor.equals(that.doctor) : that.doctor != null) return false;
        if (paciente != null ? !paciente.equals(that.paciente) : that.paciente != null) return false;
        if (diagnostico != null ? !diagnostico.equals(that.diagnostico) : that.diagnostico != null) return false;
        if (motivoarchivos != null ? !motivoarchivos.equals(that.motivoarchivos) : that.motivoarchivos != null)
            return false;
        if (medicamentos != null ? !medicamentos.equals(that.medicamentos) : that.medicamentos != null) return false;
        if (laboratorios != null ? !laboratorios.equals(that.laboratorios) : that.laboratorios != null) return false;
        if (programa != null ? !programa.equals(that.programa) : that.programa != null) return false;
        if (examenes != null ? !examenes.equals(that.examenes) : that.examenes != null) return false;
        if (archivos != null ? !archivos.equals(that.archivos) : that.archivos != null) return false;
        if (tipoAgenda != null ? !tipoAgenda.equals(that.tipoAgenda) : that.tipoAgenda != null) return false;
        return tipoespecialidad != null ? tipoespecialidad.equals(that.tipoespecialidad) : that.tipoespecialidad == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idPaciente ^ (idPaciente >>> 32));
        result = 31 * result + (int) (idDoctor ^ (idDoctor >>> 32));
        result = 31 * result + (int) (idTipocita ^ (idTipocita >>> 32));
        result = 31 * result + (idPago != null ? idPago.hashCode() : 0);
        result = 31 * result + (int) (idTipoconsulta ^ (idTipoconsulta >>> 32));
        result = 31 * result + (int) (idEspecialidad ^ (idEspecialidad >>> 32));
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (horario != null ? horario.hashCode() : 0);
        result = 31 * result + (inicioconsulta != null ? inicioconsulta.hashCode() : 0);
        result = 31 * result + (finconsulta != null ? finconsulta.hashCode() : 0);
        result = 31 * result + (estadoconsulta != null ? estadoconsulta.hashCode() : 0);
        result = 31 * result + (motivoConsulta != null ? motivoConsulta.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        result = 31 * result + (motivoCancelacion != null ? motivoCancelacion.hashCode() : 0);
        result = 31 * result + (tipoCita != null ? tipoCita.hashCode() : 0);
        result = 31 * result + (tipoConsulta != null ? tipoConsulta.hashCode() : 0);
        result = 31 * result + (doctor != null ? doctor.hashCode() : 0);
        result = 31 * result + (paciente != null ? paciente.hashCode() : 0);
        result = 31 * result + (diagnostico != null ? diagnostico.hashCode() : 0);
        result = 31 * result + (motivoarchivos != null ? motivoarchivos.hashCode() : 0);
        result = 31 * result + (medicamentos != null ? medicamentos.hashCode() : 0);
        result = 31 * result + (laboratorios != null ? laboratorios.hashCode() : 0);
        result = 31 * result + (programa != null ? programa.hashCode() : 0);
        result = 31 * result + (examenes != null ? examenes.hashCode() : 0);
        result = 31 * result + (archivos != null ? archivos.hashCode() : 0);
        result = 31 * result + (tipoAgenda != null ? tipoAgenda.hashCode() : 0);
        result = 31 * result + (tipoespecialidad != null ? tipoespecialidad.hashCode() : 0);
        result = 31 * result + (reconsulta ? 1 : 0);
        return result;
    }

    @Basic
    @Column(name = "tipoespecialidad")
    public String getTipoespecialidad() {
        return tipoespecialidad;
    }

    public void setTipoespecialidad(String tipoespecialidad) {
        this.tipoespecialidad = tipoespecialidad;
    }

    @Override
    public String toString() {
        return "AppAgendacitaEntity{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idDoctor=" + idDoctor +
                ", idTipocita=" + idTipocita +
                ", idPago=" + idPago +
                ", idTipoconsulta=" + idTipoconsulta +
                ", idEspecialidad=" + idEspecialidad +
                ", fecha='" + fecha + '\'' +
                ", horario='" + horario + '\'' +
                ", inicioconsulta='" + inicioconsulta + '\'' +
                ", finconsulta='" + finconsulta + '\'' +
                ", estadoconsulta='" + estadoconsulta + '\'' +
                ", motivoConsulta='" + motivoConsulta + '\'' +
                ", precio=" + precio +
                ", motivoCancelacion='" + motivoCancelacion + '\'' +
                ", tipoCita=" + tipoCita +
                ", tipoConsulta=" + tipoConsulta +
                ", doctor=" + doctor +
                ", paciente=" + paciente +
                ", diagnostico=" + diagnostico +
                ", motivoarchivos=" + motivoarchivos +
                ", medicamentos=" + medicamentos +
                ", laboratorios=" + laboratorios +
                ", programa=" + programa +
                ", examenes=" + examenes +
                ", archivos=" + archivos +
                ", tipoespecialidad='" + tipoespecialidad + '\'' +
                ", reconsulta=" + reconsulta +
                ", tipoAgenda=" + tipoAgenda +
                '}';
    }
}
