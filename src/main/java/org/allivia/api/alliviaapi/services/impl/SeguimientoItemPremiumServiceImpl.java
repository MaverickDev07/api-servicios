package org.allivia.api.alliviaapi.services.impl;

import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.controllers.SeguimientoItemPremiumController;
import org.allivia.api.alliviaapi.entities.AppAntecedenteMedicoEntity;
import org.allivia.api.alliviaapi.entities.AppSeguimientoItemPremiumEntity;
import org.allivia.api.alliviaapi.entities.AppSeguimientoPremiumEntity;
import org.allivia.api.alliviaapi.repositories.IAntecedenteMedicoRepository;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ISeguimientoItemPremiumRepository;
import org.allivia.api.alliviaapi.repositories.ISeguimientoPremiumRepository;
import org.allivia.api.alliviaapi.services.ISeguimientoItemPremiumService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoItemPremiumServiceImpl extends BaseServiceImpl<AppSeguimientoItemPremiumEntity, Long> implements ISeguimientoItemPremiumService {
    public static final Logger logger = LogManager.getLogger(SeguimientoItemPremiumController.class);
    @Autowired
    private ISeguimientoItemPremiumRepository iSeguimientoItemPremiumRepository;

    public SeguimientoItemPremiumServiceImpl(IBaseRepository<AppSeguimientoItemPremiumEntity, Long> baseRepository) { super(baseRepository); }

    @Override
    public AppSeguimientoItemPremiumEntity save(AppSeguimientoItemPremiumEntity appSeguimientoItemPremiumEntity) throws Exception {
        logger.log(Level.INFO, "SeguimientoItemPremium: " + appSeguimientoItemPremiumEntity.toString());
        List<AppSeguimientoItemPremiumEntity> seguimientoItemPremium = iSeguimientoItemPremiumRepository.getByIdSeguimiento(appSeguimientoItemPremiumEntity.getSeguimiento().getId());
        AppSeguimientoItemPremiumEntity seguimientoItemPremiumSelect =  appSeguimientoItemPremiumEntity;
        if (seguimientoItemPremium.size() > 0) {
            for (AppSeguimientoItemPremiumEntity row : seguimientoItemPremium) {
                //System.out.println(row.getItem().getId() + " <-> " + appSeguimientoItemPremiumEntity.getItem().getId());
                if ( row.getEstado().equals("SinReclamar") && row.getItem().getId() == appSeguimientoItemPremiumEntity.getItem().getId() ) {
                    // seguimientoItemPremiumSelect = row;
                    System.out.println(row.getId());
                    appSeguimientoItemPremiumEntity.setId(row.getId());
                    seguimientoItemPremiumSelect = update(row.getId(), appSeguimientoItemPremiumEntity);
                }
            }
        }

        return seguimientoItemPremiumSelect;
    }
}
