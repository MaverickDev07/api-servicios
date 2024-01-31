package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppFichaexamenesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IExamenesRepository extends CrudRepository<AppFichaexamenesEntity,Long> {

    List<AppFichaexamenesEntity> findByIdFichamedica(long idFichaMedica);



}
