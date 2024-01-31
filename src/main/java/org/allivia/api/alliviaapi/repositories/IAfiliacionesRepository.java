package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppAfiliacionesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAfiliacionesRepository extends CrudRepository<AppAfiliacionesEntity,Long> {


   List<AppAfiliacionesEntity> findByEliminadoFalse();
}
