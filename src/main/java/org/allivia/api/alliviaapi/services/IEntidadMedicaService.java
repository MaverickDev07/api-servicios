package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppEntidadMedicaEntity;

import java.util.List;

public interface IEntidadMedicaService extends IBaseService<AppEntidadMedicaEntity, Long> {
    List<AppEntidadMedicaEntity> getEntidadMedicaByTipoEntidad(String tipo) throws Exception;
}
