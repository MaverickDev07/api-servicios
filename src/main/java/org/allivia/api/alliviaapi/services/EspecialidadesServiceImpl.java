package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppEspecialidadEntity;
import org.allivia.api.alliviaapi.repositories.IDoctorRepository;
import org.allivia.api.alliviaapi.repositories.IEspecialidadDoctorRepository;
import org.allivia.api.alliviaapi.repositories.IEspecialidadesRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class EspecialidadesServiceImpl implements IEspecialidadesService {
    public static final Logger logger = LogManager.getLogger(EspecialidadesServiceImpl.class);
    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;


    public Object findAll() {
        List<AppEspecialidadEntity> appEspecialidadEntityList = iEspecialidadesRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "Obtener listado de especialidades" + appEspecialidadEntityList.toString());
        return appEspecialidadEntityList;
    }
    public Object findById(long id) {
        Optional<AppEspecialidadEntity> appEspecialidadEntity = iEspecialidadesRepository.findById(id);
        if(appEspecialidadEntity.isPresent()){
            return appEspecialidadEntity.get();
        }else return null;
    }

    public Object findByNombre(String descripcion){
        logger.log(Level.INFO, "Obtener especialiadad por despcripcion" + descripcion);
        Optional<AppEspecialidadEntity> appEspecialidadEntity = iEspecialidadesRepository.findByDescripcionIsLike(descripcion);
        logger.log(Level.INFO, "especialidad" + appEspecialidadEntity);
        if(appEspecialidadEntity.isPresent()){
        return appEspecialidadEntity.get();
        }else return null;
    }





}
