package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppHorasEntity;
import org.allivia.api.alliviaapi.entities.AppPaisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IHorasRepository extends CrudRepository<AppHorasEntity,Long> {

    List<AppHorasEntity> findAllByEliminadoFalse();

}
