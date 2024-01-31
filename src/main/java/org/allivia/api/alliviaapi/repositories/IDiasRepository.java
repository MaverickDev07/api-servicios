package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDiasEntity;
import org.allivia.api.alliviaapi.entities.AppPaisEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IDiasRepository extends CrudRepository<AppDiasEntity,Long> {
        @Query(value = "select * from app_dias WHERE id=:id", nativeQuery = true)
        AppDiasEntity getid (@Param("id") long id);

        List<AppDiasEntity> findAllByEliminadoFalse();

}
