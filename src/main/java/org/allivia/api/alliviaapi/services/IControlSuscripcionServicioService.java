package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppControlsuscripcionservicioEntity;

import java.text.ParseException;

public interface IControlSuscripcionServicioService {
    Object save(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity);
//    Object update(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity);
    AppControlsuscripcionservicioEntity updateControlSuscripcionServicio(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity);
    AppControlsuscripcionservicioEntity updateControlSuscripcionServicioTipo(long idPaciente, int cantidad, String codigo);
    AppControlsuscripcionservicioEntity getControlSuscripcionServicioTipo(String tipo, long idPaciente) throws ParseException;
}
