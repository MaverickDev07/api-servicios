package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;

import java.util.List;


public interface IAfiliacionesDoctorService {
    Object save(AppDoctorafiliacionesEntity appDoctorafiliacionesEntity);
    List<AppDoctorafiliacionesEntity> getAfiliacionesDoctor(long idDoctor);
    void deleteAfiliacionDoctor(long idDoctor);
}
