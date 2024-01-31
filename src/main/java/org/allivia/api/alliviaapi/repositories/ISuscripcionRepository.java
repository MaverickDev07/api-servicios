package org.allivia.api.alliviaapi.repositories;




import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISuscripcionRepository extends CrudRepository<AppSuscripcionEntity,Long> {

        @Query(value = "select * from app_suscripcion where eliminado = FALSE AND (codigo = 'M' OR codigo = 'S' OR codigo = 'G')",nativeQuery = true)
        List<AppSuscripcionEntity> findAllByEliminadoFalse();
}
