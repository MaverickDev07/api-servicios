package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppPreguntaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IPreguntaRepository extends CrudRepository<AppPreguntaEntity,Long> {

    @Query(
            value = "SELECT * FROM app_pregunta WHERE sintoma_id=:id and orden=1 and estado='activo'",
            nativeQuery = true)
    AppPreguntaEntity findByPregunta(@Param("id") long id);

}
