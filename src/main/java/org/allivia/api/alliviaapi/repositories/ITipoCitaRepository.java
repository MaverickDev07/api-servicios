package org.allivia.api.alliviaapi.repositories;




import org.allivia.api.alliviaapi.entities.AppTipocitaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ITipoCitaRepository extends CrudRepository<AppTipocitaEntity,Long> {



}
