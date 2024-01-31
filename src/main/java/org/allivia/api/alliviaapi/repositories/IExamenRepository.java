package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppExamenesEntity;
import org.allivia.api.alliviaapi.entities.AppFichaexamenesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IExamenRepository extends CrudRepository<AppExamenesEntity,Long> {

    List<AppExamenesEntity> findByDescripcionContainingIgnoreCaseAndEliminadoFalse(String descripcion);



}
