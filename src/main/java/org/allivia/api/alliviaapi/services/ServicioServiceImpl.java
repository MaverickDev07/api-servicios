package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppServicioEntity;
import org.allivia.api.alliviaapi.repositories.IServicioRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ServicioServiceImpl implements IServicioService {
    public static final Logger logger = LogManager.getLogger(ServicioServiceImpl.class);
    @Autowired
    private IServicioRepository servicioRepository;


    public AppServicioEntity findByIdServices(long id) {
        logger.log(Level.INFO, "Obtener el servicio con el id " + id);
        AppServicioEntity appServicioEntity = servicioRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado " + appServicioEntity.toString());
        return appServicioEntity;
    }



}