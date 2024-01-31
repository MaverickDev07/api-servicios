package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppAlergiaEntity;
import org.allivia.api.alliviaapi.repositories.IAlergiaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AlergiaServiceImpl implements IAlergiaService {
    public static final Logger logger = LogManager.getLogger(AlergiaServiceImpl.class);
    @Autowired
    private IAlergiaRepository iAlergiaRepository;



    public Object findAll() {
        List<AppAlergiaEntity>  result = (List<AppAlergiaEntity>) iAlergiaRepository.findAll();
        logger.log(Level.INFO, "Listado de alergias" + result);
        return result;
    }

    @Override
    public Object findByName(String value) {
        List<AppAlergiaEntity>  result =iAlergiaRepository.findAlergia(value);
        logger.log(Level.INFO, "Listado de alergias" + result);
        return result;
    }


}
