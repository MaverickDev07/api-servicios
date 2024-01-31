package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppRespuestaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRespuestaRepository extends CrudRepository<AppRespuestaEntity,Long> {

        List<AppRespuestaEntity> findByPreguntaId(long preguntaId);

}
