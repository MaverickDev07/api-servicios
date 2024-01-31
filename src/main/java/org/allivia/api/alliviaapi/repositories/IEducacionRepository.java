package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IEducacionRepository extends CrudRepository<AppEducacionEntity,Long> {



}
