package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppPagoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPagoRepository extends CrudRepository<AppPagoEntity,Long> {

    AppPagoEntity findByTransaccion(String transaccion);
}
