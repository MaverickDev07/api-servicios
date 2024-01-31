package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppPreguntaEntity;
import org.allivia.api.alliviaapi.repositories.IPreguntaRepository;
import org.allivia.api.alliviaapi.repositories.ISintomaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PreguntaServiceImpl implements IPreguntaService {
    public static final Logger logger = LogManager.getLogger(PreguntaServiceImpl.class);
    @Autowired
    private IPreguntaRepository iPreguntaRepository;

    public Object findByIdPregunta(long id) {
        logger.log(Level.INFO, "Obtener la primer pregunta con el idSintoma " + id);
        AppPreguntaEntity appPreguntaEntity = iPreguntaRepository.findByPregunta(id);
        logger.log(Level.INFO, "Resultado " + appPreguntaEntity.toString());
        return appPreguntaEntity;
    }

    public Object findById(long id){
        logger.log(Level.INFO, "Obtener la pregunta con el id " + id);
        AppPreguntaEntity appPreguntaEntity = iPreguntaRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado " + appPreguntaEntity.toString());
        return appPreguntaEntity;
    }
}
