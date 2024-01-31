package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppFichalaboratoriosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ILaboratoriosRepository extends CrudRepository<AppFichalaboratoriosEntity,Long> {

    List<AppFichalaboratoriosEntity> findByIdFichamedica(long idFichaMedica);

}
