package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppCirugiaEntity;
import org.allivia.api.alliviaapi.repositories.ICirugiaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CirugiaServiceImpl implements ICirugiaService {
    public static final Logger logger = LogManager.getLogger(CirugiaServiceImpl.class);
    @Autowired
    private ICirugiaRepository iCirugiaRepository;



    public Object findAll() {
        List<AppCirugiaEntity>  result = (List<AppCirugiaEntity>) iCirugiaRepository.findAll();
        logger.log(Level.INFO, "Listado de cirugias" + result);
        return result;
    }

    @Override
    public Object findByName(String value) {
        List<AppCirugiaEntity>  result =iCirugiaRepository.findCirugia(value);
        logger.log(Level.INFO, "Listado de cirugias" + result);
        return result;
    }


}
