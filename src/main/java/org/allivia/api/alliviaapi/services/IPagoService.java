package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppPagoEntity;


public interface IPagoService {
    Object save(AppPagoEntity appPagoEntity);

    Object update(String transaccion);
}
