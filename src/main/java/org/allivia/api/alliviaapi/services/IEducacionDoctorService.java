package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;


public interface IEducacionDoctorService {
    Object save(AppDoctoreducacionEntity appDoctoreducacionEntity);
    Object getEducacionDoctor(long idDoctor);
    Object getTitulos();
    void deleteEducacionDoctor(long idDoctor);
}
