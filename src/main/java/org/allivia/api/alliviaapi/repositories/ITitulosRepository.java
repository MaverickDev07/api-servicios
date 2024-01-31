package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppTituloEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ITitulosRepository extends CrudRepository<AppTituloEntity,Long> {

    List<AppTituloEntity> findAllByEliminadoFalse();

}
