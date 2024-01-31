package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppParametrosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IParametroRepository extends CrudRepository<AppParametrosEntity,Long> {

    AppParametrosEntity findByClave(String clave);
    List<AppParametrosEntity> findByParType(String type);


}
