package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppDoctorEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.InfoEducacion;
import org.allivia.api.alliviaapi.entities.InformacionLaboral;

import java.util.List;

public interface IDoctorService {
    Object savePerfilDoctor(AppUsuarioEntity userEntity);
    Object updateLicenciaDoctor(AppDoctorEntity appDoctorEntity);
    void updateInfoLaboral(InformacionLaboral informacionLaboral);
    void updateInfoEducacion(InfoEducacion infoEducacion);
    AppDoctorEntity updateFirma(Long id, String firma);
    Object findById(long id );
    List<AppDoctorEntity> findAllEspecialidad();
}
