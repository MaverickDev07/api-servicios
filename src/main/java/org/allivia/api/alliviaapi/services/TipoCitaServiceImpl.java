package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppTipocitaEntity;
import org.allivia.api.alliviaapi.repositories.ITipoCitaRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TipoCitaServiceImpl implements ITipoCitaService {
    public static final Logger logger = LogManager.getLogger(TipoCitaServiceImpl.class);
    @Autowired
    private ITipoCitaRepository iTipoCitaRepository;



    public Object findAll() {
        List<AppTipocitaEntity>  appTipocitaEntityList = (List<AppTipocitaEntity>) iTipoCitaRepository.findAll();
        logger.log(Level.INFO, "Listado de tipos de citas" + appTipocitaEntityList.toString());
        return appTipocitaEntityList;
    }



}
