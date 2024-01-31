package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppAlergiaEntity;
import org.allivia.api.alliviaapi.entities.AppVacunaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVacunaRepository extends CrudRepository<AppVacunaEntity,Long> {
    @Query(value = "select * from app_vacuna v where v.descripcion ilike %:descripcion%",nativeQuery = true)
    List<AppVacunaEntity> findVacuna(@Param("descripcion") String descripcion);
}
