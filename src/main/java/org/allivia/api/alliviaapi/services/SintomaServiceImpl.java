package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppPreguntaPremiumEntity;
import org.allivia.api.alliviaapi.entities.AppSintomaEntity;
import org.allivia.api.alliviaapi.repositories.IPreguntaPremiumRepository;
import org.allivia.api.alliviaapi.repositories.ISintomaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SintomaServiceImpl implements ISintomaService {
    public static final Logger logger = LogManager.getLogger(SintomaServiceImpl.class);
    @Autowired
    private ISintomaRepository iSintomaRepository;
    @Autowired
    private IPreguntaPremiumRepository iPreguntaPremiumRepository;

    public Object findAll() {
        List<AppSintomaEntity> appSintomaEntityList = iSintomaRepository.findAllByEstado();
        /*for (AppSintomaEntity sintoma: appSintomaEntityList) {
            List<AppPreguntaPremiumEntity> preguntas = iPreguntaPremiumRepository.findByPregunta(sintoma.getId());
            sintoma.setPreguntas(preguntas);
            System.out.println(sintoma.getId());
        }*/
        logger.log(Level.INFO, "Listado de sintomas " + appSintomaEntityList.toString());
        return appSintomaEntityList;
    }

    public Object findById(long id) {
        logger.log(Level.INFO, "Obtener sintoma por id: " + id);
        AppSintomaEntity appSintomaEntity = iSintomaRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado: " + appSintomaEntity.toString());
        return appSintomaEntity;
    }


}
