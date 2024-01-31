package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppCirugiaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICirugiaRepository extends CrudRepository<AppCirugiaEntity,Long> {
    @Query(value = "select * from app_cirugia c where c.descripcion ilike %:descripcion%",nativeQuery = true)
    List<AppCirugiaEntity> findCirugia(@Param("descripcion") String descripcion);
}
