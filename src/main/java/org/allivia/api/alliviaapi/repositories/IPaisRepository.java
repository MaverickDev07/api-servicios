package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppAfiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppPaisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPaisRepository extends CrudRepository<AppPaisEntity,Long> {



}
