package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppSintomaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISintomaRepository extends CrudRepository<AppSintomaEntity,Long> {
    @Query(
            value = "SELECT * FROM app_sintoma WHERE estado='activo'",
            nativeQuery = true)
    List<AppSintomaEntity> findAllByEstado();
}
