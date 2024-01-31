package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.repositories.ISuscripcionRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SuscripcionServiceImpl implements ISuscripcionService {
    public static final Logger logger = LogManager.getLogger(SuscripcionServiceImpl.class);
    @Autowired
    private ISuscripcionRepository suscripcionRepository;


    public AppSuscripcionEntity findByIdSuscripcion(long id) {
        logger.log(Level.INFO, "Obtener suscripcion con el id " + id);
        AppSuscripcionEntity appSuscripcionEntity = suscripcionRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado " + appSuscripcionEntity.toString());
        return appSuscripcionEntity;
    }



}