package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppParametrosEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.repositories.IParametroRepository;
import org.allivia.api.alliviaapi.repositories.IUserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ParametroServiceImpl implements IParametroService {
    public static final Logger logger = LogManager.getLogger(ParametroServiceImpl.class);
    @Autowired
    private IParametroRepository parametroRepository;

    public AppParametrosEntity findParametro(String Clave) {
        logger.log(Level.INFO, "Busqueda de parametro por la clave " + Clave);
        AppParametrosEntity appParametrosEntity = parametroRepository.findByClave(Clave);
        logger.log(Level.INFO, "Resultado " + appParametrosEntity.toString());
        return appParametrosEntity;
    }
    public List<AppParametrosEntity> findByParType(String type) {
        logger.log(Level.INFO, "Busqueda de parametro por grupo " + type);
        List<AppParametrosEntity> result = parametroRepository.findByParType(type);
        logger.log(Level.INFO, "Resultados " + result.size());
        return result;
    }

}