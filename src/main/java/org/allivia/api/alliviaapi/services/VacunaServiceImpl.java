package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppEnfermedadBaseEntity;
import org.allivia.api.alliviaapi.entities.AppTipocitaEntity;
import org.allivia.api.alliviaapi.entities.AppVacunaEntity;
import org.allivia.api.alliviaapi.repositories.ITipoCitaRepository;
import org.allivia.api.alliviaapi.repositories.IVacunaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class VacunaServiceImpl implements IVacunaService {
    public static final Logger logger = LogManager.getLogger(VacunaServiceImpl.class);
    @Autowired
    private IVacunaRepository iVacunaRepository;



    public Object findAll() {
        List<AppVacunaEntity>  result = (List<AppVacunaEntity>) iVacunaRepository.findAll();
        logger.log(Level.INFO, "Listado de vacunas" + result);
        return result;
    }

    @Override
    public Object findByName(String value) {
        List<AppVacunaEntity>  result =iVacunaRepository.findVacuna(value);
        logger.log(Level.INFO, "Listado de vacunas" + result);
        return result;
    }


}
