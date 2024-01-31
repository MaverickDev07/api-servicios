package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDiasEntity;
import org.allivia.api.alliviaapi.entities.AppPrefijoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IprefijoRepository extends CrudRepository<AppPrefijoEntity,Long> {



}
