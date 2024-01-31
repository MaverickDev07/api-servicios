package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppParametrosEntity;
import org.allivia.api.alliviaapi.entities.AppServicioEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;

import java.util.List;

public interface IServicioService {
    AppServicioEntity findByIdServices(long id);
}
