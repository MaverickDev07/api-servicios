package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppParametrosEntity;

import java.util.List;


public interface IParametroService {
    AppParametrosEntity findParametro(String clave);
    public List<AppParametrosEntity> findByParType(String type);
}
