package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.ProgramasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProgramasRepository extends CrudRepository<ProgramasEntity,Long> {

        ProgramasEntity findByIdcita(long idCita);

}
