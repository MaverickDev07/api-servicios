package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppProgramaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IProgramaRepository extends CrudRepository<AppProgramaEntity,Long> {


        List<AppProgramaEntity> findAllByEliminadoFalse();
}
