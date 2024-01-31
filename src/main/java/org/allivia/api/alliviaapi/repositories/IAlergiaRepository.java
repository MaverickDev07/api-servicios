package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppAlergiaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlergiaRepository extends CrudRepository<AppAlergiaEntity,Long> {

    @Query(value = "select * from app_alergia a where a.descripcion ilike %:descripcion%",nativeQuery = true)
    List<AppAlergiaEntity> findAlergia(@Param("descripcion") String descripcion);
}
