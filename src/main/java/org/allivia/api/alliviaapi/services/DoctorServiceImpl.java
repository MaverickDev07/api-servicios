package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


@Component
public class DoctorServiceImpl implements IDoctorService {
    public static final Logger logger = LogManager.getLogger(DoctorServiceImpl.class);
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IDoctorRepository iDoctorRepository;
    @Autowired
    private IAfiliacionesDoctorService iAfiliacionesDoctorService;
    @Autowired
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;
    @Autowired
    private IAfiliacionesDoctorRepository iAfiliacionesDoctorRepository;
    @Autowired
    private IEducacionDoctorRepository iEducacionDoctorRepository;
    @Autowired
    private ExecutorService EXECUTOR_SERVICE;
    @Autowired
    private NotificationMail notificationMail;
    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;

    @Value( "${spring.mail.subject}")
    private String mailSubject;


    public Object savePerfilDoctor(AppUsuarioEntity userEntity) {

        AppUsuarioEntity userEntityFind = userRepository.findById(userEntity.getUsuarioId()).get();
        userEntityFind.setNombre(userEntity.getNombre());
        userEntityFind.setApellido(userEntity.getApellido());
        userEntityFind.setDescricpion("DOCTOR");
        userEntityFind.setCarnet(userEntity.getCarnet());
        userEntityFind.setFechaNacimiento(userEntity.getFechaNacimiento());
        userEntityFind.setGenero(userEntity.getGenero());

        userRepository.save(userEntityFind);

        logger.log(Level.INFO, "Actualizacion de usuario doctor " + userEntityFind.toString());
        AppDoctorEntity appDoctorEntityFind = iDoctorRepository.findByUsuarioId(userEntity.getUsuarioId());
        if(appDoctorEntityFind == null){
            AppDoctorEntity appDoctorEntity = new AppDoctorEntity();
            appDoctorEntity.setUsuarioId(userEntity.getUsuarioId());
            appDoctorEntity.setPerfilDoctor(userEntityFind);
            appDoctorEntity.setPacientes("0");
            iDoctorRepository.save(appDoctorEntity);
            logger.log(Level.INFO, "Guardado" + appDoctorEntity.toString());
            return appDoctorEntity;
        }
        logger.log(Level.INFO, "El doctor ya se encuentra relacionado con usuario" + appDoctorEntityFind.toString() + ", Usuario: "+userEntity.toString());
        return null;
    }

    public Object updateLicenciaDoctor(AppDoctorEntity appDoctorEntity){
        AppDoctorEntity appDoctorEntityFind = iDoctorRepository.findById(appDoctorEntity.getId()).get();
        appDoctorEntityFind.setAdjunto(appDoctorEntity.getAdjunto());
        appDoctorEntityFind.setPais(appDoctorEntity.getPais());
        appDoctorEntityFind.setDescripcion(appDoctorEntity.getDescripcion());
        iDoctorRepository.save(appDoctorEntityFind);
        logger.log(Level.INFO, "Actualizado" + appDoctorEntityFind.toString());
        AppUsuarioEntity userEntityFind = userRepository.findById(appDoctorEntityFind.getUsuarioId()).get();
        userEntityFind.setEstado("HABILITADO");
        userEntityFind.setModificado(Timestamp.from(Instant.now()));
        userRepository.save(userEntityFind);
        logger.log(Level.INFO, "Actualizado" + userEntityFind.toString());
        return appDoctorEntityFind;

    }
    @Transactional
    public void updateInfoLaboral(InformacionLaboral informacionLaboral){
        iAfiliacionesDoctorService.deleteAfiliacionDoctor(informacionLaboral.getDoctorId());
        iEspecialidadDoctorRepository.deleteByIdDoctor(informacionLaboral.getDoctorId());
        for (AppAfiliacionesEntity afiliacion: informacionLaboral.getAfiliaciones()){
            AppDoctorafiliacionesEntity appDoctorafiliacion=new AppDoctorafiliacionesEntity();
            appDoctorafiliacion.setIdAfiliaciones(afiliacion.getId());
            appDoctorafiliacion.setIdDoctor(informacionLaboral.getDoctorId());
            iAfiliacionesDoctorRepository.save(appDoctorafiliacion);
            logger.log(Level.INFO, "Actualizado" + appDoctorafiliacion.toString());
        }
        for (AppEspecialidadEntity especialidad: informacionLaboral.getEspecialidades()) {
            AppDoctoresespecialidadesEntity appDoctoresespecialidad = new AppDoctoresespecialidadesEntity();
            appDoctoresespecialidad.setIdDoctor(informacionLaboral.getDoctorId());
            appDoctoresespecialidad.setIdEspecialidad(especialidad.getId());
            iEspecialidadDoctorRepository.save(appDoctoresespecialidad);
            logger.log(Level.INFO, "Actualizado" + appDoctoresespecialidad.toString());
        }
        AppDoctorEntity appDoctorEntityFind = iDoctorRepository.findById(informacionLaboral.getDoctorId()).get();
        AppUsuarioEntity userEntityFind = userRepository.findById(appDoctorEntityFind.getUsuarioId()).get();
        userEntityFind.setEstado("HABILITADO");
        userEntityFind.setModificado(Timestamp.from(Instant.now()));
        userRepository.save(userEntityFind);
    }

    @Override
    @Transactional
    public void updateInfoEducacion(InfoEducacion infoEducacion) {

        AppDoctorEntity appDoctorEntityFind = iDoctorRepository.findById(infoEducacion.getDoctorId()).get();
        AppUsuarioEntity appUsuarioEntity = userRepository.findById(appDoctorEntityFind.getUsuarioId()).get();

        appDoctorEntityFind.setPais(infoEducacion.getPais());
        appDoctorEntityFind.setDescripcion(infoEducacion.getNroLicencia());
        iDoctorRepository.save(appDoctorEntityFind);
        logger.log(Level.INFO, "Actualizado" + appDoctorEntityFind.toString());
        iEducacionDoctorRepository.deleteByIdDoctor(infoEducacion.getDoctorId());
        for(AppDoctoreducacionEntity de:infoEducacion.getDoctoreducaciones()){
            de.setId(0);
            de.setIdDoctor(infoEducacion.getDoctorId());
            de.setIdtitulo(de.getTitulo().getId());
            logger.log(Level.INFO, "Actualizado" + de.toString());
            iEducacionDoctorRepository.save(de);
        }
        if(infoEducacion.isCreate()){
                EXECUTOR_SERVICE.submit(()->{
                    notificationMail.sendMessage(appUsuarioEntity.getEmail(),mailSubject);
           });
        }
    }

    @Override
    @Transactional
    public AppDoctorEntity updateFirma(Long id, String firma) {
        AppDoctorEntity appDoctorEntityFind = iDoctorRepository.findById(id).get();
        appDoctorEntityFind.setFirma(firma);
        logger.log(Level.INFO, "Actualizacion de la firma del usuario: " + appDoctorEntityFind.toString());
        return appDoctorEntityFind;
    }

    @Override
    public Object findById(long id) {
        logger.log(Level.INFO, "Obtener doctor por el id:" + id);
        AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado:" + appDoctorEntity.toString());
        return appDoctorEntity;
    }

    @Override
    public List<AppDoctorEntity> findAllEspecialidad() {
        logger.log(Level.INFO, "Consulta Doctores por especialidades");
        List<AppDoctorEntity> appDoctor = iDoctorRepository.findAllMedicosEspecialistas();

        for (AppDoctorEntity row : appDoctor) {
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(row.getId()).get();
            AppUsuarioEntity appUsuarioEntity = userRepository.findById(appDoctorEntity.getUsuarioId()).get();
            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            List<AppDoctoresespecialidadesEntity> appEspecialidadEntityList = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());
            List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
            for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : appEspecialidadEntityList) {
                AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                appEspecialidadEntities.add(especialidadEntity);
            }
            /*
            List<AppDoctorafiliacionesEntity> afiliacionesServiceList = iAfiliacionesDoctorService.getAfiliacionesDoctor(appDoctorEntity.getId());
            List<AppAfiliacionesEntity> appAfiliacionesEntityList = new ArrayList<>();
            if (afiliacionesServiceList != null) {
                for (AppDoctorafiliacionesEntity row2 : afiliacionesServiceList) {
                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(row2.getIdAfiliaciones()).get();
                    appAfiliacionesEntityList.add(appAfiliacionesEntity);
                }
            }
            appDoctorEntity.setListAfiliaciones(appAfiliacionesEntityList);
            */
            appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
        }

        logger.log(Level.INFO, "Listado de doctores con especialidad: " + appDoctor.toString());
        return appDoctor;
    }
}