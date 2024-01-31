package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppEnfermedadBaseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnfermedadBaseRepository extends CrudRepository<AppEnfermedadBaseEntity,Long> {

    @Query(value = "select * from app_enfermedad_base aeb where aeb.descripcion ilike %:descripcion%",nativeQuery = true)
    List<AppEnfermedadBaseEntity> findEnfermedadBase(@Param("descripcion") String descripcion);

}
