package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppExamenesEntity;
import org.allivia.api.alliviaapi.entities.AppLaboratoriosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ILaboratorioRepository extends CrudRepository<AppLaboratoriosEntity,Long> {

    List<AppLaboratoriosEntity> findByDescripcionContainingIgnoreCaseAndEliminadoFalse(String descripcion);



}
