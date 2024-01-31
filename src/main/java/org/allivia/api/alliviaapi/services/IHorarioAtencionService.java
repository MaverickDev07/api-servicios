package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppHorarioatencionEntity;

import java.text.ParseException;

public interface IHorarioAtencionService {
    Object save(AppHorarioatencionEntity appHorarioatencionEntity);
    Object findAllHorarios();
    Object findAllDias();
    Object getHorarioAtencionDoctor(long idDoctor, String fecha);
    Object getHorasAtencionDoctor(long idDoctor, String fecha) throws ParseException;
    Object updateHoraAtencion(long id, boolean estado);
}
