package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.TratamientosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ITratamientosRepository extends CrudRepository<TratamientosEntity,Long> {

    List<TratamientosEntity> findByIdcita(long idCita);

}
