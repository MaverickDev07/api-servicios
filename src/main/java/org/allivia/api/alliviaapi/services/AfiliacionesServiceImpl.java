package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppAfiliacionesEntity;
import org.allivia.api.alliviaapi.repositories.IAfiliacionesRepository;
import org.allivia.api.alliviaapi.repositories.IEspecialidadesRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AfiliacionesServiceImpl implements IAfiliacionesService {
    public static final Logger logger = LogManager.getLogger(AfiliacionesServiceImpl.class);
    @Autowired
    private IAfiliacionesRepository iAfiliacionesRepository;



    public Object findAll() {
        List<AppAfiliacionesEntity> appAfiliacionesEntityList =  iAfiliacionesRepository.findByEliminadoFalse();
        logger.log(Level.INFO,"Listado de afiliaciones "+appAfiliacionesEntityList.toString());
        return appAfiliacionesEntityList;
    }


}
