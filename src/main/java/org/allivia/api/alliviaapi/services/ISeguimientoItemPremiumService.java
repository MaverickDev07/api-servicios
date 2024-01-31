package org.allivia.api.alliviaapi.services;

import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.AppSeguimientoItemPremiumEntity;

public interface ISeguimientoItemPremiumService extends IBaseService<AppSeguimientoItemPremiumEntity, Long> {
    AppSeguimientoItemPremiumEntity save(AppSeguimientoItemPremiumEntity appSeguimientoItemPremiumEntity) throws Exception;
}
