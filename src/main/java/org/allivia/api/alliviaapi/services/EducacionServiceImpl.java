package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.allivia.api.alliviaapi.repositories.IEducacionRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EducacionServiceImpl implements IEducacionService{
    public static final Logger logger = LogManager.getLogger(EducacionServiceImpl.class);
    @Autowired
    private IEducacionRepository iEducacionRepository;



    public Object save(AppEducacionEntity appEducacionEntity) {
        AppEducacionEntity appEducacionEntity1 = iEducacionRepository.save(appEducacionEntity);
        logger.log(Level.INFO, "Guardado" + appEducacionEntity1.toString());
        return appEducacionEntity1;
    }


}
