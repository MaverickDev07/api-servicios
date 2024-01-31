package org.allivia.api.alliviaapi.services;



import org.allivia.api.alliviaapi.entities.AppPaisEntity;
import org.allivia.api.alliviaapi.repositories.IPaisRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaisServiceImpl implements IPaisService {
    public static final Logger logger = LogManager.getLogger(PaisServiceImpl.class);
    @Autowired
    private IPaisRepository iPaisRepository;



    public Object findAll() {
        List<AppPaisEntity> appPaisEntityList = (List<AppPaisEntity>) iPaisRepository.findAll();
        logger.log(Level.INFO, "Listado de paises " + appPaisEntityList.toString());
        return appPaisEntityList;
    }


}
