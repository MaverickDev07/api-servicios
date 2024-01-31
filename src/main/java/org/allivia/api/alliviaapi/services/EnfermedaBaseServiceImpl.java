package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppEnfermedadBaseEntity;
import org.allivia.api.alliviaapi.repositories.IEnfermedadBaseRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EnfermedaBaseServiceImpl implements IEnfermedadBaseService {
    public static final Logger logger = LogManager.getLogger(EnfermedaBaseServiceImpl.class);
    @Autowired
    private IEnfermedadBaseRepository iEnfermedadBaseRepository;

    public Object findAll() {
        List<AppEnfermedadBaseEntity>  result = (List<AppEnfermedadBaseEntity>) iEnfermedadBaseRepository.findAll();
        logger.log(Level.INFO, "Listado de enfermedades de base" + result);
        return result;
    }

    @Override
    public Object findByName(String value) {
        List<AppEnfermedadBaseEntity>  result =iEnfermedadBaseRepository.findEnfermedadBase(value);
        logger.log(Level.INFO, "Listado de enfermedades de base" + result);
        return result;

    }


}
