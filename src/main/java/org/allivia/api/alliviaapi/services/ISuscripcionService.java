package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;

public interface ISuscripcionService {
    AppSuscripcionEntity findByIdSuscripcion(long id);
}
