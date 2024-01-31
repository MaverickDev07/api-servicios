package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAfiliacionesDoctorRepository extends CrudRepository<AppDoctorafiliacionesEntity,Long> {

    List<AppDoctorafiliacionesEntity> findByIdDoctor(long idDoctor);
    void deleteByIdDoctor(long idDoctor);
}
