package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppTipoconsultaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ITiposConsultaRepository extends CrudRepository<AppTipoconsultaEntity,Long> {

        List<AppTipoconsultaEntity> findAllByEliminadoFalseOrderByIdAsc();

}
