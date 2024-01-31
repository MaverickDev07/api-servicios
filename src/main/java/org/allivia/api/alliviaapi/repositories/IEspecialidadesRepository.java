package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppEspecialidadEntity;
import org.allivia.api.alliviaapi.entities.AppSintomaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IEspecialidadesRepository extends CrudRepository<AppEspecialidadEntity,Long> {

    List<AppEspecialidadEntity> findAllByEliminadoFalse();
    Optional<AppEspecialidadEntity> findByDescripcionIsLike(String descripcion);

}
