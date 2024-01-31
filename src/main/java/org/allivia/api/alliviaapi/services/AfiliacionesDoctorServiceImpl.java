package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppAfiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.allivia.api.alliviaapi.repositories.IAfiliacionesDoctorRepository;
import org.allivia.api.alliviaapi.repositories.IAfiliacionesRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class AfiliacionesDoctorServiceImpl implements IAfiliacionesDoctorService{
    public static final Logger logger = LogManager.getLogger(AfiliacionesDoctorServiceImpl.class);

    @Autowired
    private IAfiliacionesDoctorRepository iAfiliacionesDoctorRepository;

    @Autowired
    private IAfiliacionesRepository iAfiliacionesRepository;


    public Object save(AppDoctorafiliacionesEntity appDoctorafiliacionesEntity) {
        logger.log(Level.INFO,"Guardar datos de afiliacion doctor "+appDoctorafiliacionesEntity.toString());
        return iAfiliacionesDoctorRepository.save(appDoctorafiliacionesEntity);
    }


    public List<AppDoctorafiliacionesEntity> getAfiliacionesDoctor(long idDoctor){
        logger.log(Level.INFO,"Obtener afiliciacion por el idDoctor: "+idDoctor);
        List<AppDoctorafiliacionesEntity> appDoctorafiliacionesEntityList = iAfiliacionesDoctorRepository.findByIdDoctor(idDoctor);



        for(AppDoctorafiliacionesEntity row: appDoctorafiliacionesEntityList){
            AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(row.getIdAfiliaciones()).get();
            row.setAfiliaciones(appAfiliacionesEntity);
        }


        logger.log(Level.INFO,"Informacion del doctor afiliacion: "+appDoctorafiliacionesEntityList.toString());

        return appDoctorafiliacionesEntityList;
    }

    @Transactional
    public void deleteAfiliacionDoctor(long idDoctor){
        logger.log(Level.INFO,"Eliminacion de afiliacion por el idDoctor: "+idDoctor);
        iAfiliacionesDoctorRepository.deleteByIdDoctor(idDoctor);
    }

}
