package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;
import org.allivia.api.alliviaapi.entities.AppTituloEntity;
import org.allivia.api.alliviaapi.repositories.IEducacionDoctorRepository;
import org.allivia.api.alliviaapi.repositories.ITitulosRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class EducacionDoctorServiceImpl implements IEducacionDoctorService{
    public static final Logger logger = LogManager.getLogger(EducacionDoctorServiceImpl.class);
    @Autowired
    private IEducacionDoctorRepository iEducacionDoctorRepository;
    @Autowired
    private ITitulosRepository iTitulosRepository;


    public Object save(AppDoctoreducacionEntity appDoctoreducacionEntity) {

        AppTituloEntity appTituloEntity = iTitulosRepository.findById(appDoctoreducacionEntity.getIdtitulo()).get();
        appDoctoreducacionEntity.setTitulo(appTituloEntity);
        logger.log(Level.INFO, "Guardado" + appDoctoreducacionEntity.toString());
        return iEducacionDoctorRepository.save(appDoctoreducacionEntity);
    }

    public Object getEducacionDoctor(long idDoctor){
        List<AppDoctoreducacionEntity> appDoctoreducacionEntityList = iEducacionDoctorRepository.findByIdDoctor(idDoctor);
        for(AppDoctoreducacionEntity row: appDoctoreducacionEntityList){
            AppTituloEntity appTituloEntity = iTitulosRepository.findById(row.getIdtitulo()).get();
            row.setTitulo(appTituloEntity);
        }
        logger.log(Level.INFO, "Listado doctor educacion" + appDoctoreducacionEntityList.toString());
        return appDoctoreducacionEntityList;
    }

    public Object getTitulos(){

        List<AppTituloEntity> appTituloEntityList = iTitulosRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "Listado de titulos" + appTituloEntityList.toString());
        return appTituloEntityList;
    }

    @Transactional
    public void deleteEducacionDoctor(long idDoctor){
        logger.log(Level.INFO, "Eliminacion de registros de educacion del idDoctor" + idDoctor);
        iEducacionDoctorRepository.deleteByIdDoctor(idDoctor);
    }


}
