package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppFichamedicamentosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IFMedicamentosRepository extends CrudRepository<AppFichamedicamentosEntity,Long> {

    List<AppFichamedicamentosEntity> findByIdFichamedica(long idFichaMedica);

}
