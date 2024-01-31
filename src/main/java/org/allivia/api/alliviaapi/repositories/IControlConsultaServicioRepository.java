package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppControlconsultaservicioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IControlConsultaServicioRepository extends CrudRepository<AppControlconsultaservicioEntity,Long> {



}
