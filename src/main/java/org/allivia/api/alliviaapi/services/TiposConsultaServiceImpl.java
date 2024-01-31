package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppTipoconsultaEntity;
import org.allivia.api.alliviaapi.repositories.ITiposConsultaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TiposConsultaServiceImpl implements ITiposConsultaService {
    public static final Logger logger = LogManager.getLogger(TiposConsultaServiceImpl.class);
    @Autowired
    private ITiposConsultaRepository iTiposConsultaRepository;



    public Object findAll() {
        List<AppTipoconsultaEntity> appTipoconsultaEntityList = iTiposConsultaRepository.findAllByEliminadoFalseOrderByIdAsc();
        logger.log(Level.INFO, "Listado de tipos de consultas" + appTipoconsultaEntityList.toString());
        return appTipoconsultaEntityList;
    }

}
