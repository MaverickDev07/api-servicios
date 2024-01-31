package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppSeguimientoPremiumEntity;

public interface ISeguimientoPremiumService extends IBaseService<AppSeguimientoPremiumEntity, Long> {
    AppSeguimientoPremiumEntity findSeguimientoPremiumPaciente(Long id) throws Exception;
}
