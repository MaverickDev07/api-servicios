package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PacienteServiceImpl implements IPacienteService {
    public static final Logger logger = LogManager.getLogger(PacienteServiceImpl.class);
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private IFichaMedicaRepository iFichaMedicaRepository;

    @Autowired
    private IDiagnosticoRepository iDiagnosticoRepository;


    @Autowired
    private IFMedicamentosRepository IFMedicamentosRepository;

    @Autowired
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;

    @Autowired
    private IMedicamentosRepository iMedicamentosRepository;

    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;

    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;

    @Autowired
    private ISuscripcionPacienteRepository iSuscripcionPacienteRepository;

    @Autowired
    private ILaboratoriosPacientesRepository iLaboratoriosPacientesRepository;

    @Autowired
    private ISuscripcionServicioRepository isuscripcionServicioRepository;

    @Autowired
    private IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository;
    @Autowired
    private ISuscripcionRepository iSuscripcionRepository;


    public Object savePaciente(AppUsuarioEntity userEntity) {

        AppUsuarioEntity userEntityFind = userRepository.findById(userEntity.getUsuarioId()).get();
        userEntityFind.setEstado("HABILITADO");
        userEntityFind.setNombre(userEntity.getNombre());
        userEntityFind.setDescricpion("PACIENTE");
        userEntityFind.setApellido(userEntity.getApellido());
        userEntityFind.setCarnet(userEntity.getCarnet());
        userEntityFind.setFechaNacimiento(userEntity.getFechaNacimiento());
        userEntityFind.setGenero(userEntity.getGenero());
        userEntityFind.setTelefono(userEntity.getTelefono());
        userRepository.save(userEntityFind);
        logger.log(Level.INFO, "Actualizacion de usuario paciente " + userEntityFind.toString());

        AppPacienteEntity pacienteEntityFind = iPacienteRepository.findByUsuario(userEntity.getUsuarioId());
        if (pacienteEntityFind == null) {
            AppPacienteEntity appPacienteEntity = new AppPacienteEntity();
            appPacienteEntity.setDescripcion("Membresia Gratuita");
//            appPacienteEntity.setIdSuscripcion(1);
            appPacienteEntity.setUsuarioId(userEntity.getUsuarioId());
            appPacienteEntity.setPerfilPaciente(userEntityFind);
            AppPacienteEntity getAppPaciente = iPacienteRepository.save(appPacienteEntity);
            logger.log(Level.INFO, "Registro de paciente " + getAppPaciente.toString());

            AppSuscripcionpacienteEntity suscripcionpacienteEntity = new AppSuscripcionpacienteEntity();
            suscripcionpacienteEntity.setIdPaciente(getAppPaciente.getId());
            suscripcionpacienteEntity.setIdSuscripcion(1);
            suscripcionpacienteEntity.setEstado("VIGENTE");
            Date fechaActual = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.MONTH,1);
            suscripcionpacienteEntity.setVigencia(calendar.getTime());

            iSuscripcionPacienteRepository.save(suscripcionpacienteEntity);

            List<AppSuscripcionservicioEntity> appSuscripcionservicioEntities = isuscripcionServicioRepository.findByCodigo("I");

            for(AppSuscripcionservicioEntity row: appSuscripcionservicioEntities){
                AppControlsuscripcionservicioEntity controlsuscripcionservicioEntity = new AppControlsuscripcionservicioEntity();
                controlsuscripcionservicioEntity.setCantidad(row.getCantidad());
                controlsuscripcionservicioEntity.setIdPaciente(getAppPaciente.getId());
                controlsuscripcionservicioEntity.setIdServicio(row.getIdServicio());
                controlsuscripcionservicioEntity.setEstado(true);
                iControlSuscripcionServicioRepository.save(controlsuscripcionservicioEntity);

                logger.log(Level.INFO, "Listado de servicios ofertados " + controlsuscripcionservicioEntity.toString());
            }
            AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository
					.findById(suscripcionpacienteEntity.getIdSuscripcion()).get();
			appPacienteEntity.setSuscripcion(appSuscripcionEntity);

            return appPacienteEntity;
        }
        logger.log(Level.INFO, "El paciente ya se encuentra relacionado con usuario" + pacienteEntityFind.toString() + ", Usuario: "+userEntityFind.toString());
        return null;
    }

    public Object findAll(long idDoctor, String fechaCalendar ){

        StringBuilder fechaInicio = new StringBuilder();
        fechaInicio.append(fechaCalendar).append(" 00:00:00");

        StringBuilder fechaFin = new StringBuilder();
        fechaFin.append(fechaCalendar).append(" 23:59:59");
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaDoctorReagendar(idDoctor,fechaInicio.toString(),fechaFin.toString());
        for(AppAgendacitaEntity agenda: appAgendacitaEntityList){
            AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(agenda.getIdPaciente()).get();
            AppUsuarioEntity appUsuarioEntity = userRepository.findById(appPacienteEntity.getUsuarioId()).get();
            appPacienteEntity.setPerfilPaciente(appUsuarioEntity);
            agenda.setPaciente(appPacienteEntity);
        }

        logger.log(Level.INFO, "Listado de pacientes " + appAgendacitaEntityList.toString());
        return appAgendacitaEntityList;
    }
    public List<AppPacienteEntity> findByNombreContains(String nombre){
        logger.log(Level.INFO, "Busqueda por nombre de paciente " + nombre);
        List<AppPacienteEntity> list= iPacienteRepository.findByNombreContains(nombre);
        for (AppPacienteEntity pacienteEntity:list){
            Optional<AppUsuarioEntity> user=userRepository.findById(pacienteEntity.getUsuarioId());
            user.ifPresent(appUsuarioEntity -> {
                pacienteEntity.setPerfilPaciente(appUsuarioEntity);
            });
        }
        logger.log(Level.INFO, "Resultado  " + list.toString());
        return list;
    }
    public List<AppPacienteEntity> getLastUsersPaciente() {
        logger.log(Level.INFO, "Busqueda ultimos usuarios de paciente ");
        List<AppPacienteEntity> list= iPacienteRepository.findLastUsersPaciente();
        for (AppPacienteEntity pacienteEntity:list){
            Optional<AppUsuarioEntity> user=userRepository.findById(pacienteEntity.getUsuarioId());
            user.ifPresent(appUsuarioEntity -> {
                pacienteEntity.setPerfilPaciente(appUsuarioEntity);
            });
        }
        logger.log(Level.INFO, "Resultado finsLastUsersPaciente p" + list.toString());
        return list;
    }

    public AppPacienteEntity update(AppUsuarioEntity userEntity) {
        AppUsuarioEntity userEntityFind = userRepository.findById(userEntity.getUsuarioId()).get();
        if(userEntity.getNombre()!=null){
            userEntityFind.setNombre(userEntity.getNombre());
        }
        if(userEntity.getApellido()!=null){
            userEntityFind.setApellido(userEntity.getApellido());
        }
        if(userEntity.getFechaNacimiento()!=null){
            userEntityFind.setFechaNacimiento(userEntity.getFechaNacimiento());
        }
        if(userEntity.getCarnet()!=null){
            userEntityFind.setCarnet(userEntity.getCarnet());
        }
        if(userEntity.getEmail()!=null){
            userEntityFind.setEmail(userEntity.getEmail());
        }
        if(userEntity.getGenero()!=null){
            userEntityFind.setGenero(userEntity.getGenero());
        }
        if(userEntity.getDireccion()!=null){
            userEntityFind.setDireccion(userEntity.getDireccion());
        }
        if(userEntity.getTelefono()!=null){
            userEntityFind.setTelefono(userEntity.getTelefono());
        }

        userRepository.save(userEntityFind);
        logger.log(Level.INFO, "Actualizacion del usuario  " + userEntityFind.toString());

        AppPacienteEntity appPacienteEntity = iPacienteRepository.findByUsuarioId(userEntityFind.getUsuarioId());
        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(appPacienteEntity.getId());
        AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository.findById(appSuscripcionpacienteEntity.getIdSuscripcion()).get();

        List<AppControlsuscripcionservicioEntity> appControlsuscripcionservicioEntityList = iControlSuscripcionServicioRepository.findByIdPacienteSuscripcionActual(appPacienteEntity.getId());

        appPacienteEntity.setPerfilPaciente(userEntityFind);
        appPacienteEntity.setSuscripcion(appSuscripcionEntity);
        appPacienteEntity.setServicios(appControlsuscripcionservicioEntityList);

        return appPacienteEntity;
    }

    public List<LaboratoriospacienteEntity> getListLaboratoriosPaciente(long idPaciente){
        List<LaboratoriospacienteEntity> LabPaciente = iLaboratoriosPacientesRepository.findByIdPaciente(idPaciente);
        List<LaboratoriospacienteEntity> LabPacientePremium = iLaboratoriosPacientesRepository.laboratoriospacientepremiumFindByIdPaciente(idPaciente);

        List<LaboratoriospacienteEntity> result = new ArrayList<LaboratoriospacienteEntity>(LabPaciente);
        result.addAll(LabPacientePremium);
        return result;
    }

    public List<AppAgendacitaEntity> getListRecetasPaciente(long idPaciente, String nombreDoctor) {
        List<AppAgendacitaEntity> appAgendacitaEntityList;
        if(nombreDoctor != null && !nombreDoctor.isEmpty()) {
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPacienteRecetas(idPaciente,nombreDoctor);
        } else {
           appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPacienteRecetas(idPaciente);
        }

        for(AppAgendacitaEntity cita: appAgendacitaEntityList) {
            logger.log(Level.INFO, "Recorrer agenda cita: " + cita.toString());
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(cita.getIdDoctor()).get();
            AppFichamedicaEntity appFichamedicaEntity = iFichaMedicaRepository.findByIdAgendacita(cita.getId());
            AppFichadiagnosticoEntity appFichadiagnosticoEntity = iDiagnosticoRepository.findByIdFichamedica(appFichamedicaEntity.getId());
            AppUsuarioEntity appUsuarioEntity = userRepository.findById(appDoctorEntity.getUsuarioId()).get();
            List<AppDoctoresespecialidadesEntity> appEspecialidadEntityList = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());


            List<AppFichamedicamentosEntity> appFichamedicamentosEntityList = IFMedicamentosRepository.findByIdFichamedica(appFichamedicaEntity.getId());
            List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
            for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : appEspecialidadEntityList) {
                AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                appEspecialidadEntities.add(especialidadEntity);
            }
            for (AppFichamedicamentosEntity rowFichamedicamentos : appFichamedicamentosEntityList) {
                if (rowFichamedicamentos.getIdMedicamento() != null) {
                    Optional<AppMedicamentosEntity> appMedicamentosEntity = iMedicamentosRepository.findById(rowFichamedicamentos.getIdMedicamento());
                    rowFichamedicamentos.setMedicamento(appMedicamentosEntity);
                }
            }
            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
            cita.setDoctor(appDoctorEntity);
            cita.setMedicamentos(appFichamedicamentosEntityList);
            cita.setDiagnostico(appFichadiagnosticoEntity);
        }

        return appAgendacitaEntityList;

    }


}
