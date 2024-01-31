package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppDiasEntity;
import org.allivia.api.alliviaapi.entities.AppDoctoresespecialidadesEntity;

import java.text.ParseException;
import java.util.List;


public interface IEspecialidadDoctorService {
    Object save(AppDoctoresespecialidadesEntity appDoctoresespecialidadesEntity);
    Object findEspecialidadDoctor(long id) throws ParseException;
    Object findEspecialidadDoctorTipo(String tipo) throws ParseException;
    Object findDoctorEspecialidad(String nombre) throws ParseException;
    Object findDoctorEspecialidad(long idDoctor);
    List<AppDoctoresespecialidadesEntity> getEspecialidadesDoctor(long idDoctor);
    void deleteEspecialidadDoctor(long idDoctor);
    Object findDoctorAleatorio() throws ParseException;
    Object findDoctorEspecialidad(long idEspecialidad, String nombre);
    List<AppDiasEntity> obtenerDiasHorasAtencion(long id);

}
